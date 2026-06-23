package com.vnetoo.system.user.bo;

import java.util.List;

import com.vnetoo.common.bo.BaseObject;
import com.vnetoo.common.bo.IAuthUser;
import com.vnetoo.system.right.bo.SysRight;
/**
 * @comment 用户信息表
 * @author chenh
 * @date 2013-11-18
 */
@SuppressWarnings("serial")
public class SysUser extends AbstractSysUser implements BaseObject,IAuthUser{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
	/**
	 * 用户当前可以操作的资源集合
	 */
	List<SysRight> menuRootList;
	/**
	 * 用户当前操作的一级菜单
	 */
	private Integer menuParentId = 0;
	/**
	 * 用户当前操作的二级菜单
	 */
	private Integer menuId = 0;

	public List<SysRight> getMenuRootList() {
		return menuRootList;
	}

	public void setMenuRootList(List<SysRight> menuRootList) {
		this.menuRootList = menuRootList;
	}

	public Integer getMenuParentId() {
		return menuParentId;
	}

	public void setMenuParentId(Integer menuParentId) {
		this.menuParentId = menuParentId;
	}

	public Integer getMenuId() {
		return menuId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:"+id+";");
		sb.append("acct:"+acct+";");
		sb.append("pwd:"+pwd+";");
		sb.append("realName:"+realName+";");
		sb.append("memo:"+memo+";");
		sb.append("email:"+email+";");
		sb.append("emailActive:"+emailActive+";");
		sb.append("status:"+status+";");
		sb.append("lastUpdateDate:"+lastUpdateDate+";");
		sb.append("lastUpdatedBy:"+lastUpdatedBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("enabledFlag:"+enabledFlag+";");
		sb.append("logins:"+logins+";");
		sb.append("lastLoginTime:"+lastLoginTime+";");
		sb.append("nickname:"+nickname+";");
		sb.append("errortimes:"+errortimes+";");
		sb.append("logintime:"+logintime+";");

		return sb.toString();
	}

	public String getRemoteIP() {
		// TODO Auto-generated method stub
		return null;
	}
	
	boolean isStudent = false;
	
	public boolean isStudent() {
		return isStudent;
	}

	public void setStudent(boolean isStudent) {
		this.isStudent = isStudent;
	}
	
}