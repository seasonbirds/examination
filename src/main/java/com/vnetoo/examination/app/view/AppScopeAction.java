package com.vnetoo.examination.app.view;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.view.BaseAction;

import org.springframework.stereotype.Controller;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.app.bo.AppCourse;
import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.bo.ScopeCourseRel;
import com.vnetoo.examination.app.service.AppCourseService;
import com.vnetoo.examination.app.service.AppScopeService;
import com.vnetoo.examination.app.service.ScopeCourseRelService;
import com.vnetoo.examination.app.service.ScopeKeyPointRelService;
import com.vnetoo.examination.app.service.ScopePolicyRelService;
import com.vnetoo.examination.enums.QuestionRangeType;

import freemarker.ext.beans.ResourceBundleModel;

/**
 * @comment Action
 * @author chenh
 * @date 2013-11-11 
 */
@SuppressWarnings("serial") 
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value="appScope",
results=
{
		@Result(name="search",	type="freemarker",location="appScope/search.htm"),
		@Result(name="list",	type="freemarker",location="appScope/list.htm"),
		@Result(name="create",	type="freemarker",location="appScope/create.htm"),
		@Result(name="show",	type="freemarker",location="appScope/show.htm")
}
)
public class AppScopeAction extends BaseAction<AppScope>{
	@Resource
	private AppScopeService appScopeService;
	
	private AppScope appScope = new AppScope();
	
	@Resource
	private ScopeKeyPointRelService scopeKeyPointRelService;
	
	@Resource
	private ScopePolicyRelService scopePolicyRelService;
	
	@Resource
	private ScopeCourseRelService scopeCourseRelService;
	@Resource
	private AppCourseService appCourseService;
	
	private Boolean courseModFlag;
	
	private String courseIds;
	
	private Boolean keyPointModFlag;
	
	private String keyPointIds;
	
	private String dialogType;
	private List<AppCourse> courses;
	/**************************************************************************************/
		


	/**
	 * 进入查询页面
	 * /jsp/appScope/search.htm
	 */
	public String search() {		
		query();
		return RESULT_SEARCH;
	}
		
	/**
	 * 查询
	 * /jsp/appScope/list.jsp
	 */
	public String query() {	
		/*当前为了在考试管理中筛选掉入学考试和模拟考试，为了最小化的改动，
		 * 在sql语句中增加筛选条件，详情见AppScope.xml的selectPage*/
		appScopeService.pageQuery(appScope,paginator);
		List<AppScope> list = paginator.getResultList();
		for(AppScope aScope : list){
			Integer scopeId = aScope.getId();
			Integer policyNum = scopePolicyRelService.getPolicyCountByScopeId(scopeId);
			Integer paperNum = scopePolicyRelService.getPaperCountByScopeId(scopeId);
			aScope.setPolicyNum(policyNum);
			aScope.setPaperNum(paperNum);
		}
		return RESULT_LIST;
	}
	
	/**
	 * 进入修改页面
	 * /jsp/appScope/CreateAppScope.jsp 
	 */
	public String toCreateOrUpdate(){
		if(appScope.getId()!=null&&appScope.getId().longValue()>0){//修改
			appScope=appScopeService.findById(appScope.getId());			
		}
		return RESULT_CREATE;
	}
	/**
	 * 展示
	 *  
	 */
	public String show(){
		if(appScope.getId()!=null&&appScope.getId().longValue()>0){
			appScope=appScopeService.findById(appScope.getId());
			//获得课程名称集合
			List<Integer> courseIds = scopeCourseRelService.getCourseIdsByScopeId(appScope.getId());
			if(courseIds!=null &&courseIds.size()>0){
				courses=new ArrayList<AppCourse>();
				for (Integer courseId : courseIds) {
					courses.add(appCourseService.findById(courseId));
				}
			}
			
			return RESULT_SHOW;
		}
		return null;
	}
	/**
	 * 保存	
	 */
	public String save(){
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			if(appScope == null || appScope.getName() == null || !isValidType(appScope.getType()) 
					|| (courseModFlag != null && courseModFlag == true && courseIds == null)
					|| (keyPointModFlag != null && keyPointModFlag == true && keyPointIds == null)){
				setResponseFlag(-1);
				setResponseMsg(rbm.format("PARAMETER.ERROR", null));
			}else{
				Integer userId = this.getCurrentUser().getId();
				if(userId == null || userId < 1){
					setResponseFlag(-1);
					setResponseMsg(rbm.format("AUTH.ERROR", null));
				}else{
					appScope.setCreatedBy(userId);
					appScope.setLastUpdatedBy(userId);
					appScopeService.save(appScope, courseModFlag, courseIds, keyPointModFlag, keyPointIds);			
				}
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除
	 */
	public String remove() {
		try{
			//appScopeService.removeLogic(appScope.getId());		
			appScopeService.delete(appScope.getId());
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 批量删除
	 */
	public String batchRemove() {
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			Integer[] ids = appScope.getIds();
			if(ids == null){
				setResponseFlag(-1);
				setResponseMsg(rbm.format("PARAMETER.ERROR", null));
			}else{
				for(int i=0;i<ids.length;i++){
					//appScopeService.removeLogic(appScope.getIds()[i]);		
					appScopeService.delete(ids[i]);
				}
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 获取考试的相关数据
	 */
	public String getScopeData(){
		ResourceBundleModel rbm = this.getResourceBundle();
		JSONObject json = null;
		try{
			if(appScope == null || appScope.getId() == null){
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				this.setResponseMsg(rbm.format("PARAMETER.ERROR", null));
			}else{
				json = new JSONObject();
				appScope = appScopeService.findById(appScope.getId());
				json.put("type", appScope.getType());
				
				List<Integer> courseIds = scopeCourseRelService.getCourseIdsByScopeId(appScope.getId());
				json.put("courseIds", list2Str(courseIds));
				List<Integer> keyPointIds = scopeKeyPointRelService.getKeyPointIdListByScopeId(appScope.getId());
				if(keyPointIds != null && keyPointIds.size() > 0){
					json.put("keyPointIds", list2Str(keyPointIds));
				}
			}
		}catch(Exception e){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			this.setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		
		this.writeJsonDataResponse(json);
		return null;
	}
	
	/**
	 * 查询appScope信息（json格式，仅限AppScope本身数据，不包括课程知识点等信息）
	 */
	public String getAppScopeJsonData(){
		JSONArray json = null;
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			List<AppScope> list = appScopeService.dynamicQuery(appScope);
			if(list != null && !list.isEmpty()){
				json = new JSONArray();
				for(AppScope as : list){
					JSONObject o = new JSONObject();
					o.put("id", as.getId());
					o.put("name", as.getName());
					json.add(o);
				}
			}
		}catch(Exception e){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			this.setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		
		this.writeJsonDataResponse(json);
		return null;
	}
	
	/**
	 * 获取相同课程的模拟考试
	 */
	public String getSimulationExamWithSameCourse(){
		JSONArray json = null;
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			if(appScope == null || appScope.getId() == null || appScope.getType() == null){
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				this.setResponseMsg(rbm.format("PARAMETER.ERROR", null));
			}else{
				AppScope as = appScopeService.findById(appScope.getId());
				if(as == null){
					this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					this.setResponseMsg(rbm.format("PARAMETER.ERROR", null));
				}else{
					ScopeCourseRel scopeCourseRel = new ScopeCourseRel();
					scopeCourseRel.setScopeId(as.getId());
					List<ScopeCourseRel> scrList = scopeCourseRelService.dynamicQuery(scopeCourseRel);
					//暂时只考虑对应一门课程的考试（appScope.getType()=simulation）
					List<AppScope> list = appScopeService.getAppScopeByTypeAndCourse(appScope.getType().toString(), scrList.get(0).getCourseId());
					if(list != null && !list.isEmpty()){
						json = new JSONArray();
						for(AppScope scope : list){
							JSONObject o = new JSONObject();
							o.put("id", scope.getId());
							o.put("name", scope.getName());
							json.add(o);
						}
					}
				}
			}
		}catch(Exception e){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			this.setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		
		this.writeJsonDataResponse(json);
		return null;
	}
	
	/**
	 * 将列表转化为逗号字符串
	 */
	private String list2Str(List<Integer> ids){
		if(ids == null){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i<ids.size(); i++){
			sb.append(ids.get(i));
			if(i != ids.size() - 1){
				sb.append(",");
			}
		}
		return sb.toString();
	}
	
	/**
	 * 验证考试的类型
	 */
	private boolean isValidType(QuestionRangeType  type){
		boolean validate = true;
		if(type == null){
			validate = false;
		}else{
			if(type != QuestionRangeType.COMMON && type != QuestionRangeType.ENROL && type != QuestionRangeType.EXAM
					&& type != QuestionRangeType.PRACTISE && type != QuestionRangeType.SIMULATION && type != QuestionRangeType.TASK){
				validate = false;
			}
		}
		return validate;
	}
	/**************************************************************************************/
	
	public void setAppScopeService(AppScopeService appScopeService) {
		this.appScopeService = appScopeService;
	}
	
	public AppScopeService getAppScopeService() {
		return appScopeService;
	}
	
	public void setAppScope(AppScope appScope) {
		this.appScope = appScope;
	}
	
	public AppScope getAppScope() {
		return appScope;
	}
	
	public String getKeyPointIds() {
		return keyPointIds;
	}
	
	public Boolean getCourseModFlag() {
		return courseModFlag;
	}

	public void setCourseModFlag(Boolean courseModFlag) {
		this.courseModFlag = courseModFlag;
	}

	public String getCourseIds() {
		return courseIds;
	}

	public void setCourseIds(String courseIds) {
		this.courseIds = courseIds;
	}

	public Boolean getKeyPointModFlag() {
		return keyPointModFlag;
	}

	public void setKeyPointModFlag(Boolean keyPointModFlag) {
		this.keyPointModFlag = keyPointModFlag;
	}

	public void setKeyPointIds(String keyPointIds) {
		this.keyPointIds = keyPointIds;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}
	public List<AppCourse> getCourses() {
		return courses;
	}

	public void setCourses(List<AppCourse> courses) {
		this.courses = courses;
	}
}