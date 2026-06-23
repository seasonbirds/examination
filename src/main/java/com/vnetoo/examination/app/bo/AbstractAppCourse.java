package com.vnetoo.examination.app.bo;

/**
 * @comment 
 * @author chenh
 * @date 2013-11-11
 */
@SuppressWarnings("serial")
public abstract class AbstractAppCourse implements java.io.Serializable{
	/**
	 * @alias 编号
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 名称
	 */
	protected java.lang.String name;

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
}