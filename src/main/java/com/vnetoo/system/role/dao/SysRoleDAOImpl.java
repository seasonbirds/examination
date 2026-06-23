package com.vnetoo.system.role.dao;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;

import org.springframework.stereotype.Repository;

import com.vnetoo.system.role.bo.SysRole;

/**
 * @comment 系统角色Service实现类
 * @author chenh
 * @date 2013-11-18 
 */
@Repository
@MybatisNamespace("SysRole")
public class SysRoleDAOImpl extends BaseDAOImpl<SysRole> implements SysRoleDAO{
	
	/**
	 * 根据用户ID查询角色
	 * @author chenh
	 * @date 2013年8月22日
	 * @param userId
	 * @return
	 */
	public List<SysRole> findRoleByUser(Integer userId){
		return getSqlSessionTemplate().selectList(getStatement(), userId);
	}
	
	public void insertUserRoleRel(Map<String, Integer> map){
		getSqlSessionTemplate().insert(getStatement(), map);
	}
}
