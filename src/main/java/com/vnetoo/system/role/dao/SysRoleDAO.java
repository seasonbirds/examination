package com.vnetoo.system.role.dao;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.system.role.bo.SysRole;

/**
 * @comment 系统角色DAO接口
 * @author chenh
 * @date 2013-11-18 
 */
public interface SysRoleDAO extends BaseDAO<SysRole>{
	
	/**
	 * 根据用户ID查询角色
	 * @author chenh
	 * @date 2013年8月22日
	 * @param userId
	 * @return
	 */
	public List<SysRole> findRoleByUser(Integer userId);
	
	/**
	 * 插入用户和角色的关系
	 * @param map
	 */
	public void insertUserRoleRel(Map<String, Integer> map);
}