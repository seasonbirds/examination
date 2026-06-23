package com.vnetoo.system.role.bo;

import com.vnetoo.common.enums.Enabled;

/**
 * @comment 系统角色
 * @author chenh
 * @date 2013-11-18
 */
@SuppressWarnings("serial")
public abstract class AbstractSysRole implements java.io.Serializable{
	/**
	 * @alias 序号
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 名称
	 */
	protected java.lang.String name;
	/**
	 * @alias 父ID
	 */
	protected java.lang.Integer parentId;
	/**
	 * @alias 描述
	 */
	protected java.lang.String description;
	/**
	 * @alias 状态
	 */
	protected com.vnetoo.common.enums.Enabled status;
	/**
	 * @alias 删除标记
	 */
	protected java.lang.Integer enabledFlag;
	/**
	 * @alias 创建人
	 */
	protected java.lang.Integer createdBy;
	/**
	 * @alias 创建时间
	 */
	protected java.util.Date creationDate;
	/**
	 * @alias 更新人
	 */
	protected java.lang.Integer lastUpdatedBy;
	/**
	 * @alias 更新时间
	 */
	protected java.util.Date lastUpdateDate;

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
	public void setParentId(java.lang.Integer parentId){
		 this.parentId=parentId;
	}
	public java.lang.Integer getParentId(){
		 return this.parentId;
	}
	public void setDescription(java.lang.String description){
		 this.description=description;
	}
	public java.lang.String getDescription(){
		 return this.description;
	}
	public void setStatus(Enabled status){
		 this.status=status;
	}
	public Enabled getStatus(){
		 return this.status;
	}
	public void setEnabledFlag(java.lang.Integer enabledFlag){
		 this.enabledFlag=enabledFlag;
	}
	public java.lang.Integer getEnabledFlag(){
		 return this.enabledFlag;
	}
	public void setCreatedBy(java.lang.Integer createdBy){
		 this.createdBy=createdBy;
	}
	public java.lang.Integer getCreatedBy(){
		 return this.createdBy;
	}
	public void setCreationDate(java.util.Date creationDate){
		 this.creationDate=creationDate;
	}
	public java.util.Date getCreationDate(){
		 return this.creationDate;
	}
	public void setLastUpdatedBy(java.lang.Integer lastUpdatedBy){
		 this.lastUpdatedBy=lastUpdatedBy;
	}
	public java.lang.Integer getLastUpdatedBy(){
		 return this.lastUpdatedBy;
	}
	public void setLastUpdateDate(java.util.Date lastUpdateDate){
		 this.lastUpdateDate=lastUpdateDate;
	}
	public java.util.Date getLastUpdateDate(){
		 return this.lastUpdateDate;
	}
}