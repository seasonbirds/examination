package com.vnetoo.system.user.dao;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.system.user.bo.SysUser;

/**
 * @comment 用户信息表DAO接口
 * @author chenh
 * @date 2013-11-18 
 */
public interface SysUserDAO extends BaseDAO<SysUser>{
	
	/**
	 * @author chenh
	 * @date 2013年8月15日
	 * @param acct
	 * @param passwd
	 * @return
	 */
	public SysUser checkPasswd(String acct, String passwd);
	
}