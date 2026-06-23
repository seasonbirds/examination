package com.vnetoo.examination.examReview.bo;

import java.util.Date;
/**
 * 考试阅卷的信息
 */
public class ExamReviewInfo {
	/**
	 * 考试Id
	 */
	private Integer scopeId;
	/**
	 * 考试名称
	 */
	private String scopeName;
	/**
	 * 学生Id
	 */
	private Integer studentId;
	/**
	 * 学生账号
	 */
	private String studentAcct;
	/**
	 * 学生姓名
	 */
	private String studentName;
	/**
	 * 学生昵称
	 */
	private String nickName;
	/**
	 * 试卷Id
	 */
	private Integer paperId;
	/**
	 * 试卷的唯一标示符
	 */
	private String paperName;
	/**
	 * 考试时间
	 */
	private Date examTime;
	/**
	 * 策略Id
	 */
	private Integer policyId;
	/**
	 * 学生答题Id
	 */
	private Integer studentAnswerId;
	/**
	 * 客观题得分
	 */
	private Integer objectiveQuestionScore;
	/**
	 * 主观题得分
	 */
	private Integer subjectiveQuestionScore;
	/**
	 * 试卷总分
	 */
	private Integer totalScore;
	/**
	 * 试卷通过分
	 */
	private Integer passScore;
	/**
	 * 试卷审阅状态
	 */
	private Integer reviewStatus;
	/**
	 * 通过标志
	 */
	private Integer passFlag;
	
	public Integer getScopeId() {
		return scopeId;
	}
	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}
	public String getScopeName() {
		return scopeName;
	}
	public void setScopeName(String scopeName) {
		this.scopeName = scopeName;
	}
	public Integer getStudentId() {
		return studentId;
	}
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	public String getStudentAcct() {
		return studentAcct;
	}
	public void setStudentAcct(String studentAcct) {
		this.studentAcct = studentAcct;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public Integer getPaperId() {
		return paperId;
	}
	public void setPaperId(Integer paperId) {
		this.paperId = paperId;
	}
	public String getPaperName() {
		return paperName;
	}
	public void setPaperName(String paperName) {
		this.paperName = paperName;
	}
	public Date getExamTime() {
		return examTime;
	}
	public void setExamTime(Date examTime) {
		this.examTime = examTime;
	}
	public Integer getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}
	public Integer getStudentAnswerId() {
		return studentAnswerId;
	}
	public void setStudentAnswerId(Integer studentAnswerId) {
		this.studentAnswerId = studentAnswerId;
	}
	public Integer getObjectiveQuestionScore() {
		return objectiveQuestionScore;
	}
	public void setObjectiveQuestionScore(Integer objectiveQuestionScore) {
		this.objectiveQuestionScore = objectiveQuestionScore;
	}
	public Integer getSubjectiveQuestionScore() {
		return subjectiveQuestionScore;
	}
	public void setSubjectiveQuestionScore(Integer subjectiveQuestionScore) {
		this.subjectiveQuestionScore = subjectiveQuestionScore;
	}
	public Integer getTotalScore() {
		return totalScore;
	}
	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}
	public Integer getReviewStatus() {
		return reviewStatus;
	}
	public void setReviewStatus(Integer reviewStatus) {
		this.reviewStatus = reviewStatus;
	}
	public Integer getPassFlag() {
		return passFlag;
	}
	public void setPassFlag(Integer passFlag) {
		this.passFlag = passFlag;
	}
	public Integer getPassScore() {
		return passScore;
	}
	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}
}
