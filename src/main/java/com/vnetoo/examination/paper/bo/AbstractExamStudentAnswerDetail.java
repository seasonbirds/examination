package com.vnetoo.examination.paper.bo;

import java.util.Date;

@SuppressWarnings("serial")
public abstract class AbstractExamStudentAnswerDetail implements java.io.Serializable {
	/**
	 * ID
	 */
	protected Integer id;
	/**
	 * 学生的考试答题表id
	 */
	protected Integer studentAnswerId;
	/**
	 * 学生所选问题id
	 */
	protected Integer questionId;
	/**
	 * 学生主观题所写答案
	 */
	protected String answerContent;
	/**
	 * 得分
	 */
	protected Integer score;
	/**
	 * 试卷批改人的Id
	 */
	protected Integer reviewerId;
	/**
	 * 试卷批改的日期
	 */
	protected Date reviewDate;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getStudentAnswerId() {
		return studentAnswerId;
	}
	public void setStudentAnswerId(Integer studentAnswerId) {
		this.studentAnswerId = studentAnswerId;
	}
	public Integer getQuestionId() {
		return questionId;
	}
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public String getAnswerContent() {
		return answerContent;
	}
	public void setAnswerContent(String answerContent) {
		this.answerContent = answerContent;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Integer getReviewerId() {
		return reviewerId;
	}
	public void setReviewerId(Integer reviewerId) {
		this.reviewerId = reviewerId;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
}
