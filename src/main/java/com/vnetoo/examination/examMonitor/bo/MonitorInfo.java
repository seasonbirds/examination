package com.vnetoo.examination.examMonitor.bo;

import java.util.Date;
/**
 * 考试监控的信息
 */
public class MonitorInfo {
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
	 * 学生考试开始时间
	 */
	private Date startTime;
	/**
	 * 本场考试结束的时间
	 */
	private Date endTime;
	/**
	 * 考试总时间
	 */
	private Integer totalTime;
	/**
	 * 考试已进行时间
	 */
	private Integer examTime;
	/**
	 * 试卷Id
	 */
	private Integer paperId;
	/**
	 * 试卷的唯一标示符
	 */
	private String paperName;
	/**
	 * 策略Id
	 */
	private Integer policyId;
	/**
	 * 学生答题Id
	 */
	private Integer studentAnswerId;
	/**
	 * 学生答题的客观题答案
	 */
	private String answerQuestionIds;
	/**
	 * 总的题目数量
	 */
	private Integer totalQuestionNum;
	/**
	 * 已经答题的数量
	 */
	private Integer answerQuestionNum;
	/**
	 * 在线状态
	 */
	private Boolean onLine;
	
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
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Integer getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(Integer totalTime) {
		this.totalTime = totalTime;
	}
	public Integer getExamTime() {
		return examTime;
	}
	public void setExamTime(Integer examTime) {
		this.examTime = examTime;
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
	public String getAnswerQuestionIds() {
		return answerQuestionIds;
	}
	public void setAnswerQuestionIds(String answerQuestionIds) {
		this.answerQuestionIds = answerQuestionIds;
	}
	public Integer getTotalQuestionNum() {
		return totalQuestionNum;
	}
	public void setTotalQuestionNum(Integer totalQuestionNum) {
		this.totalQuestionNum = totalQuestionNum;
	}
	public Integer getAnswerQuestionNum() {
		return answerQuestionNum;
	}
	public void setAnswerQuestionNum(Integer answerQuestionNum) {
		this.answerQuestionNum = answerQuestionNum;
	}
	public Boolean getOnLine() {
		return onLine;
	}
	public void setOnLine(Boolean onLine) {
		this.onLine = onLine;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
