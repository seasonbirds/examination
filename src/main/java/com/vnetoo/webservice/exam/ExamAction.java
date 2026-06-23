package com.vnetoo.webservice.exam;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.service.AppScopeService;
import com.vnetoo.examination.enums.QuestionRangeType;
import com.vnetoo.examination.paper.service.ExamStudentAnswerService;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.service.ExamPolicyService;

@Controller
@ParentPackage("default")
@Namespace("/services/exam")
/**
 * 考试系统提供的web service接口：获取考试的相关信息
 */
public class ExamAction extends BaseAction<Object> {

	private static final Logger log = LoggerFactory.getLogger(ExamAction.class);
	
	private static final long serialVersionUID = -4925727380359618273L;
	
	@Resource
	private AppScopeService appScopeService;
	@Resource
	private ExamPolicyService examPolicyService;
	@Resource
	private ExamStudentAnswerService examStudentAnswerService;
	//学生ID
	private String studentId;
	//课程ID
	private String courseId;
	//考试类型
	private String examType;

	@Action(value="getExamList")
	public String getExamList() {
		JSONObject json = new JSONObject();
		//参数验证
		if(!validParams(studentId, courseId, examType)){
			json.put("result", -1);
			json.put("msg", "Illegal Argument!");
		}else{
			try{
				//按考试类型和考试课程查询当前可考的考试列表
				List<AppScope> scopes = appScopeService.getAppScopeByTypeAndCourse(examType, Integer.parseInt(courseId));
				List<Integer> scopeIds = new ArrayList<Integer>();
				JSONArray examList = new JSONArray();
				if(scopes != null && !scopes.isEmpty()){
					for(AppScope as : scopes){
						scopeIds.add(as.getId());
					}
					//按考试列表查询相应的策略列表，并构建它们的对应关系
					List<ExamPolicy> policys = examPolicyService.getPolicyByScopeIds(scopeIds);
					Map<Integer, ExamPolicy>scopePolicyMap = new HashMap<Integer, ExamPolicy>();
					for(ExamPolicy ep : policys){
						Integer psId = ep.getScopeId();
						if(!scopePolicyMap.containsKey(psId)){
							scopePolicyMap.put(psId, ep);
						}
					}
					
					//删除未设定策略的考试
					for(int i=0; i<scopes.size(); i++){
						Integer sId = scopes.get(i).getId();
						if(!scopePolicyMap.containsKey(sId)){
							scopes.remove(i);
							scopeIds.remove(sId);
							i--;
						}
					}
					//查询考试列表中各考试的状态
					Map<Integer, Integer> scopeStatusMap = examStudentAnswerService.getAnswerStatusByScopeIdsAndSId(scopeIds, Integer.parseInt(studentId));
					Date date = new Date();
					for(AppScope scope : scopes){
						int scopeId = scope.getId();
						JSONObject item = new JSONObject();
						item.put("examId", scopeId);
						item.put("examName", scope.getName());
						ExamPolicy policy = scopePolicyMap.get(scopeId);
						int status = 0;
						//当前时间在考试时间开始之前：考试未开始
						if(date.before(policy.getExamTimeStart())){
							status = 0;
							//当前时间在考试结束时间之后：考试已结束
						}else if(date.after(policy.getExamTimeEnd())){
							status = 4;
						}else{
							//考试没有相关的状态：可以参加考试
							if(!scopeStatusMap.containsKey(scopeId)){
								status = 1;
							}else{
								int answerStatus = scopeStatusMap.get(scopeId);
								//考试的状态为：学生未交卷，可以继续考试
								if(answerStatus ==0 ){
									status = 2;
								//考试的状态我：学生已交卷。
								}else{
									status = 3;
								}
							}
						}
						
						item.put("status", status);
						examList.add(item);
					}
				}
				json.put("result", 0);
				json.put("studentId", Integer.parseInt(studentId));
				json.put("examList", examList);
			}catch(Exception e){
				log.error("Server exception when getting exam list.", e);
				json.put("result", -1);
				json.put("msg", "Server occur exception.");
			}
		}
		
		writeResponse(json.toJSONString());
		return null;
	}

	/**
	 * 
	 *@param studentId	学生ID
	 *@param courseId		课程ID
	 *@param examType	考试类型
	 *@return						true/false 验证结果：true，通过；false，不通过
	 * @author Lisz
	 * @date  2014年4月22日
	 */
	private boolean validParams(String studentId, String courseId, String examType){
		if(studentId == null || courseId == null || examType == null){
			return false;
		}
		
		boolean result = true;
		try{
			int sId = Integer.parseInt(studentId);
			int cId = Integer.parseInt(courseId);
			if(sId <= 0 || cId <= 0){
				result = false;
			}
			
			if(result){
				QuestionRangeType type = QuestionRangeType.getRangeType(examType);
				if(type == null){
					result = false;
				}
			}
		}catch(Exception e){
			log.error("Server exception.", e);
			result = false;
		}
		
		return result;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getExamType() {
		return examType;
	}

	public void setExamType(String examType) {
		this.examType = examType;
	}
	
}
