package com.vnetoo.examination.paper.bo;

/**
 * 问题答案关系类
 */
public class QuestionAnswer {

	private Integer questionId;
	
	private Integer answerId;
	
	private Integer score;

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getAnswerId() {
		return answerId;
	}

	public void setAnswerId(Integer answerId) {
		this.answerId = answerId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
}
