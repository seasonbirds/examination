package com.vnetoo.examination.policy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.vnetoo.common.AppContext;
import com.vnetoo.common.SystemGlobals;
import com.vnetoo.examination.FreeMarketUtil;
import com.vnetoo.examination.SystemKeys;
import com.vnetoo.examination.enums.QuestionType;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.dao.ExamPaperDAO;
import com.vnetoo.examination.paper.dao.PaperQuestionRelDAO;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.dao.ExamPolicyDetailDAO;
import com.vnetoo.examination.question.bo.ExamAnswer;
import com.vnetoo.examination.question.dao.ExamAnswerDAO;
import com.vnetoo.examination.question.dao.ExamQuestionDAO;

public abstract class AbstractPaperBuilder implements PaperBuilder {
	//用户ID
	protected Integer userId;
	//考试策略
	protected ExamPolicy examPolicy; 
	//知识点列表
	protected List<Integer> examKeyPointList;
	//考试类型
	protected String rangeType;
	//其它考试信息，策略生成的试卷中试题占其它策略试题的比例，包括试卷ID和百分比
	//如：ID,百分比;   1,30
	protected String otherExamInfo;
	
	protected ExamPolicyDetailDAO examPolicyDetailDAO;
	protected ExamQuestionDAO examQuestionDAO;
	protected ExamPaperDAO examPaperDAO;
	protected PaperQuestionRelDAO paperQuestionRelDAO;
	protected ExamAnswerDAO examAnswerDAO;
	
	/**
	 * 初始化试卷生成类
	 */
	public void init(Integer userId, ExamPolicy examPolicy, List<Integer> examKeyPointList, String rangeType, String otherExamInfo){
		this.userId = userId;
		this.examPolicy = examPolicy;
		this.examKeyPointList = examKeyPointList;
		this.rangeType = rangeType;
		this.otherExamInfo = otherExamInfo;
		examPolicyDetailDAO = (ExamPolicyDetailDAO) AppContext.getBean("examPolicyDetailDAOImpl");
		examQuestionDAO = (ExamQuestionDAO) AppContext.getBean("examQuestionDAOImpl");
		examPaperDAO = (ExamPaperDAO) AppContext.getBean("examPaperDAOImpl");
		paperQuestionRelDAO = (PaperQuestionRelDAO) AppContext.getBean("paperQuestionRelDAOImpl");
		examAnswerDAO = (ExamAnswerDAO) AppContext.getBean("examAnswerDAOImpl");
	}
	
	/**
	 * 试卷静态化
	 */
	public String toStatic(String uuid, ExamPolicy examPolicy, List<PaperQuestion> questionList){
		if(examPolicy == null || questionList == null || questionList.isEmpty() || examAnswerDAO == null){
			return null;
		}
		
		HashMap<String, Object>map = new HashMap<String, Object>();
		map.put("webRoot", SystemGlobals.getValue(SystemKeys.WEB_ROOT));
		map.put("examPolicy", examPolicy);
		//单选题列表
		List<PaperQuestion> singleList = new ArrayList<PaperQuestion>();
		//多选题列表
		List<PaperQuestion> multList = new ArrayList<PaperQuestion>();
		//判断题列表
		 List<PaperQuestion> judgeList = new ArrayList<PaperQuestion>();
		//填空题列表
		List<PaperQuestion> clozeList = new ArrayList<PaperQuestion>();
		//名称解释题列表
		List<PaperQuestion> explainList = new ArrayList<PaperQuestion>();
		//问答题列表
		List<PaperQuestion> qnqList = new ArrayList<PaperQuestion>();
		for(PaperQuestion question : questionList){
			QuestionType type = question.getQuestionType();
			if(type == QuestionType.SINGLE){
				ExamAnswer examAnswer = new ExamAnswer();
				examAnswer.setQuestionId(question.getQuestionId());
				List<ExamAnswer> answerList = examAnswerDAO.selectList(examAnswer);
				question.setAnswerList(answerList);
				singleList.add(question);
			}else if(type == QuestionType.MULT){
				ExamAnswer examAnswer = new ExamAnswer();
				examAnswer.setQuestionId(question.getQuestionId());
				List<ExamAnswer> answerList = examAnswerDAO.selectList(examAnswer);
				question.setAnswerList(answerList);
				multList.add(question);
			}else if(type == QuestionType.JUDGE){
				ExamAnswer examAnswer = new ExamAnswer();
				examAnswer.setQuestionId(question.getQuestionId());
				List<ExamAnswer> answerList = examAnswerDAO.selectList(examAnswer);
				question.setAnswerList(answerList);
				judgeList.add(question);
			}else if(type == QuestionType.CLOZE){
				clozeList.add(question);
			}else if(type == QuestionType.EXPLAIN){
				explainList.add(question);
			}else if(type == QuestionType.QNQ){
				qnqList.add(question);
			}
		}
		map.put("singleList", singleList);
		map.put("multList", multList);
		map.put("judgeList", judgeList);
		map.put("clozeList", clozeList);
		map.put("explainList", explainList);
		map.put("qnqList", qnqList);
		map.put("paperGuid", uuid);
		//生成试卷静态页面
		FreeMarketUtil freeMarketUtil = new FreeMarketUtil();
		String paperUrl = freeMarketUtil.processPaperTemplate(map, uuid);
		
		return paperUrl;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public ExamPolicy getExamPolicy() {
		return examPolicy;
	}
	public void setExamPolicy(ExamPolicy examPolicy) {
		this.examPolicy = examPolicy;
	}
	public List<Integer> getExamKeyPointList() {
		return examKeyPointList;
	}
	public void setExamKeyPointList(List<Integer> examKeyPointList) {
		this.examKeyPointList = examKeyPointList;
	}

	public String getRangeType() {
		return rangeType;
	}

	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}

	public String getOtherExamInfo() {
		return otherExamInfo;
	}

	public void setOtherExamInfo(String otherExamInfo) {
		this.otherExamInfo = otherExamInfo;
	}
	
}
