package com.vnetoo.system.role.service;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.vnetoo.system.role.bo.SysRole;
import com.vnetoo.system.role.dao.SysRoleDAO;

/**
 * @comment 系统角色Service实现类
 * @author chenh
 * @date 2013-11-18 
 */
@Service 
public class SysRoleServiceImpl extends BaseServiceImpl<SysRole> implements SysRoleService{
	
	public SysRoleDAO getDao() {
		// TODO Auto-generated method stub
		return (SysRoleDAO)super.dao;
	}
	
	@Resource(name="sysRoleDAOImpl")
	public void setDao(SysRoleDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
}
