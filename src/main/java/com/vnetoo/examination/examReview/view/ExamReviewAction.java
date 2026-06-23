package com.vnetoo.examination.examReview.view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.stereotype.Controller;

import com.vnetoo.common.SystemGlobals;
import com.vnetoo.common.view.BaseAction;
import com.vnetoo.examination.ExamConstant;
import com.vnetoo.examination.app.bo.AppScope;
import com.vnetoo.examination.app.bo.ScopeCourseRel;
import com.vnetoo.examination.app.bo.ScopePolicyRel;
import com.vnetoo.examination.app.service.AppScopeService;
import com.vnetoo.examination.app.service.ScopeCourseRelService;
import com.vnetoo.examination.app.service.ScopePolicyRelService;
import com.vnetoo.examination.enums.QuestionType;
import com.vnetoo.examination.examReview.bo.ExamReviewInfo;
import com.vnetoo.examination.examReview.service.ExamReviewInfoService;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.ExamStudentAnswer;
import com.vnetoo.examination.paper.bo.ExamStudentAnswerDetail;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.service.ExamPaperService;
import com.vnetoo.examination.paper.service.ExamStudentAnswerDetailService;
import com.vnetoo.examination.paper.service.ExamStudentAnswerService;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.service.ExamPolicyService;
import com.vnetoo.examination.question.bo.ExamAnswer;
import com.vnetoo.examination.question.service.ExamAnswerService;
import com.vnetoo.webservice.HttpClientUtil;

import freemarker.ext.beans.ResourceBundleModel;

@Controller
@ParentPackage("default")
@Namespace("/")
@Action(value = "examReview", results = {
		@Result(name = "search", type = "freemarker", location = "examReview/search.htm"),
		@Result(name = "list", type = "freemarker", location = "examReview/list.htm"),
		@Result(name = "review", type = "freemarker", location = "examReview/review.htm")})
public class ExamReviewAction extends BaseAction<ExamReviewInfo> {
	private static final long serialVersionUID = 7846777506818970566L;
	
	@Resource
	private ExamReviewInfoService examReviewInfoService;
	@Resource
	private ExamPolicyService examPolicyService;
	@Resource
	private ExamStudentAnswerDetailService examStudentAnswerDetailService;
	@Resource
	private ExamPaperService examPaperService;
	@Resource
	private ExamAnswerService examAnswerService;
	@Resource
	private ExamStudentAnswerService examStudentAnswerService;
	@Resource
	private ScopePolicyRelService scopePolicyRelService;
	@Resource
	private AppScopeService appScopeService;
	@Resource
	private ScopeCourseRelService scopeCourseRelService;
	@Resource
	private Map<String, String> scoreCallbackUrls;
	
	private ExamReviewInfo examReviewInfo = new ExamReviewInfo();
	private ExamPolicy examPolicy;
	
	//单选题列表
	private List<PaperQuestion> singleList = new ArrayList<PaperQuestion>();
	//多选题列表
	private List<PaperQuestion> multList = new ArrayList<PaperQuestion>();
	//判断题列表
	private List<PaperQuestion> judgeList = new ArrayList<PaperQuestion>();
	//填空题列表
	private List<PaperQuestion> clozeList = new ArrayList<PaperQuestion>();
	//名称解释题列表
	private List<PaperQuestion> explainList = new ArrayList<PaperQuestion>();
	//问答题列表
	private List<PaperQuestion> qnqList = new ArrayList<PaperQuestion>();
	
	private Integer paperId;
	private Integer studentId;
	//主观题ID、分数字符串
	private String[] subjectScore;
	
	private String dialogType;

	public String search(){
		query();
		return RESULT_SEARCH;
	}
	
	public String query(){
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
			}
			paginator.setResultList(examReviewInfoList);
		}
		return RESULT_LIST;
	}
	
	public String goReview(){
		try{
			if(paperId == null || paperId < 0 || studentId == null || studentId < 0){
				this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			}else{
				//试卷
				ExamPaper examPaper = examPaperService.findById(paperId);
				//答题记录(客观题)
				ExamStudentAnswer studentAnswer = new ExamStudentAnswer();
				studentAnswer.setPapersId(paperId);
				studentAnswer.setStudentId(studentId);
				studentAnswer = examStudentAnswerService.dynamicQuery(studentAnswer).get(0);
				//查找学生答题详情(主观题)
				ExamStudentAnswerDetail examStudentAnswerDetail = new ExamStudentAnswerDetail();
				examStudentAnswerDetail.setStudentAnswerId(studentAnswer.getId());
				List<ExamStudentAnswerDetail>answerDetailList = examStudentAnswerDetailService.dynamicQuery(examStudentAnswerDetail);
				
				//查找试卷对应的策略
				examPolicy = examPolicyService.findById(examPaper.getExamPolicyId());
				
				//获取试卷所有试题
				List<PaperQuestion> questionList = examPaperService.getQuestionByPaperId(paperId);
				//获取试卷试题所有答案
				List<ExamAnswer> answerList = examAnswerService.getAllAnswersByPaperId(paperId);
				
				//试题对应的答案
				Map<Integer, List<ExamAnswer>> questionAnswerMap = new HashMap<Integer, List<ExamAnswer>>();
				//学生答题的客观题答案
				Map<Integer, List<Integer>> studentObjectiveAnswerMap = new HashMap<Integer, List<Integer>>();
				//学生答题的主观题答案
				Map<Integer, ExamStudentAnswerDetail> studentSubjectiveAnswerMap = new HashMap<Integer, ExamStudentAnswerDetail>();
				//构造试题和答案、学生答题的主观、客观题答案对应的map
				String answerIds = studentAnswer.getAnswerId() != null ?  studentAnswer.getAnswerId() : "";
				List<String> answerIdList =Arrays.asList(answerIds.split(","));
				for(ExamAnswer examAnswer : answerList){
					Integer questionId = examAnswer.getQuestionId();
					//试题和答案对应的Map
					if(questionAnswerMap.containsKey(questionId)){
						questionAnswerMap.get(questionId).add(examAnswer);
					}else{
						List<ExamAnswer> list = new ArrayList<ExamAnswer>();
						list.add(examAnswer);
						questionAnswerMap.put(questionId, list);
					}
					//学生答题的客观题的试题和答案对应的map
					Integer answerId = examAnswer.getId();
					if(answerIdList.contains(String.valueOf(answerId))){
						if(studentObjectiveAnswerMap.containsKey(questionId)){
							studentObjectiveAnswerMap.get(questionId).add(answerId);
						}else{
							List<Integer> list = new ArrayList<Integer>();
							list.add(answerId);
							studentObjectiveAnswerMap.put(questionId, list);
						}
					}
				}
				//学生答题的主观题的试题和答案对应的map
				for(ExamStudentAnswerDetail answerDetail : answerDetailList){
					studentSubjectiveAnswerMap.put(answerDetail.getQuestionId(), answerDetail);
				}
				//TODO
				//设置考题、学生的答案、试题的正确答案、试题的得分
				for(PaperQuestion paperQuestion : questionList){
					QuestionType type = paperQuestion.getQuestionType();
					Integer questionId = paperQuestion.getQuestionId();
					List<ExamAnswer> al = questionAnswerMap.get(questionId);
					switch(type){
						case SINGLE :
						case MULT :
						case JUDGE :
							List<Integer> sal = studentObjectiveAnswerMap.get(questionId);
							//学生答题的答案列表
							List<ExamAnswer> qal = new ArrayList<ExamAnswer>();
							//正确答案列表
							List<ExamAnswer> cqal = new ArrayList<ExamAnswer>();
							if(sal != null && !sal.isEmpty()){
								for(ExamAnswer answer : al){
									if(sal.contains(answer.getId())){
										qal.add(answer);
									}
								}
							}
							for(ExamAnswer answer : al){
								if("1".equals(answer.getValid())){
									cqal.add(answer);
								}
							}
							//计算本题得分
							if(qal.size() == cqal.size()){
								int arraySize = qal.size();
								//题目回答的答案Id数组
								int[] answerIdArray = new int[arraySize];
								//题目正确答案的ID数组
								int[] cAnswerIdArray = new int[arraySize];
								for(int i=0; i<arraySize; i++){
									answerIdArray[i] = qal.get(i).getId();
									cAnswerIdArray[i] = cqal.get(i).getId();
								}
								
								Arrays.sort(answerIdArray);
								Arrays.sort(cAnswerIdArray);
								if(Arrays.equals(answerIdArray, cAnswerIdArray)){
									paperQuestion.setAnswerScore(paperQuestion.getQuestionScore());
								}else{
									paperQuestion.setAnswerScore(0);
								}
								
							}else{
								paperQuestion.setAnswerScore(0);
							}
							paperQuestion.setqAnswerList(al);
							paperQuestion.setAnswerList(qal);
							paperQuestion.setCorrectAnswerList(cqal);
							if(type == QuestionType.SINGLE){
								singleList.add(paperQuestion);
							}else if(type == QuestionType.MULT){
								multList.add(paperQuestion);
							}else{
								judgeList.add(paperQuestion);
							}
							break;
						case CLOZE :
						case EXPLAIN :
						case QNQ :
							paperQuestion.setCorrectAnswerList(questionAnswerMap.get(questionId));
							ExamStudentAnswerDetail esad = studentSubjectiveAnswerMap.get(questionId);
							List<ExamAnswer> l = new ArrayList<ExamAnswer>();
							if(esad != null ){
								ExamAnswer ead = new ExamAnswer();
								ead.setQuestionId(esad.getQuestionId());
								ead.setContent(esad.getAnswerContent());
								l.add(ead);
								if(esad.getScore() != null){
									paperQuestion.setAnswerScore(esad.getScore());
								}
							}else{
								//未作答的统一设为0分
								paperQuestion.setAnswerScore(0);
							}
							paperQuestion.setAnswerList(l);
							if(type == QuestionType.CLOZE){
								clozeList.add(paperQuestion);
							}else if(type == QuestionType.EXPLAIN){
								explainList.add(paperQuestion);
							}else{
								qnqList.add(paperQuestion);
							}
							
							break;
						default :
								break;
					}
				}
			}
		}catch(Exception e){
			this.setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			this.setResponseMsg("服务器异常");
		}
		return "review";
	}
	
	public String review(){
		ResourceBundleModel rbm = this.getResourceBundle();
		try{
			if(subjectScore == null|| paperId == null || paperId <=0 
					|| studentId == null || studentId <=0){
				setResponseFlag(ExamConstant.RESPONSE_FAILURE);
				setResponseMsg( rbm.format("PARAMETER.ERROR", null));
			}else{
				//通过试卷Id和学生Id查找答题记录
				ExamStudentAnswer studentAnswer = new ExamStudentAnswer();
				studentAnswer.setPapersId(paperId);
				studentAnswer.setStudentId(studentId);
				List<ExamStudentAnswer> answerList = examStudentAnswerService.dynamicQuery(studentAnswer);
				if(answerList != null && !answerList.isEmpty()){
					studentAnswer = answerList.get(0);
					//通过学生答题记录查找主观题答题详情
					ExamStudentAnswerDetail esad = new ExamStudentAnswerDetail();
					 esad.setStudentAnswerId(studentAnswer.getId());
					List<ExamStudentAnswerDetail>esadList =examStudentAnswerDetailService.dynamicQuery(esad);
					Map<Integer, ExamStudentAnswerDetail> map = new HashMap<Integer, ExamStudentAnswerDetail>();
					if(esadList != null && !esadList.isEmpty()){
						for(ExamStudentAnswerDetail detail : esadList){
							map.put(detail.getQuestionId(), detail);
						}
					}
					
					Integer userId = this.getCurrentUser().getId();
					//客观题的总得分
					int totalScore = 0;
					for(String idScore : subjectScore){
						String[] tmp =  idScore.split(",");
						Integer qId = Integer.parseInt(tmp[0]);
						Integer score = Integer.parseInt(tmp[1]);
						//未批改的题目跳过
						if(score == -1){
							continue;
						}
						//只更新学生答过的主观题，未答的试题分数为0
						if(map.containsKey(qId)){
							totalScore += score;
							ExamStudentAnswerDetail updateDetail = map.get(qId);
							updateDetail.setScore(score);
							updateDetail.setReviewerId(userId);
							updateDetail.setReviewDate(new Date());
							examStudentAnswerDetailService.saveOrUpdate(updateDetail);
						}
					}
					//跟新阅卷状态
					totalScore += studentAnswer.getScore() != null ? studentAnswer.getScore() : 0;
					studentAnswer.setScore(totalScore);
					studentAnswer.setReviewStatus(1);
					examStudentAnswerService.saveOrUpdate(studentAnswer);
					
					//回调成绩接口
					if(!SystemGlobals.getValueBool(ExamConstant.INTEGRATED_RUNNING)){
						ExamPaper paper = examPaperService.findById(paperId);
						ExamPolicy policy = examPolicyService.findById(paper.getExamPolicyId());
						ScopePolicyRel scopePolicyRel = new ScopePolicyRel();
						scopePolicyRel.setPolicyId(policy.getId());
						List<ScopePolicyRel> relList = scopePolicyRelService.dynamicQuery(scopePolicyRel);
						AppScope scope = appScopeService.findById(relList.get(0).getScopeId());
						String callbackUrl = scoreCallbackUrls.get(scope.getType());
						if(callbackUrl == null){
							callbackUrl = scoreCallbackUrls.get("DEFAULT");
						}
						if(callbackUrl != null){
							ScopeCourseRel scopeCourseRel = new ScopeCourseRel();
							scopeCourseRel.setScopeId(scope.getId());
							List<ScopeCourseRel>  scopeCourseRelList = scopeCourseRelService.dynamicQuery(scopeCourseRel);
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							HttpClientUtil util = new HttpClientUtil(callbackUrl);
							Map<String, String> params = new HashMap<String, String>();
							params.put("studentId", String.valueOf(userId));
							params.put("courseId", String.valueOf(scopeCourseRelList.get(0).getCourseId()));
							params.put("score", String.valueOf(totalScore));
							params.put("examId", String.valueOf(scope.getId()));
							params.put("beginTime", sdf.format(studentAnswer.getCreationDate()));
							params.put("endTime", sdf.format(studentAnswer.getUpdateDate()));
							params.put("businessInfo", studentAnswer.getBusinessInfo());
							util.postRequset(false, params);
						}
					}
				}else{
					setResponseFlag(ExamConstant.RESPONSE_FAILURE);
					setResponseMsg( rbm.format("SERVER.EXCEPTION", null));
				}
			}
		}catch(Exception e){
			setResponseFlag(ExamConstant.RESPONSE_FAILURE);
			setResponseMsg( rbm.format("SERVER.EXCEPTION", null));
		}
		writeResponse();
		return null;
	}
	
	public ExamReviewInfo getExamReviewInfo() {
		return examReviewInfo;
	}

	public void setExamReviewInfo(ExamReviewInfo examReviewInfo) {
		this.examReviewInfo = examReviewInfo;
	}

	public Integer getPaperId() {
		return paperId;
	}

	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public ExamPolicy getExamPolicy() {
		return examPolicy;
	}

	public void setExamPolicy(ExamPolicy examPolicy) {
		this.examPolicy = examPolicy;
	}

	public List<PaperQuestion> getSingleList() {
		return singleList;
	}

	public void setSingleList(List<PaperQuestion> singleList) {
		this.singleList = singleList;
	}

	public List<PaperQuestion> getMultList() {
		return multList;
	}

	public void setMultList(List<PaperQuestion> multList) {
		this.multList = multList;
	}

	public List<PaperQuestion> getJudgeList() {
		return judgeList;
	}

	public void setJudgeList(List<PaperQuestion> judgeList) {
		this.judgeList = judgeList;
	}

	public List<PaperQuestion> getClozeList() {
		return clozeList;
	}

	public void setClozeList(List<PaperQuestion> clozeList) {
		this.clozeList = clozeList;
	}

	public List<PaperQuestion> getExplainList() {
		return explainList;
	}

	public void setExplainList(List<PaperQuestion> explainList) {
		this.explainList = explainList;
	}

	public List<PaperQuestion> getQnqList() {
		return qnqList;
	}

	public void setQnqList(List<PaperQuestion> qnqList) {
		this.qnqList = qnqList;
	}

	public String[] getSubjectScore() {
		return subjectScore;
	}

	public void setSubjectScore(String[] subjectScore) {
		this.subjectScore = subjectScore;
	}

	public String getDialogType() {
		return dialogType;
	}

	public void setDialogType(String dialogType) {
		this.dialogType = dialogType;
	}
}
