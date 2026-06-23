package com.vnetoo.system.right.dao;

import java.util.List;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;

import org.springframework.stereotype.Repository;

import com.vnetoo.system.right.bo.SysRight;

/**
 * @comment 系统操作资源表Service实现类
 * @author chenh
 * @date 2013-11-18 
 */
@Repository
@MybatisNamespace("SysRight")
public class SysRightDAOImpl extends BaseDAOImpl<SysRight> implements SysRightDAO{
	
	/**
	 * 根据角色找到角色对应的菜单
	 * @author chenh
	 * @date 2013年8月22日
	 * @param roleId
	 * @return
	 */
	public List<SysRight> findRightByRole(Integer roleId){
		return getSqlSessionTemplate().selectList(getStatement(),roleId);
	}
}
