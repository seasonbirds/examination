/**
 * 
 */
package com.vnetoo.examination.paper.bo;

import java.util.List;
/**
 * 用户页面显示的试题内容
 */
import com.vnetoo.examination.enums.QuestionType;
import com.vnetoo.examination.question.bo.ExamAnswer;

public class PaperQuestion {
	//试题ID
	private Integer questionId;
	//试题内容
	private String questionContent;
	//试题类型
	private QuestionType questionType;
	//试题分数
	private Integer questionScore;
	//试题答案列表
	private List<ExamAnswer> answerList;
	//试题正确答案列表
	private List<ExamAnswer> correctAnswerList;
	//试题得分
	private Integer answerScore;
	//客观题所有选项的列表：仅在阅卷时使用
	private List<ExamAnswer> qAnswerList;
	
	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public String getQuestionContent() {
		return questionContent;
	}

	public void setQuestionContent(String questionContent) {
		this.questionContent = questionContent;
	}

	public QuestionType getQuestionType() {
		return questionType;
	}

	public void setQuestionType(QuestionType questionType) {
		this.questionType = questionType;
	}

	public Integer getQuestionScore() {
		return questionScore;
	}

	public void setQuestionScore(Integer questionScore) {
		this.questionScore = questionScore;
	}

	public List<ExamAnswer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<ExamAnswer> answerList) {
		this.answerList = answerList;
	}

	public List<ExamAnswer> getCorrectAnswerList() {
		return correctAnswerList;
	}

	public void setCorrectAnswerList(List<ExamAnswer> correctAnswerList) {
		this.correctAnswerList = correctAnswerList;
	}

	public Integer getAnswerScore() {
		return answerScore;
	}

	public void setAnswerScore(Integer answerScore) {
		this.answerScore = answerScore;
	}

	public List<ExamAnswer> getqAnswerList() {
		return qAnswerList;
	}

	public void setqAnswerList(List<ExamAnswer> qAnswerList) {
		this.qAnswerList = qAnswerList;
	}
}
