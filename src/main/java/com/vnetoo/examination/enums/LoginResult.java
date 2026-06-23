/**
 * 
 */
package com.vnetoo.examination.enums;

/**
 * @author chenh
 * @date 2013年9月2日
 */
public enum LoginResult {
	SUCCESS,		//登录成功
	ERROR_VALIDATE_CODE,//验证码错误或过期
	ERROR_PASSWD,//用户名或密码不正确
	ERROR_NO_USE,//用户已停用
	ERROR_LOCK;//用户已锁定	
}
