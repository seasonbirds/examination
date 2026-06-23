package com.vnetoo.examination.keypoint.view;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.bo.IAuthUser;
import com.vnetoo.common.enums.Enabled;
import com.vnetoo.common.view.BaseAction;

import org.springframework.stereotype.Controller;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.app.bo.AppCourse;
import com.vnetoo.examination.app.service.AppCourseService;
import com.vnetoo.examination.keypoint.bo.ExamKeyPoint;
import com.vnetoo.examination.keypoint.service.ExamKeyPointService;

import freemarker.ext.beans.ResourceBundleModel;

/**
 * @comment 知识点Action
 * @author chenh
 * @date 2013-11-08
 */
@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value = "examKeyPoint", results = {
		@Result(name = "search", type = "freemarker", location = "keyPoint/search.htm"),
		@Result(name = "list", type = "freemarker", location = "keyPoint/list.htm"),
		@Result(name = "create", type = "freemarker", location = "keyPoint/create.htm"),
		@Result(name = "pickup", type = "freemarker", location = "keyPoint/pickup.htm")
	})
public class ExamKeyPointAction extends BaseAction<ExamKeyPoint> {
	private static final long serialVersionUID = 7398383166899903403L;
	
	private static final Logger logger = Logger.getLogger(ExamKeyPointAction.class);
	@Resource
	private ExamKeyPointService examKeyPointService;
	@Resource
	private AppCourseService appCourseService;
	
	private ExamKeyPoint examKeyPoint = new ExamKeyPoint();

	private Boolean isParent;
	
	private String parentId;

	private String dialogType;
	private List<AppCourse> courses;
	private AppCourse appCourse;
	/**************************************************************************************/
	/**
	 * 挑选课程
	 */
	public String pickup() {
		setCourses(appCourseService.dynamicQuery(new AppCourse()));
		return "pickup";
	}
	/**
	 * 进入查询知识点页面 /jsp/examKeyPoint/list.htm
	 */
	public String search() {
		//query();
		if(examKeyPoint!=null && examKeyPoint.getCourseId()!=null){
			appCourse=appCourseService.findById(examKeyPoint.getCourseId());
		}
		return RESULT_SEARCH;
	}
	
	public String query() {	
		if(isParent != null && parentId != null && !"".equals(parentId)){
			//如果是父节点，查询父节点下的所有子节点，否则查询节点本身
			if(isParent){
				examKeyPoint.setParentId(Integer.parseInt(parentId));
			}else{
				examKeyPoint.setId(Integer.parseInt(parentId));
			}
		}
		examKeyPointService.pageQuery(examKeyPoint, paginator);
		return RESULT_LIST;
	}

	/**
	 * 获取所有树节点
	 */
	public String getKeyPointTree() {
		JSONArray a = new JSONArray();
		try {
			Integer courseId = examKeyPoint.getCourseId();
			if( courseId != null && !"".equals(courseId)){
				List<ExamKeyPoint> l = examKeyPointService.dynamicQuery(examKeyPoint);
				if (l != null && l.size() > 0) {
					HashMap<Integer,String> parentMap = new HashMap<Integer,String>();
					for (int i = 0; i < l.size(); i++) {
						parentMap.put(l.get(i).getParentId(), "");
					}
					for (int i = 0; i < l.size(); i++) {
						ExamKeyPoint point = l.get(i);
						JSONObject json = new JSONObject();
						Integer id = point.getId();
						json.put("id", id);
						json.put("pid", point.getParentId());
						json.put("name", point.getName());

						if (parentMap.containsKey(id)) {
							json.put("isParent", true);
							json.put("open", "true");
						}
						a.add(json);
					}
				}else{
					AppCourse course = appCourseService.findById(courseId);
					if(course != null){
						ExamKeyPoint point = new ExamKeyPoint();
						point.setCourseId(courseId);
						point.setName(course.getName());
						point.setParentId(0);
						point.setStatus(Enabled.USED);
						point.setCreatedBy(getCurrentUser().getId());
						point.setMemo("root");
						Integer id = examKeyPointService.saveOrUpdate(point);
						JSONObject json = new JSONObject();
						json.put("id", id);
						json.put("pid", point.getParentId());
						json.put("name", point.getName());
						a.add(json);
					}else{
						throw new Exception("No the course with the id " + courseId);
					}
				}
			}
		} catch (Exception e) {
			logger.error("Server exception", e);
		}
		this.writeResponse(a.toJSONString());
		return null;
	}

	/**
	 * 进入知识点修改页面
	 */
	public String toCreateOrUpdate() {
		try{
			if (examKeyPoint.getId() != null
					&& examKeyPoint.getId().longValue() > 0) {// 修改
				examKeyPoint = examKeyPointService.findById(examKeyPoint.getId());
				if(examKeyPoint.getParentId() != 0){
					ExamKeyPoint parentKeyPoint = examKeyPointService.findById(examKeyPoint.getParentId());
					examKeyPoint.setParentName(parentKeyPoint.getName());
				}else{
					examKeyPoint.setParentName("Root");
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return RESULT_CREATE;
	}

	/**
	 * 保存知识点
	 */
	public String save() {
		ResourceBundleModel rbm = this.getResourceBundle();
		try {
			IAuthUser user = this.getCurrentUser();
			if(user == null || user.getId() == null || user.getId() <= 0){
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg(rbm.format("AUTH.ERROR", null));
			}else{
				//可操作标志，根节点不可修改
				boolean flag = true;
				Integer kpId = examKeyPoint.getId();
				if(kpId != null && kpId > 0){
					ExamKeyPoint keyPoint = examKeyPointService.findById(kpId);
					if(keyPoint.getParentId() == -1){
						setResponseFlag(ExamConstant.RESPONSE_FAILURE);
						setResponseMsg(rbm.format("KEYPOINT_OPERATOR_FORBID_ROOT", null));
						flag = false;
					}
				}
				if(flag){
					int userId = user.getId();
					examKeyPoint.setCreatedBy(userId);
					examKeyPoint.setLastUpdatedBy(userId);
					int result = examKeyPointService.saveOrUpdate(examKeyPoint);
					if (result > 0) {
						setResponseFlag(ExamConstant.RESPONSE_SUCCESS);
						setResponseMsg(rbm.format("OPERATOR_SUCCESS", null));
					} else {
						setResponseFlag(ExamConstant.RESPONSE_FAILURE);
						setResponseMsg(rbm.format("DATABASE.EXCEPTION", null));
					}
				}
			}
		} catch (Exception e) {
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		
		writeResponse();
		return null;
	}

	/**
	 * 删除知识点
	 */
	public String remove() {
		ResourceBundleModel rbm = this.getResourceBundle();
		try {
			Integer id = examKeyPoint.getId();
			ExamKeyPoint ekp = new ExamKeyPoint();
			ekp.setParentId(id);
			List<ExamKeyPoint> l = examKeyPointService.dynamicQuery(ekp);
			//父节点不可删除
			if (l == null || l.size() == 0) {
				//根节点不可删除
				ExamKeyPoint kp = examKeyPointService.findById(id);
				if(kp.getParentId() == -1){
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg(rbm.format("KEYPOINT_OPERATOR_FORBID_ROOT", null));
				}else{
					examKeyPointService.removeLogic(id);
				}
			} else {
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg(rbm.format("KEYPOINT_DELETE_PARENT", null));
			}
		} catch (Exception e) {
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		this.writeResponse();
		return null;
	}

	/**
	 * 批量删除知识点
	 */
	public String batchRemove() {
		ResourceBundleModel rbm = this.getResourceBundle();
		try {
			boolean delFlag = true;
			//批量删除先判断是否是父节点（带子节点）、跟节点
			for (int i = 0; i < examKeyPoint.getIds().length; i++) {
				ExamKeyPoint ekp = new ExamKeyPoint();
				ekp.setParentId(examKeyPoint.getIds()[i]);
				List<ExamKeyPoint> l = examKeyPointService.dynamicQuery(ekp);
				if (l != null && !l.isEmpty()) {
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg(rbm.format("KEYPOINT_DELETE_PARENT", null));
					delFlag = false;
					break;
				}
			}
			if(delFlag){
				for (int i = 0; i < examKeyPoint.getIds().length; i++) {
					examKeyPointService.removeLogic(examKeyPoint.getIds()[i]);
				}
			}
		} catch (Exception e) {
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg(rbm.format("SERVER.EXCEPTION", null));
		}
		writeResponse();
		return null;
	}

	/**************************************************************************************/

	public void setExamKeyPointService(ExamKeyPointService examKeyPointService) {
		this.examKeyPointService = examKeyPointService;
	}

	public ExamKeyPointService getExamKeyPointService() {
		return examKeyPointService;
	}

	public void setExamKeyPoint(ExamKeyPoint examKeyPoint) {
		this.examKeyPoint = examKeyPoint;
	}

	public ExamKeyPoint getExamKeyPoint() {
		return examKeyPoint;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}

	public Boolean getIsParent() {
		return isParent;
	}

	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	public List<AppCourse> getCourses() {
		return courses;
	}
	public void setCourses(List<AppCourse> courses) {
		this.courses = courses;
	}
	public AppCourse getAppCourse() {
		return appCourse;
	}
	public void setAppCourse(AppCourse appCourse) {
		this.appCourse = appCourse;
	}
}
