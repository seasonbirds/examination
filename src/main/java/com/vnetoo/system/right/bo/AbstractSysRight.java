package com.vnetoo.system.right.bo;

import com.vnetoo.common.enums.Enabled;

/**
 * @comment 系统操作资源表
 * @author chenh
 * @date 2013-11-18
 */
@SuppressWarnings("serial")
public abstract class AbstractSysRight implements java.io.Serializable{
	/**
	 * @alias 主键ID
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 资源名(功能权限名)
	 */
	protected java.lang.String name;
	/**
	 * @alias 父节点ID
	 */
	protected java.lang.Integer parentId;
	/**
	 * @alias 资源状态
	 */
	protected Enabled status;
	/**
	 * @alias 系统功能URL
	 */
	protected java.lang.String url;
	/**
	 * @alias 操作类型编码，用于控制操作类型按钮的出现。
有编码的记录必须唯一。
	 */
	protected java.lang.String code;
	/**
	 * @alias 排序号，指同一级的菜单的次序
	 */
	protected java.lang.Integer seq;
	/**
	 * @alias 备注
	 */
	protected java.lang.String memo;
	/**
	 * @alias 是否可用
	 */
	protected java.lang.Integer enabledFlag;

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
	public void setStatus(Enabled status){
		 this.status=status;
	}
	public Enabled getStatus(){
		 return this.status;
	}
	public void setUrl(java.lang.String url){
		 this.url=url;
	}
	public java.lang.String getUrl(){
		 return this.url;
	}
	public void setCode(java.lang.String code){
		 this.code=code;
	}
	public java.lang.String getCode(){
		 return this.code;
	}
	public void setSeq(java.lang.Integer seq){
		 this.seq=seq;
	}
	public java.lang.Integer getSeq(){
		 return this.seq;
	}
	public void setMemo(java.lang.String memo){
		 this.memo=memo;
	}
	public java.lang.String getMemo(){
		 return this.memo;
	}
	public void setEnabledFlag(java.lang.Integer enabledFlag){
		 this.enabledFlag=enabledFlag;
	}
	public java.lang.Integer getEnabledFlag(){
		 return this.enabledFlag;
	}
}