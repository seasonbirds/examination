package com.vnetoo.examination.app.view;

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
import com.vnetoo.examination.app.bo.AppCourse;
import com.vnetoo.examination.app.service.AppCourseService;

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
@Action(value="appCourse",
results=
{
		@Result(name="list",	type="freemarker",location="appCourse/list.htm"),
		@Result(name="data",	type="freemarker",location="appCourse/data.htm"),	
		@Result(name="create",	type="freemarker",location="appCourse/create.htm"),
}
)
public class AppCourseAction extends BaseAction<AppCourse>{
	@Resource
	private AppCourseService appCourseService;
	
	private AppCourse appCourse = new AppCourse();
	
	private Integer scopeId;
	
	/**************************************************************************************/
	/**
	 * 进入查询页面
	 * /jsp/appCourse/list.htm
	 */
	public String list() 
	{		
		search();
		return RESULT_LIST;
	}
		
	/**
	 * 查询
	 * /jsp/appCourse/data.jsp
	 */
	public String search() 
	{				
		appCourseService.pageQuery(appCourse,paginator);
		return RESULT_DATA;
	}
	
	/**
	 * 进入修改页面
	 * /jsp/appCourse/CreateAppCourse.jsp 
	 */
	public String toCreateOrUpdate()
	{
		if(appCourse.getId()!=null&&appCourse.getId().longValue()>0){//修改
			appCourse=appCourseService.findById(appCourse.getId());			
		}
		return RESULT_CREATE;
	}
	
	/**
	 * 保存	
	 */
	public String save()
	{
		if(appCourse.getId()!=null&&appCourse.getId().longValue()>0){
			AppCourse po=appCourseService.findById(appCourse.getId());			
			//po.setName(appCourse.getName());
			
			//po.setUpdateBy(new Integer(0));			
			//appCourse=po;
		}
		else{
			//appCourse.setCreateBy(new Integer(0));
			//appCourse.setUpdateBy(appCourse.getCreateBy());
		}
		try{
			appCourseService.saveOrUpdate(appCourse);			
		}catch(Exception e){
			setResponseMsg(e.getMessage());
			setResponseFlag(-1);
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 删除
	 */
	public String remove() 
	{
		try{
			appCourseService.removeLogic(appCourse.getId());			
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
	public String batchRemove() 
	{
		try{
			for(int i=0;i<appCourse.getIds().length;i++){
				appCourseService.removeLogic(appCourse.getIds()[i]);		
			}
		}catch(Exception e){
			setResponseFlag(-1);
			setResponseMsg(e.getMessage());
		}
		writeResponse();
		return null;
	}
	
	/**
	 * 获取所有课程列表
	 * @return
	 */
	public String getCourseList(){
		JSONObject json = new JSONObject();
		try{
			List<AppCourse> list = appCourseService.dynamicQuery(new AppCourse());
			if(list != null && list.size() > 0){
				JSONArray array = new JSONArray();
				for(AppCourse course : list){
					JSONObject item = new JSONObject();
					item.put("id", course.getId());
					item.put("name", course.getName());
					array.add(item);
				}
				json.put("result", "success");
				json.put("data", array);
			}else{
				json.put("result", "failure");
				json.put("msg", "This is no data");
			}
		}catch(Exception e){
			json.put("result", "failure");
			json.put("msg", "Exception occour");
		}
		try {
			this.writeResponse(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 根据scopeId获取课程列表
	 * @return
	 */
	public String getCourseListByScopeId(){
		JSONObject json = new JSONObject();
		ResourceBundleModel model = this.getResourceBundle();
		try{
			if(scopeId != null && scopeId > 0){
				List<AppCourse> l = appCourseService.getCourseListByScopId(scopeId);
				JSONArray a = new JSONArray();
				if(l != null && l.size() > 0){
					for(int i=0; i<l.size(); i++){
						AppCourse course = l.get(i);
						JSONObject item = new JSONObject();
						item.put("id", course.getId());
						item.put("name", course.getName());
						a.add(item);
					}
				}
				json.put("result", "success");
				json.put("data", a);
			}else{
				json.put("result", "failure");
				json.put("msg", model.format("PARAMETER.ERROR", null));
			}
		}catch(Exception e){
			json.put("result", "failure");
			json.put("msg", model.format("SERVER.EXCEPTION", null));
		}
		
		try {
			this.writeResponse(json.toJSONString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**************************************************************************************/
	public void setAppCourseService(AppCourseService appCourseService) {
		this.appCourseService = appCourseService;
	}
	
	public AppCourseService getAppCourseService() {
		return appCourseService;
	}
	
	public void setAppCourse(AppCourse appCourse) {
		this.appCourse = appCourse;
	}
	
	public AppCourse getAppCourse() {
		return appCourse;
	}
	
	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}
}



