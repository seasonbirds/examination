/**
 * 
 */
package com.vnetoo.examination.login.service;

import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.opensymphony.xwork2.ActionContext;
import com.vnetoo.common.ConfigKeys;
import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.enums.LoginResult;
import com.vnetoo.system.SystemAuthCache;
import com.vnetoo.system.right.bo.SysRight;
import com.vnetoo.system.user.bo.SysUser;
import com.vnetoo.system.user.dao.SysUserDAO;

/**
 * @author chenh
 * @date 2013年9月2日
 */
@Service 
public class LoginServiceImpl extends BaseServiceImpl<SysUser> implements LoginService{
	public SysUserDAO getDao() {
		// TODO Auto-generated method stub
		return (SysUserDAO)super.dao;
	}
	
	@Resource(name="sysUserDAOImpl")
	public void setDao(SysUserDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
	
	@Resource
	private SystemAuthCache systemAuthCache;	
	public void setSystemAuthCache(SystemAuthCache systemAuthCache) {
		this.systemAuthCache = systemAuthCache;
	}
	
	/**
	 * @author chenh
	 * @date 2013年8月15日
	 * @param acct
	 * @param passwd
	 * @return
	 */
	public LoginResult login(String acct,String passwd){
		SysUser user = getDao().checkPasswd(acct, passwd);
		if(user==null){
			return LoginResult.ERROR_PASSWD;
		}
		
		List<SysRight> menuRootList = systemAuthCache.findRootRightByUser(user.getId());
		for (Iterator<SysRight> iterator = menuRootList.iterator(); iterator.hasNext();) {
			SysRight sysRight = (SysRight) iterator.next();
			sysRight.setSubList(systemAuthCache.getMenuList(user.getId(), sysRight.getId()));
		}
        user.setMenuRootList(menuRootList);
  	  	
  	  	ActionContext.getContext().getSession().put(ConfigKeys.AUTH_USRE, user);
		
		return LoginResult.SUCCESS;
	}
}
