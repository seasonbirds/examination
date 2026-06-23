package com.vnetoo.examination.question.bo;

/**
 * @comment 答案表
 * @author chenh
 * @date 2013-11-08
 */
@SuppressWarnings("serial")
public abstract class AbstractExamAnswer implements java.io.Serializable{
	/**
	 * @alias 编号
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 答案内容
	 */
	protected java.lang.String content;
	/**
	 * @alias 是否正确答案（１正确，０错误）
	 */
	protected java.lang.String valid;
	/**
	 * @alias null
	 */
	protected java.util.Date lastUpdateDate;
	/**
	 * @alias null
	 */
	protected java.lang.Integer lastUpdatedBy;
	/**
	 * @alias null
	 */
	protected java.util.Date creationDate;
	/**
	 * @alias null
	 */
	protected java.lang.Integer createdBy;
	/**
	 * @alias null
	 */
	protected java.lang.Integer enabledFlag;
	/**
	 * @alias 序号
	 */
	protected java.lang.String answerIdx;
	/**
	 * @alias 问题ID
	 */
	protected java.lang.Integer questionId;

	public void setId(java.lang.Integer id){
		 this.id=id;
	}
	public java.lang.Integer getId(){
		 return this.id;
	}
	public void setContent(java.lang.String content){
		 this.content=content;
	}
	public java.lang.String getContent(){
		 return this.content;
	}
	public void setValid(java.lang.String valid){
		 this.valid=valid;
	}
	public java.lang.String getValid(){
		 return this.valid;
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
	public void setAnswerIdx(java.lang.String answerIdx){
		 this.answerIdx=answerIdx;
	}
	public java.lang.String getAnswerIdx(){
		 return this.answerIdx;
	}
	public void setQuestionId(java.lang.Integer questionId){
		 this.questionId=questionId;
	}
	public java.lang.Integer getQuestionId(){
		 return this.questionId;
	}
}