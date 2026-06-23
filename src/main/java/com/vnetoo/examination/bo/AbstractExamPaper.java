package com.vnetoo.examination.bo;


/**
 * @comment 试卷表
 * @author Administrator
 * @date 2012-06-01
 */
@SuppressWarnings("serial")
public abstract class AbstractExamPaper implements java.io.Serializable{
	/**
	 * @alias null
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 有效标识
	 */
	protected java.lang.Integer enabledFlag;
	/**
	 * @alias 试卷地址
	 */
	protected java.lang.String paperUrl;
	/**
	 * @alias null
	 */
	protected java.lang.String guid;
	/**
	 * @alias 考试策略id
	 */
	protected java.lang.Integer examPolicyId;
	/**
	 * @alias 试卷状态，0：无效，1：有效
	 */
	protected java.lang.Integer status;
	/**
	 * @alias 试卷状态，0：没人考，1：有人考
	 */
	protected java.lang.Integer used;
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
	 * 考试时间
	 */
	private Integer time;

	/**
	 * 试卷总分
	 */
	private Integer totalScore;

	/**
	 * 试卷及格分数
	 */
	private Integer passScore;
	
	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public Integer getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(Integer totalScore) {
		this.totalScore = totalScore;
	}

	public Integer getPassScore() {
		return passScore;
	}

	public void setPassScore(Integer passScore) {
		this.passScore = passScore;
	}

	public void setId(java.lang.Integer id){
		 this.id=id;
	}
	public java.lang.Integer getId(){
		 return this.id;
	}
	public void setEnabledFlag(java.lang.Integer enabledFlag){
		 this.enabledFlag=enabledFlag;
	}
	public java.lang.Integer getEnabledFlag(){
		 return this.enabledFlag;
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
	public void setExamPolicyId(java.lang.Integer examPolicyId){
		 this.examPolicyId=examPolicyId;
	}
	public java.lang.Integer getExamPolicyId(){
		 return this.examPolicyId;
	}
	public void setStatus(java.lang.Integer status){
		 this.status=status;
	}
	public java.lang.Integer getStatus(){
		 return this.status;
	}
	public void setUsed(java.lang.Integer used){
		 this.used=used;
	}
	public java.lang.Integer getUsed(){
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
}