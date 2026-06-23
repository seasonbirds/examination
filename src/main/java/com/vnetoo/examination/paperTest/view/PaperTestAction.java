/**
 * 
 */
package com.vnetoo.examination.paperTest.view;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.jasig.cas.client.util.AbstractCasFilter;
import org.jasig.cas.client.validation.Assertion;
import org.springframework.stereotype.Controller;

import com.vnetoo.common.bo.IAuthUser;
import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.SystemKeys;
import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.bo.ScopePolicyRel;
import com.vnetoo.examination.app.bo.ScopeUserRel;
import com.vnetoo.examination.app.service.AppScopeService;
import com.vnetoo.examination.app.service.ScopePolicyRelService;
import com.vnetoo.examination.app.service.ScopeUserRelService;
import com.vnetoo.examination.paper.bo.ExamStudentAnswer;
import com.vnetoo.examination.paper.service.ExamPaperService;
import com.vnetoo.examination.paper.service.ExamStudentAnswerService;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.service.ExamPolicyService;
import com.vnetoo.system.user.bo.SysUser;

@SuppressWarnings("restriction")
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="paperTest",
results={	@Result(name="list",	type="freemarker",location="paperTest/data.htm"),
				@Result(name="search",	type="freemarker",location="studentExam.htm")})
public class PaperTestAction extends BaseAction<AppScope> {

	private static final long serialVersionUID = 1L;
	@Resource
	private AppScopeService appScopeService;
	@Resource
	private ExamPaperService examPaperService;
	@Resource
	private ExamStudentAnswerService examStudentAnswerService;
	@Resource
	private ScopePolicyRelService scopePolicyRelService;
	@Resource
	private ExamPolicyService examPolicyService;
	@Resource
	private ScopeUserRelService scopeUserRelService;
	
	private SysUser user;
	
	AppScope appScope = new AppScope();
	
	public String search(){
		HttpSession session = ServletActionContext.getRequest().getSession();
		Assertion assertion = session != null ? (Assertion) session.getAttribute(AbstractCasFilter.CONST_CAS_ASSERTION) : null;
		if(assertion != null){
			user = (SysUser)assertion.getAttributes().get(SystemKeys.AUTH_USRE);
		}
		if(user == null){
			user = new SysUser();
			IAuthUser authUser = this.getCurrentUser();
			user.setId(authUser.getId());
			user.setAcct(authUser.getAcct());
			user.setRealName("student");
		}
		query();
		return RESULT_SEARCH;
	}
	
	public String query(){
		IAuthUser user = this.getCurrentUser();
		Integer userId = user.getId();
		ScopeUserRel sur = new ScopeUserRel();
		sur.setUserId(userId);
		List<ScopeUserRel> surList = scopeUserRelService.dynamicQuery(sur);
		if(surList != null && !surList.isEmpty()){
			Integer[] scopeIds = new Integer[surList.size()];
			for(int i=0; i<surList.size(); i++){
				scopeIds[i] = surList.get(i).getScopeId();
			}
			
			//appScope.setIds(scopeIds);
			//appScopeService.dynamicQuery(appScope);
			List<AppScope> asList = appScopeService.getAppScopeByIds(scopeIds);
			if(asList != null && !asList.isEmpty()){
				for(AppScope as : asList){
					ScopePolicyRel scopePolicyRel = new ScopePolicyRel();
					scopePolicyRel.setScopeId(as.getId());
					List<ScopePolicyRel> scopePolicyRelList = scopePolicyRelService.dynamicQuery(scopePolicyRel);
					if(scopePolicyRelList == null || scopePolicyRelList.isEmpty()){
						as.setExamStatus(-1);
						continue;
					}
					//无策略
					ExamPolicy examPolicy= examPolicyService.findById(scopePolicyRelList.get(0).getPolicyId());
					if(examPolicy == null){
						as.setExamStatus(-1);
						continue;
					}
					//无试卷
					List<Integer> paperIds = examPaperService.getPaperIdsByScopeId(as.getId());
					if(paperIds == null || paperIds.isEmpty()){
						as.setExamStatus(-1);
						continue;
					}
					
					List<ExamStudentAnswer>examStudentAnswerList = examStudentAnswerService.getStudentAnswerByPaperIdAndSId(userId, paperIds);
					Date examStart = examPolicy.getExamTimeStart();
					Date examEnd = examPolicy.getExamTimeEnd();
					Date current = new Date();
					//当前时间在考试的开始和结束之间
					if(current.after(examStart) && current.before(examEnd)){
						if(examStudentAnswerList == null || examStudentAnswerList.isEmpty()){
							//无答题记录，参加考试
							as.setExamStatus(1);
						}else{
							ExamStudentAnswer examStudentAnswer = examStudentAnswerList.get(0);
							if(examStudentAnswer.getStatus() == 0){
								//未交卷，继续考试
								as.setExamStatus(2);
							}else if(examStudentAnswer.getStatus() == 1){
								//已交卷，查看结果
								as.setExamStatus(3);
							}
						}
					//当前时间在考试结束之后
					}else if(current.after(examEnd)){
						//存在答题记录，查看结果
						if(examStudentAnswerList != null && !examStudentAnswerList.isEmpty()){
							ExamStudentAnswer examStudentAnswer = examStudentAnswerList.get(0);
							if(examStudentAnswer.getStatus() == 0){
								//未交卷，查看结果
								as.setExamStatus(4);
							}else if(examStudentAnswer.getStatus() == 1){
								//已交卷，查看结果
								as.setExamStatus(3);
							}
						}else{
							//不存在答题记录，考试已过期
							as.setExamStatus(5);
						}
					//当前时间在考试开始之前
					}else if(current.before(examStart)){
						as.setExamStatus(0);
					}
				}
				paginator.setResultList(asList);
				paginator.setPageCount(asList.size()/10 + 1);
				paginator.setResultCount(asList.size());
			}
		}
		return RESULT_LIST;
	}

	public AppScope getAppScope() {
		return appScope;
	}

	public void setAppScope(AppScope appScope) {
		this.appScope = appScope;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}
}
