package com.vnetoo.system.right.service;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.vnetoo.system.right.bo.SysRight;
import com.vnetoo.system.right.dao.SysRightDAO;

/**
 * @comment 系统操作资源表Service实现类
 * @author chenh
 * @date 2013-11-18 
 */
@Service 
public class SysRightServiceImpl extends BaseServiceImpl<SysRight> implements SysRightService{
	
	public SysRightDAO getDao() {
		// TODO Auto-generated method stub
		return (SysRightDAO)super.dao;
	}
	
	@Resource(name="sysRightDAOImpl")
	public void setDao(SysRightDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
}
