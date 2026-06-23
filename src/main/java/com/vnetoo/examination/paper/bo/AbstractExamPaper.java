package com.vnetoo.examination.paper.bo;

import com.vnetoo.common.enums.Bool;

/**
 * @comment 试卷表
 * @author chenh
 * @date 2013-11-13
 */
@SuppressWarnings("serial")
public abstract class AbstractExamPaper implements java.io.Serializable{
	/**
	 * @alias null
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 策略ID
	 */
	protected java.lang.Integer examPolicyId;
	/**
	 * @alias 试卷地址
	 */
	protected java.lang.String paperUrl;
	/**
	 * @alias null
	 */
	protected java.lang.String guid;
	/**
	 * @alias 试卷状态，0：无效，1：有效
	 */
	protected Bool status;
	/**
	 * @alias 试卷状态，0：没人考，1：有人考
	 */
	protected Bool used;
	/**
	 * @alias 更新时间
	 */
	protected java.util.Date updateDate;
	/**
	 * @alias 更新人
	 */
	protected java.lang.Integer updatedBy;
	/**
	 * @alias 创建时间
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
	 * @alias 考试时间（从策略中拷贝过来的）
	 */
	protected java.lang.Integer time;
	/**
	 * @alias 试卷总分（从策略来的）
	 */
	protected java.lang.Integer totleScore;
	/**
	 * @alias 试卷及格分数（从策略来的）
	 */
	protected java.lang.Integer passScore;

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
	public void setPaperUrl(java.lang.String paperUrl){
		 this.paperUrl=paperUrl;
	}
	public java.lang.String getPaperUrl(){
		 return this.paperUrl;
	}
	public void setGuid(java.lang.String guid){
		 this.guid=guid;
	}
	public java.lang.String getGuid(){
		 return this.guid;
	}
	public void setStatus(Bool status){
		 this.status=status;
	}
	public Bool getStatus(){
		 return this.status;
	}
	public void setUsed(Bool used){
		 this.used=used;
	}
	public Bool getUsed(){
		 return this.used;
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
	public void setTime(java.lang.Integer time){
		 this.time=time;
	}
	public java.lang.Integer getTime(){
		 return this.time;
	}
	public void setTotleScore(java.lang.Integer totleScore){
		 this.totleScore=totleScore;
	}
	public java.lang.Integer getTotleScore(){
		 return this.totleScore;
	}
	public void setPassScore(java.lang.Integer passScore){
		 this.passScore=passScore;
	}
	public java.lang.Integer getPassScore(){
		 return this.passScore;
	}
}