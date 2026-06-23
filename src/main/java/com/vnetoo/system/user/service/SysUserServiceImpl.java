package com.vnetoo.system.user.service;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.vnetoo.system.user.bo.SysUser;
import com.vnetoo.system.user.dao.SysUserDAO;

/**
 * @comment 用户信息表Service实现类
 * @author chenh
 * @date 2013-11-18 
 */
@Service 
public class SysUserServiceImpl extends BaseServiceImpl<SysUser> implements SysUserService{
	
	public SysUserDAO getDao() {
		// TODO Auto-generated method stub
		return (SysUserDAO)super.dao;
	}
	
	@Resource(name="sysUserDAOImpl")
	public void setDao(SysUserDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
}
