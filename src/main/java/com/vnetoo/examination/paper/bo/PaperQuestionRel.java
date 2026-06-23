package com.vnetoo.examination.paper.bo;

import com.vnetoo.common.bo.BaseObject;

public class PaperQuestionRel implements BaseObject {

	private Integer papersId;
	
	private Integer questionId;
	
	private Integer score;
	
	public Integer getId() {
		return null;
	}

	public Integer getPapersId() {
		return papersId;
	}

	public void setPapersId(Integer papersId) {
		this.papersId = papersId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

}
