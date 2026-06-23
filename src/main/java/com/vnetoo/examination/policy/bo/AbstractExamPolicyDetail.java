package com.vnetoo.examination.policy.bo;

import com.vnetoo.examination.enums.QuestionLevel;
import com.vnetoo.examination.enums.QuestionType;

/**
 * @comment 出题策略明细
 * @author chenh
 * @date 2013-11-13
 */
@SuppressWarnings("serial")
public abstract class AbstractExamPolicyDetail implements java.io.Serializable{
	/**
	 * @alias 主键
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 策略ID
	 */
	protected java.lang.Integer examPolicyId;
	/**
	 * @alias 题目类型(CODE,单选,多选..)
	 */
	protected QuestionType type;
	/**
	 * @alias 题目难度(CODE,简单,困难..)
	 */
	protected QuestionLevel lev;
	/**
	 * @alias 题目个数
	 */
	protected java.lang.Integer questionCount;
	/**
	 * @alias 每题分数
	 */
	protected java.lang.Integer perScore;
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

	public void setId(java.lang.Integer id){
		 this.id=id;
	}
	public java.lang.Integer getId(){
		 return this.id;
	}
	public void setExamPolicyId(java.lang.Integer examPolicyId){
		 this.examPolicyId=examPolicyId;
	}
	public java.lang.Integer getExamPolicyId(){
		 return this.examPolicyId;
	}
	public void setType(QuestionType type){
		 this.type=type;
	}
	public QuestionType getType(){
		 return this.type;
	}
	public void setLev(QuestionLevel lev){
		 this.lev=lev;
	}
	public QuestionLevel getLev(){
		 return this.lev;
	}
	public void setQuestionCount(java.lang.Integer questionCount){
		 this.questionCount=questionCount;
	}
	public java.lang.Integer getQuestionCount(){
		 return this.questionCount;
	}
	public void setPerScore(java.lang.Integer perScore){
		 this.perScore=perScore;
	}
	public java.lang.Integer getPerScore(){
		 return this.perScore;
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
}