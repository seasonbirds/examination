package com.vnetoo.examination.app.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.app.bo.AppCourse;
import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.service.AppCourseService;
import com.vnetoo.examination.app.service.AppScopeService;
import com.vnetoo.examination.app.service.ScopeCourseRelService;
import com.vnetoo.examination.app.service.ScopePolicyRelService;
import com.vnetoo.examination.paper.service.ExamPaperService;

/**
 * 作为特殊的考试类型（模拟考试和入学考试），本类只做为页面转向
 * 其它考试的功能请参见AppScopeAction类
 */

@SuppressWarnings("restriction")
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value = "specialExamAction", results = {
		@Result(name = "search", type = "freemarker", location = "specialExam/search.htm"),
		@Result(name = "list", type = "freemarker", location = "specialExam/list.htm"),
		@Result(name = "show", type = "freemarker", location = "specialExam/show.htm"),
		@Result(name="create",	type="freemarker",location="specialExam/create.htm"),})
public class SpecialExamAction extends BaseAction<AppScope> {

	private static final long serialVersionUID = 6966466375440541532L;
	
	@Resource
	private AppScopeService appScopeService;
	@Resource
	private ScopePolicyRelService scopePolicyRelService;
	@Resource
	private ExamPaperService examPaperService;
	@Resource
	private ScopeCourseRelService scopeCourseRelService;
	@Resource
	private AppCourseService appCourseService;
	
	private AppScope appScope = new AppScope();

	private String dialogType;
	private List<AppCourse> courses;
	/**
	 * 进入查询页面
	 */
	public String search() {		
		query();
		return RESULT_SEARCH;
	}
		
	public List<AppCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<AppCourse> courses) {
		this.courses = courses;
	}

	/**
	 * 查询
	 */
	public String query() {				
		appScopeService.pageQuery(appScope,paginator);
		List<AppScope> list = paginator.getResultList();
		for(AppScope aScope : list){
			Integer scopeId = aScope.getId();
			Integer policyId = 0;
			Integer paperId = 0;
			List<Integer> policyIds = scopePolicyRelService.getPolicyIds(scopeId);
			if(policyIds != null && !policyIds.isEmpty()){
				policyId = policyIds.get(0);
			}
			aScope.setPolicyNum(policyId);
			List<Integer> paperIds = examPaperService.getPaperIdsByScopeId(scopeId);
			if("ENROL".equals(appScope.getType().toString())){
				aScope.setPaperNum(paperIds != null ? paperIds.size() : 0);
			}else if("SIMULATION".equals(appScope.getType().toString())){
				if(paperIds != null && !paperIds.isEmpty()){
					paperId = paperIds.get(0);
				}
				aScope.setPaperNum(paperId);
			}
		}
		return RESULT_LIST;
	}
	
	/**
	 * 进入修改页面
	 */
	public String toCreateOrUpdate(){
		if(appScope.getId()!=null&&appScope.getId().longValue()>0){//修改
			appScope=appScopeService.findById(appScope.getId());			
		}
		return RESULT_CREATE;
	}
	/**
	 * 查看
	 */
	public String show(){
		if(appScope.getId()!=null&&appScope.getId().longValue()>0){//查看
			appScope=appScopeService.findById(appScope.getId());	
			//获得课程名称集合
			List<Integer> courseIds = scopeCourseRelService.getCourseIdsByScopeId(appScope.getId());
			if(courseIds!=null &&courseIds.size()>0){
				courses=new ArrayList<AppCourse>();
				for (Integer courseId : courseIds) {
					courses.add(appCourseService.findById(courseId));
				}
			}
			
		}
		return RESULT_SHOW;
	}

	public AppScope getAppScope() {
		return appScope;
	}

	public void setAppScope(AppScope appScope) {
		this.appScope = appScope;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}

 
}
