package com.vnetoo.system.right.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.system.right.bo.SysRight;

/**
 * @comment 系统操作资源表DAO接口
 * @author chenh
 * @date 2013-11-18 
 */
public interface SysRightDAO extends BaseDAO<SysRight>{
	
	/**
	 * 根据角色找到角色对应的菜单
	 * @author chenh
	 * @date 2013年8月22日
	 * @param roleId
	 * @return
	 */
	public List<SysRight> findRightByRole(Integer roleId);
}