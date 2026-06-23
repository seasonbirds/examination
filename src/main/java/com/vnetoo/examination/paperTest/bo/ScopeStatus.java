package com.vnetoo.examination.paperTest.bo;

public class ScopeStatus {

	private Integer scopeId;
	
	private String name;
	//0：参加考试 1：继续考试 2：考试结束
	private Integer examStatus;

	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(Integer examStatus) {
		this.examStatus = examStatus;
	}
}
