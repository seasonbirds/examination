package com.vnetoo.system.user.bo;

/**
 * @comment 用户信息表
 * @author chenh
 * @date 2013-11-18
 */
@SuppressWarnings("serial")
public abstract class AbstractSysUser implements java.io.Serializable{
	/**
	 * @alias 主键
	 */
	protected java.lang.Integer id;
	/**
	 * @alias 帐号
	 */
	protected java.lang.String acct;
	/**
	 * @alias 用户密码
	 */
	protected java.lang.String pwd;
	/**
	 * @alias 真实姓名
	 */
	protected java.lang.String realName;
	/**
	 * @alias 备注
	 */
	protected java.lang.String memo;
	/**
	 * @alias 电子邮件
	 */
	protected java.lang.String email;
	/**
	 * @alias 邮箱验证
	 */
	protected java.lang.String emailActive;
	/**
	 * @alias 使用状态
	 */
	protected java.lang.String status;
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
	/**
	 * @alias 登录次数
	 */
	protected java.lang.Integer logins;
	/**
	 * @alias 上次登陆时间
	 */
	protected java.util.Date lastLoginTime;
	/**
	 * @alias 昵称
	 */
	protected java.lang.String nickname;
	/**
	 * @alias 错误登陆次数
	 */
	protected java.lang.Integer errortimes;
	/**
	 * @alias 登陆时间
	 */
	protected java.util.Date logintime;

	public void setId(java.lang.Integer id){
		 this.id=id;
	}
	public java.lang.Integer getId(){
		 return this.id;
	}
	public void setAcct(java.lang.String acct){
		 this.acct=acct;
	}
	public java.lang.String getAcct(){
		 return this.acct;
	}
	public void setPwd(java.lang.String pwd){
		 this.pwd=pwd;
	}
	public java.lang.String getPwd(){
		 return this.pwd;
	}
	public void setRealName(java.lang.String realName){
		 this.realName=realName;
	}
	public java.lang.String getRealName(){
		 return this.realName;
	}
	public void setMemo(java.lang.String memo){
		 this.memo=memo;
	}
	public java.lang.String getMemo(){
		 return this.memo;
	}
	public void setEmail(java.lang.String email){
		 this.email=email;
	}
	public java.lang.String getEmail(){
		 return this.email;
	}
	public void setEmailActive(java.lang.String emailActive){
		 this.emailActive=emailActive;
	}
	public java.lang.String getEmailActive(){
		 return this.emailActive;
	}
	public void setStatus(java.lang.String status){
		 this.status=status;
	}
	public java.lang.String getStatus(){
		 return this.status;
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
	public void setLogins(java.lang.Integer logins){
		 this.logins=logins;
	}
	public java.lang.Integer getLogins(){
		 return this.logins;
	}
	public void setLastLoginTime(java.util.Date lastLoginTime){
		 this.lastLoginTime=lastLoginTime;
	}
	public java.util.Date getLastLoginTime(){
		 return this.lastLoginTime;
	}
	public void setNickname(java.lang.String nickname){
		 this.nickname=nickname;
	}
	public java.lang.String getNickname(){
		 return this.nickname;
	}
	public void setErrortimes(java.lang.Integer errortimes){
		 this.errortimes=errortimes;
	}
	public java.lang.Integer getErrortimes(){
		 return this.errortimes;
	}
	public void setLogintime(java.util.Date logintime){
		 this.logintime=logintime;
	}
	public java.util.Date getLogintime(){
		 return this.logintime;
	}
}