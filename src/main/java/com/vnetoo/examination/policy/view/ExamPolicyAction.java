package com.vnetoo.examination.policy.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.vnetoo.common.bo.IAuthUser;
import com.vnetoo.common.enums.Bool;
import com.vnetoo.common.view.BaseAction;

import org.springframework.stereotype.Controller;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.service.AppScopeService;
import com.vnetoo.examination.app.service.ScopeCourseRelService;
import com.vnetoo.examination.app.service.ScopeKeyPointRelService;
import com.vnetoo.examination.app.service.ScopePolicyRelService;
import com.vnetoo.examination.enums.QuestionRangeType;
import com.vnetoo.examination.keypoint.service.ExamKeyPointService;
import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.service.ExamPolicyService;
import com.vnetoo.examination.question.service.ExamQuestionService;

import freemarker.ext.beans.ResourceBundleModel;

/**
 * @comment 出题策略Action
 * @author chenh
 * @date 2013-11-13 
 */
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="examPolicy",
results={@Result(name="list",	type="freemarker",location="examPolicy/list.htm"),
		        @Result(name="data",	type="freemarker",location="examPolicy/data.htm"),	
		        @Result(name="create",	type="freemarker",location="examPolicy/create.htm")})
public class ExamPolicyAction extends BaseAction<ExamPolicy>{
	private static final long serialVersionUID = -3778812527518280894L;
	@Resource
	private ExamPolicyService examPolicyService;
	@Resource
	private ScopePolicyRelService scopePolicyRelService;
	@Resource
	private AppScopeService appScopeService;
	@Resource
	private ExamQuestionService examQuestionService;
	@Resource
	private ScopeCourseRelService scopeCourseRelService;
	@Resource
	private ScopeKeyPointRelService scopeKeyPointRelService;
	@Resource
	private ExamKeyPointService examKeyPointService;
	
	private ExamPolicy examPolicy = new ExamPolicy();
	
	private String dialogType;
	
	private String[] policyDetails;
	
	private Boolean isCreatePaper;
	
	private String otherExamInfo;
	
	private Map<String, Integer> questionTypeAndCountMap = new HashMap<String, Integer>();
	/**************************************************************************************/
		
	/**
	 * 进入查询出题策略页面
	 * /jsp/examPolicy/list.htm
	 */
	public String list() {		
		search();
		return RESULT_LIST;
	}
		
	/**
	 * 查询出题策略
	 * /jsp/examPolicy/data.jsp
	 */
	public String search() {				
		examPolicyService.pageQuery(examPolicy,paginator);
		return RESULT_DATA;
	}
	
	/**
	 * 进入出题策略修改页面
	 * /jsp/examPolicy/CreateExamPolicy.jsp 
	 */
	public String toCreateOrUpdate() {
		AppScope appScope = null;
		Integer scopeId = examPolicy.getScopeId();
		//获取考试(课程/类型)对应的试题数量
		if(scopeId != null && scopeId > 0){
			appScope=appScopeService.findById(scopeId);
			//考试对应课程
			List<Integer> courseIds = scopeCourseRelService.getCourseIdsByScopeId(scopeId);
			if(courseIds != null && !courseIds.isEmpty()){
				List<Integer> keyPointIds = null;
				//只对应一门课程，判断是否设置了知识点
				if(courseIds.size() == 1){
					keyPointIds = scopeKeyPointRelService.getKeyPointIdListByScopeId(scopeId);
				}
				//没有设置知识点，获取课程对应的默认知识点
				if(keyPointIds == null || keyPointIds.isEmpty()){
					keyPointIds = examKeyPointService.getKeyPointIdListByCourseId(courseIds);
				}
				//获取知识点和考试类型对应的试题类型与数量的对应关系
				//目前由于入学考试和模拟考试的特殊关系，考试类型为入学考试的时候，查询的是模拟考试的试题题量
				String type= appScope.getType().toString();
				if(type.equals("ENROL")){
					type = "SIMULATION";
				}
				questionTypeAndCountMap = examQuestionService.getQuestionTypeAndCountByKeyPointsAndRangeType(keyPointIds, type);
			}
		}
		
		if(examPolicy.getId()!=null&&examPolicy.getId().longValue()>0){//修改
			QuestionRangeType  type = examPolicy.getExamType();
			examPolicy=examPolicyService.findById(examPolicy.getId());		
			if(type != null){
				examPolicy.setExamType(type);
			}
			examPolicy.setScopeId(scopeId);
		}else {
				if(appScope!=null){
					examPolicy.setName(appScope.getName() != null ? appScope.getName() : "");
					examPolicy.setShowAnswer(Bool.YES);
					examPolicy.setShowScore(Bool.YES);
				} 
		}
		return RESULT_CREATE;
	}
	
	/**
	 * 保存出题策略	
	 */
	public String save() {
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			if(examPolicy == null || policyDetails == null || policyDetails.length < 1){
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg(rbm.format("PARAMETER.ERROR", null));
			}else{
				IAuthUser user = this.getCurrentUser();
				if(user == null || user.getId() == null || user.getId() < 1){
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg(rbm.format("AUTH.ERROR", null));
				}else{
					Integer userId = user.getId();
					//保存出题策略
					Integer policyId = examPolicyService.savePolicy(examPolicy, policyDetails, userId);
					if(policyId != null && policyId > 0){
						//根据页面参数生成试卷
						if(isCreatePaper == null || isCreatePaper == false){
							setResponseMsg(rbm.format("POLICY.SAVE.SUCCESS", null));
						}else{
							CreatePaperResult result = examPolicyService.createPaper(policyId, userId, otherExamInfo);
							if(!result.getCode().equals("PAPER.CREATE.SUCCESS")){
								this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
								if(result.getType() != null && result.getLev() != null){
									String type = rbm.format("QuestionType." + result.getType().toUpperCase() , null);
									String lev = rbm.format("QuestionLevel." + result.getLev().toUpperCase(), null);
									setResponseMsg(rbm.format(result.getCode(), new String[]{lev,type}));
								}else{
									setResponseMsg(rbm.format(result.getCode(), null));
								}
							}else{
								setResponseMsg(rbm.format(result.getCode(), null));
							}
						}
					}else{
						setResponseFlag(ExamConstant.RESPONSE_FAILURE);
						setResponseMsg(rbm.format("POLICY.SAVE.FAILURE", null));
					}
				}
			}
		}catch(Exception e){
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		
		writeResponse();
		return null;
	}
	
	/**
	 * 删除出题策略
	 */
	public String remove() {
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			if(examPolicy.getId() != null && examPolicy.getId() > 0){
				//先删除策略详情
				examPolicyService.deletePolicyDetailByPolicy(examPolicy.getId());
				//删除Scope-Policy的关系
				scopePolicyRelService.deleteRel( examPolicy.getId());
				//删除策略
				examPolicyService.removeLogic(examPolicy.getId());
			}else{
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg(rbm.format("PARAMETER.ERROR", null));
			}
		}catch(Exception e){
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}

		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除出题策略
	 */
	public String batchRemove() {
		try{
			for(int i=0;i<examPolicy.getIds().length;i++){
				examPolicyService.removeLogic(examPolicy.getIds()[i]);		
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**************************************************************************************/
	public void setExamPolicy(ExamPolicy examPolicy) {
		this.examPolicy = examPolicy;
	}
	
	public ExamPolicy getExamPolicy() {
		return examPolicy;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}

	public String[] getPolicyDetails() {
		return policyDetails;
	}

	public void setPolicyDetails(String[] policyDetails) {
		this.policyDetails = policyDetails;
	}

	public Boolean getIsCreatePaper() {
		return isCreatePaper;
	}

	public void setIsCreatePaper(Boolean isCreatePaper) {
		this.isCreatePaper = isCreatePaper;
	}

	public String getOtherExamInfo() {
		return otherExamInfo;
	}

	public void setOtherExamInfo(String otherExamInfo) {
		this.otherExamInfo = otherExamInfo;
	}

	public Map<String, Integer> getQuestionTypeAndCountMap() {
		return questionTypeAndCountMap;
	}

	public void setQuestionTypeAndCountMap(
			Map<String, Integer> questionTypeAndCountMap) {
		this.questionTypeAndCountMap = questionTypeAndCountMap;
	}
}