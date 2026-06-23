/**
 * 
 */
package com.vnetoo.examination.login.service;

import com.vnetoo.examination.enums.LoginResult;

/**
 * @author chenh
 * @date 2013年9月2日
 */
public interface LoginService {
	/**
	 * @author chenh
	 * @date 2013年8月15日
	 * @param acct
	 * @param passwd
	 * @return
	 */
	public LoginResult login(String acct,String passwd);
}
