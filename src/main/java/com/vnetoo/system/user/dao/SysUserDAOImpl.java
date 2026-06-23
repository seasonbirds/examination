package com.vnetoo.system.user.dao;

import com.vnetoo.common.ConfigKeys;
import com.vnetoo.common.MD5;
import com.vnetoo.common.SystemGlobals;
import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;

import org.springframework.stereotype.Repository;

import com.vnetoo.system.user.bo.SysUser;

/**
 * @comment 用户信息表Service实现类
 * @author chenh
 * @date 2013-11-18 
 */
@Repository
@MybatisNamespace("SysUser")
public class SysUserDAOImpl extends BaseDAOImpl<SysUser> implements SysUserDAO{
	
	/**
	 * @author chenh
	 * @date 2013年8月15日
	 * @param acct
	 * @param passwd
	 * @return
	 */
	public SysUser checkPasswd(String acct, String passwd) {
		SysUser user = new SysUser();
		user.setAcct(acct);
		user.setPwd(MD5.cryptByKey(passwd,SystemGlobals.getValue(ConfigKeys.PASSWORD_KEY)));
		
		return this.getSqlSessionTemplate().selectOne(getStatement(), user);
	}
}
