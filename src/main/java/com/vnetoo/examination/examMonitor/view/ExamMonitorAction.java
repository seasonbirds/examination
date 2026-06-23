package com.vnetoo.examination.examMonitor.view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.examMonitor.bo.MonitorInfo;
import com.vnetoo.examination.examMonitor.service.MonitorInfoService;
import com.vnetoo.examination.paper.service.ExamStudentAnswerDetailService;
import com.vnetoo.examination.policy.service.ExamPolicyDetailService;

@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value = "examMonitor", results = {
		@Result(name = "search", type = "freemarker", location = "examMonitor/search.htm"),
		@Result(name = "list", type = "freemarker", location = "examMonitor/list.htm")})
public class ExamMonitorAction extends BaseAction<MonitorInfo> {

	private static final long serialVersionUID = -6179182164119201144L;
	
	@Resource
	private MonitorInfoService monitorInfoService;
	@Resource
	private ExamPolicyDetailService examPolicyDetailService;
	@Resource
	private ExamStudentAnswerDetailService examStudentAnswerDetailService;
	private MonitorInfo monitorInfo = new MonitorInfo();
	
	public String search(){
		query();
		return RESULT_SEARCH;
	}
	
	public String query(){
		List<MonitorInfo> monitorInfoList = monitorInfoService.selectPage(monitorInfo, paginator);
		if(monitorInfoList != null && !monitorInfoList.isEmpty()){
			List<Integer> policyIds = new ArrayList<Integer>();
			List<Integer> answerIds = new ArrayList<Integer>();
			//获取策略ID列表,去掉重复的值
			for(MonitorInfo mi : monitorInfoList){
				answerIds.add(mi.getStudentAnswerId());
				Integer policyId = mi.getPolicyId();
				if(!policyIds.contains(policyId)){
					policyIds.add(mi.getPolicyId());
				}
			}
			
			//获取策略中的总题量
			Map<Integer, Integer> epIdQnumMap = examPolicyDetailService.getQuestionNumberByPolicys(policyIds);
			//获取主观题已答题量
			Map<Integer, Integer> aIdAnumMap = examStudentAnswerDetailService.getAnswerNumberByAnswerId(answerIds);
			
			//设置监控信息中的值无法直接获得的信息
			for(MonitorInfo mi : monitorInfoList){
				Integer policyId = mi.getPolicyId();
				//设置已考时长
				Calendar c= Calendar.getInstance();
				Date timeEnd = mi.getEndTime();
				if(c.getTime().after(timeEnd)){
					mi.setExamTime(-1);
				}else{
					int examTime = (int)((c.getTimeInMillis() - mi.getStartTime().getTime())/(1000 * 60));
					mi.setExamTime(examTime);
				}
				//设置总题量
				mi.setTotalQuestionNum(epIdQnumMap.get(policyId));
				//设置已答题量
				int aNum = 0;
				//主观题已答题量
				if(aIdAnumMap.containsKey(mi.getStudentAnswerId())){
					aNum = aIdAnumMap.get(mi.getStudentAnswerId());
				}
				//客观题已答题量
				String questionIds = mi.getAnswerQuestionIds();
				if(questionIds != null && !"".equals(questionIds.trim())){
					String[] qIds = questionIds.split(",");
					aNum += qIds.length;
					mi.setAnswerQuestionIds("");
				}
				mi.setAnswerQuestionNum(aNum);
			}
				paginator.setResultList(monitorInfoList);
		}
		return RESULT_LIST;
	}

	public MonitorInfo getMonitorInfo() {
		return monitorInfo;
	}

	public void setMonitorInfo(MonitorInfo monitorInfo) {
		this.monitorInfo = monitorInfo;
	}
}
