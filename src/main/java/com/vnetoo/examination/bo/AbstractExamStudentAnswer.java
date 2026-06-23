package com.vnetoo.examination.bo;

/**
 * @comment 学生的考试答题表明细表
 * @author Administrator
 * @date 2012-06-01
 */
@SuppressWarnings("serial")
public abstract class AbstractExamStudentAnswer implements java.io.Serializable{
	/**
	 * @alias null
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 学生id
	 */
	protected java.lang.Integer studentId;
	/**
	 * @alias 试卷id
	 */
	protected java.lang.Integer papersId;
	/**
	 * @alias 考试状态，0：未交卷，1：已交卷
	 */
	protected java.lang.Integer status;
	/**
	 * @alias 更新时间
	 */
	protected java.util.Date updateDate;
	/**
	 * @alias 更新人
	 */
	protected java.lang.Integer updatedBy;
	/**
	 * @alias  创建时间
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
	public void setStudentId(java.lang.Integer studentId){
		 this.studentId=studentId;
	}
	public java.lang.Integer getStudentId(){
		 return this.studentId;
	}
	public void setPapersId(java.lang.Integer papersId){
		 this.papersId=papersId;
	}
	public java.lang.Integer getPapersId(){
		 return this.papersId;
	}
	public void setStatus(java.lang.Integer status){
		 this.status=status;
	}
	public java.lang.Integer getStatus(){
		 return this.status;
	}
	public void setUpdateDate(java.util.Date updateDate){
		 this.updateDate=updateDate;
	}
	public java.util.Date getUpdateDate(){
		 return this.updateDate;
	}
	public void setUpdatedBy(java.lang.Integer updatedBy){
		 this.updatedBy=updatedBy;
	}
	public java.lang.Integer getUpdatedBy(){
		 return this.updatedBy;
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