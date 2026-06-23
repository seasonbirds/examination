package com.vnetoo.examination.examReview.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.examReview.bo.ExamReviewInfo;
import com.vnetoo.examination.examReview.service.ExamReviewInfoService;
import com.vnetoo.examination.paper.service.ExamStudentAnswerDetailService;

@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value = "scoreManage", results = {
		@Result(name = "search", type = "freemarker", location = "scoreManage/search.htm"),
		@Result(name = "list", type = "freemarker", location = "scoreManage/list.htm")})
public class ScoreManageAction extends BaseAction<ExamReviewInfo> {

	private static final long serialVersionUID = -842906970875037600L;

	@Resource
	private ExamReviewInfoService examReviewInfoService;
	@Resource
	private ExamStudentAnswerDetailService examStudentAnswerDetailService;
	
	private ExamReviewInfo examReviewInfo = new ExamReviewInfo();
	
	public String search(){
		query();
		return RESULT_SEARCH;
	}
	
	public String query(){
		examReviewInfo.setReviewStatus(1);
		List<ExamReviewInfo> examReviewInfoList = examReviewInfoService.selectPage(examReviewInfo, paginator);
		if(examReviewInfoList != null && !examReviewInfoList.isEmpty()){
			List<Integer> policyIds = new ArrayList<Integer>();
			List<Integer> answerIds = new ArrayList<Integer>();
			//获取策略ID列表,去掉重复的值
			for(ExamReviewInfo eri : examReviewInfoList){
				answerIds.add(eri.getStudentAnswerId());
				Integer policyId = eri.getPolicyId();
				if(!policyIds.contains(policyId)){
					policyIds.add(eri.getPolicyId());
				}
			}
			
			//获取主观题得分
			Map<Integer, Integer> aIdScoreMap = examStudentAnswerDetailService.getScoreByAnswerId(answerIds);
			
			//设置阅卷信息中的值无法直接获得的信息
			for(ExamReviewInfo eri : examReviewInfoList){
				//设置主观题得分
				Integer answerId = eri.getStudentAnswerId();
				Integer subScore = 0;
				if(aIdScoreMap.containsKey(answerId)){
					subScore = aIdScoreMap.get(answerId);
				}
				eri.setSubjectiveQuestionScore(subScore);
				if(eri.getPassScore() <= (eri.getObjectiveQuestionScore() + subScore)){
					eri.setPassFlag(1);
				}else{
					eri.setPassFlag(0);
				}
			}
			paginator.setResultList(examReviewInfoList);
			
		}
		return RESULT_LIST;
	}

	public ExamReviewInfo getExamReviewInfo() {
		return examReviewInfo;
	}

	public void setExamReviewInfo(ExamReviewInfo examReviewInfo) {
		this.examReviewInfo = examReviewInfo;
	}
	
}
