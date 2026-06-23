package com.vnetoo.webservice.course;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.app.bo.AppCourse;
import com.vnetoo.examination.app.service.AppCourseService;
import com.vnetoo.webservice.exam.ExamAction;

@Controller
@ParentPackage("default")
@Namespace("/services/course")
/**
 * 考试系统提供的web service接口：同步课程数据
 */
public class CourseSynchronizeAction extends BaseAction<Object> {

	private static final Logger log = LoggerFactory.getLogger(ExamAction.class);
	
	private static final long serialVersionUID = -6825543520720575529L;
	
	@Resource
	private AppCourseService appCourseService;
	//课程ID
	private String courseId;
	//课程名
	private String courseName;
	
	@Action(value="addCourse")
	public String addCourse() {
		if(!validCourseId(courseId) || courseName == null || "".equals(courseName.trim())){
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg("Illegal Argument!");
		}else{
			try{
				Integer cId = Integer.parseInt(courseId);
				AppCourse ac = appCourseService.findById(cId);
				if(ac != null){
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg("Value  of courseId already exist!");
				}else{
					AppCourse appCourse = new AppCourse();
					appCourse.setId(cId);
					appCourse.setName(new String(courseName.getBytes("ISO-8859-1"), "UTF-8"));
					Integer id = appCourseService.insert(appCourse);
					if(id == null || id <= 0){
						setResponseFlag(ExamConstant.RESPONSE_FAILURE);
						setResponseMsg("Fail to add course!");
					}else{
						setResponseFlag(ExamConstant.RESPONSE_SUCCESS);
					}
				}
			}catch(Exception e){
				log.error("Server exception when adding course!", e);
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg("Server exception!");
			}
		}
		
		writeResponse();
		return null;
	}

	@Action(value="modCourse")
	public String modCourse() {
		if(!validCourseId(courseId) || courseName == null || "".equals(courseName.trim())){
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg("Illegal Argument!");
		}else{
			try{
				Integer cId = Integer.parseInt(courseId);
				AppCourse ac = appCourseService.findById(cId);
				if(ac == null){
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg("Course  does not exist!");
				}else{
					ac.setName(new String(courseName.getBytes("ISO-8859-1"), "UTF-8"));
					Integer id = appCourseService.saveOrUpdate(ac);
					if(id == null || id <= 0){
						setResponseFlag(ExamConstant.RESPONSE_FAILURE);
						setResponseMsg("Fail to add course!");
					}else{
						setResponseFlag(ExamConstant.RESPONSE_SUCCESS);
					}
				}
			}catch(Exception e){
				log.error("Server exception when mod course!", e);
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg("Server exception!");
			}
		}
		
		writeResponse();
		return null;
	}

	@Action(value="delCourse")
	public String delCourse() {
		if(!validCourseId(courseId)){
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg("Illegal Argument!");
		}else{
			try{
				Integer cId = Integer.parseInt(courseId);
				AppCourse ac = appCourseService.findById(cId);
				if(ac == null){
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg("Course  does not exist!");
				}else{
					appCourseService.remove(cId);
					setResponseFlag(ExamConstant.RESPONSE_SUCCESS);
				}
			}catch(Exception e){
				log.error("Server exception when del course!", e);
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg("Server exception!");
			}
		}
		
		writeResponse();
		return null;
	}
	
	/**
	 *  验证课程ID是否合法
	 * @param courseId		课程ID
	 * @return 					true/false
	 * @author Lisz
	 * @date  2014年4月23日
	 */
	private boolean validCourseId(String courseId){
		boolean result = false;
		if(courseId != null){
			try{
				int cId = Integer.parseInt(courseId);
				if(cId > 0){
					result = true;
				}
			}catch(Exception e){
				log.error("Server exception!", e);
			}
		}
		return result;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
}
