package com.vnetoo.examination.app.bo;

import com.vnetoo.examination.enums.QuestionRangeType;

/**
 * @comment 
 * @author chenh
 * @date 2013-11-11
 */
@SuppressWarnings("serial")
public abstract class AbstractAppScope implements java.io.Serializable{
	/**
	 * @alias 编号
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 名称
	 */
	protected java.lang.String name;
	
	/**
	 * @alias 考试类型
	 */
	protected QuestionRangeType type;
	
	/**
	 * @alias 有效标识0 无效；1有效
	 */
	protected java.lang.Integer enabledFlag;
	
	/**
	 * 启用和禁用标志：0，禁用； 1，启用
	 */
	protected Integer status;
	
	/**
	 * @alias 更新时间
	 */
	protected java.util.Date lastUpdateDate;
	/**
	 * @alias 更新人
	 */
	protected java.lang.Integer lastUpdatedBy;
	/**
	 * @alias  创建时间
	 */
	protected java.util.Date creationDate;
	/**
	 * @alias 创建人
	 */
	protected java.lang.Integer createdBy;

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
	public QuestionRangeType getType() {
		return type;
	}
	public void setType(QuestionRangeType type) {
		this.type = type;
	}
	public java.lang.Integer getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(java.lang.Integer enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public java.util.Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(java.util.Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public java.lang.Integer getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(java.lang.Integer lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public java.util.Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(java.util.Date creationDate) {
		this.creationDate = creationDate;
	}
	public java.lang.Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(java.lang.Integer createdBy) {
		this.createdBy = createdBy;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
}