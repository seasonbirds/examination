package com.vnetoo.examination.policy.bo;

import com.vnetoo.common.enums.Bool;
import com.vnetoo.examination.enums.Assemble;

/**
 * @comment 出题策略
 * @author chenh
 * @date 2013-11-13
 */
@SuppressWarnings("serial")
public abstract class AbstractExamPolicy implements java.io.Serializable{
	/**
	 * @alias 答题时间（分钟，０或空为不限时）
	 */
	protected java.lang.Integer time;
	/**
	 * @alias 总分
	 */
	protected java.lang.Integer totalScore;
	/**
	 * @alias 及格分
	 */
	protected java.lang.Integer passScore;
	/**
	 * @alias 考试开放开始时间
	 */
	protected java.util.Date examTimeStart;
	/**
	 * @alias 考试开放结束时间
	 */
	protected java.util.Date examTimeEnd;
	/**
	 * @alias 立即显示答案
	 */
	protected Bool showAnswer;
	/**
	 * @alias 立即显示分数
	 */
	protected Bool showScore;
	/**
	 * @alias 是否保存试卷
	 */
	protected Bool savePage;
	/**
	 * @alias 是否统一试卷
	 */
	protected Bool isUnify;
	/**
	 * @alias 备注
	 */
	protected java.lang.String memo;
	/**
	 * @alias 最后更新日期
	 */
	protected java.util.Date lastUpdateDate;
	/**
	 * @alias 最后更新人
	 */
	protected java.lang.Integer lastUpdatedBy;
	/**
	 * @alias 创建日期
	 */
	protected java.util.Date creationDate;
	/**
	 * @alias 创建人
	 */
	protected java.lang.Integer createdBy;
	/**
	 * @alias 有效标识
	 */
	protected java.lang.Integer enabledFlag;
	/**
	 * @alias 试题套数
	 */
	protected java.lang.Integer paperCount;
	/**
	 * @alias 自动出题周期（单位：天，0表示不执行）
	 */
	protected java.lang.Integer generateCycle;
	/**
	 * @alias 出题方式
	 */
	protected Assemble type;
	/**
	 * @alias 主键
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 策略名称
	 */
	protected java.lang.String name;

	public void setTime(java.lang.Integer time){
		 this.time=time;
	}
	public java.lang.Integer getTime(){
		 return this.time;
	}
	public void setTotalScore(java.lang.Integer totalScore){
		 this.totalScore=totalScore;
	}
	public java.lang.Integer getTotalScore(){
		 return this.totalScore;
	}
	public void setPassScore(java.lang.Integer passScore){
		 this.passScore=passScore;
	}
	public java.lang.Integer getPassScore(){
		 return this.passScore;
	}
	public void setExamTimeStart(java.util.Date examTimeStart){
		 this.examTimeStart=examTimeStart;
	}
	public java.util.Date getExamTimeStart(){
		 return this.examTimeStart;
	}
	public void setExamTimeEnd(java.util.Date examTimeEnd){
		 this.examTimeEnd=examTimeEnd;
	}
	public java.util.Date getExamTimeEnd(){
		 return this.examTimeEnd;
	}
	
	public void setMemo(java.lang.String memo){
		 this.memo=memo;
	}
	public java.lang.String getMemo(){
		 return this.memo;
	}
	public void setLastUpdateDate(java.util.Date lastUpdateDate){
		 this.lastUpdateDate=lastUpdateDate;
	}
	public java.util.Date getLastUpdateDate(){
		 return this.lastUpdateDate;
	}
	public void setLastUpdatedBy(java.lang.Integer lastUpdatedBy){
		 this.lastUpdatedBy=lastUpdatedBy;
	}
	public java.lang.Integer getLastUpdatedBy(){
		 return this.lastUpdatedBy;
	}
	public void setCreationDate(java.util.Date creationDate){
		 this.creationDate=creationDate;
	}
	public java.util.Date getCreationDate(){
		 return this.creationDate;
	}
	public void setCreatedBy(java.lang.Integer createdBy){
		 this.createdBy=createdBy;
	}
	public java.lang.Integer getCreatedBy(){
		 return this.createdBy;
	}
	public void setEnabledFlag(java.lang.Integer enabledFlag){
		 this.enabledFlag=enabledFlag;
	}
	public java.lang.Integer getEnabledFlag(){
		 return this.enabledFlag;
	}
	public void setPaperCount(java.lang.Integer paperCount){
		 this.paperCount=paperCount;
	}
	public java.lang.Integer getPaperCount(){
		 return this.paperCount;
	}
	public void setGenerateCycle(java.lang.Integer generateCycle){
		 this.generateCycle=generateCycle;
	}
	public java.lang.Integer getGenerateCycle(){
		 return this.generateCycle;
	}
	public void setType(Assemble type){
		 this.type=type;
	}
	public Assemble getType(){
		 return this.type;
	}
	public void setId(java.lang.Integer id){
		 this.id=id;
	}
	public java.lang.Integer getId(){
		 return this.id;
	}
	public void setName(java.lang.String name){
		 this.name=name;
	}
	public java.lang.String getName(){
		 return this.name;
	}
	public Bool getShowAnswer() {
		return showAnswer;
	}
	public void setShowAnswer(Bool showAnswer) {
		this.showAnswer = showAnswer;
	}
	public Bool getShowScore() {
		return showScore;
	}
	public void setShowScore(Bool showScore) {
		this.showScore = showScore;
	}
	public Bool getSavePage() {
		return savePage;
	}
	public void setSavePage(Bool savePage) {
		this.savePage = savePage;
	}
	public Bool getIsUnify() {
		return isUnify;
	}
	public void setIsUnify(Bool isUnify) {
		this.isUnify = isUnify;
	}
}