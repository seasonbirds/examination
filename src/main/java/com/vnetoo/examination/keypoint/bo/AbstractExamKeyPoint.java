package com.vnetoo.examination.keypoint.bo;

/**
 * @comment 知识点
 * @author chenh
 * @date 2013-11-08
 */
@SuppressWarnings("serial")
public abstract class AbstractExamKeyPoint implements java.io.Serializable{
	/**
	 * @alias 课程ID
	 */
	protected java.lang.Integer courseId;
	/**
	 * @alias 编号
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 名称
	 */
	protected java.lang.String name;
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
	 * @alias 备注
	 */
	protected java.lang.String memo;
	/**
	 * @alias 未用，启用，禁用
	 */
	protected com.vnetoo.common.enums.Enabled status;
	/**
	 * @alias 父节点
	 */
	protected java.lang.Integer parentId;

	public void setCourseId(java.lang.Integer courseId){
		 this.courseId=courseId;
	}
	public java.lang.Integer getCourseId(){
		 return this.courseId;
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
	public void setMemo(java.lang.String memo){
		 this.memo=memo;
	}
	public java.lang.String getMemo(){
		 return this.memo;
	}
 
	public com.vnetoo.common.enums.Enabled getStatus() {
		return status;
	}
	public void setStatus(com.vnetoo.common.enums.Enabled status) {
		this.status = status;
	}
	public void setParentId(java.lang.Integer parentId){
		 this.parentId=parentId;
	}
	public java.lang.Integer getParentId(){
		 return this.parentId;
	}
}