package com.vnetoo.examination.question.bo;

import com.vnetoo.examination.enums.QuestionLevel;
import com.vnetoo.examination.enums.QuestionRangeType;
import com.vnetoo.examination.enums.QuestionType;

/**
 * @comment 题目表
 * @author chenh
 * @date 2013-11-08
 */
@SuppressWarnings("serial")
public abstract class AbstractExamQuestion implements java.io.Serializable{
	/**
	 * @alias 编号
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 题目代号
	 */
	protected java.lang.String code;
	/**
	 * @alias 知识点ID
	 */
	protected java.lang.Integer keyPointId;
	/**
	 * @alias 难度等级（1简单，2中等，2困难）
	 */
	protected QuestionLevel lev;
	/**
	 * @alias 问题内容
	 */
	protected java.lang.String content;
	/**
	 * @alias 题目类型（客观：1单选，2多选，3判断；主观：4问答，5填空，6论述，7计算）
	 */
	protected QuestionType type;
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
	 * @alias 有效标识0 无效；1有效
	 */
	protected java.lang.Integer enabledFlag;
	/**
	 * @alias 题目解析
	 */
	protected java.lang.String parse;
	/**
	 * @alias 题目类型（练习题，考试题目，入学考试题...）
	 */
	protected QuestionRangeType rangeType;

	public void setId(java.lang.Integer id){
		 this.id=id;
	}
	public java.lang.Integer getId(){
		 return this.id;
	}
	public void setCode(java.lang.String code){
		 this.code=code;
	}
	public java.lang.String getCode(){
		 return this.code;
	}
	public void setKeyPointId(java.lang.Integer keyPointId){
		 this.keyPointId=keyPointId;
	}
	public java.lang.Integer getKeyPointId(){
		 return this.keyPointId;
	}
	public void setLev(QuestionLevel lev){
		 this.lev=lev;
	}
	public QuestionLevel getLev(){
		 return this.lev;
	}
	public void setContent(java.lang.String content){
		 this.content=content;
	}
	public java.lang.String getContent(){
		 return this.content;
	}
	public void setType(QuestionType type){
		 this.type=type;
	}
	public QuestionType getType(){
		 return this.type;
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
	public void setParse(java.lang.String parse){
		 this.parse=parse;
	}
	public java.lang.String getParse(){
		 return this.parse;
	}
	public void setRangeType(QuestionRangeType rangeType){
		 this.rangeType=rangeType;
	}
	public QuestionRangeType getRangeType(){
		 return this.rangeType;
	}
}