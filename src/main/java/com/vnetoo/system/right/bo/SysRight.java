package com.vnetoo.system.right.bo;

import java.util.List;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 系统操作资源表
 * @author chenh
 * @date 2013-11-18
 */
@SuppressWarnings("serial")
public class SysRight extends AbstractSysRight implements BaseObject{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
	/**
	 * 存放用户操作资源的子操作资源
	 */
	private List<SysRight> subList;

	public List<SysRight> getSubList() {
		return subList;
	}

	public void setSubList(List<SysRight> subList) {
		this.subList = subList;
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
		sb.append("name:"+name+";");
		sb.append("parentId:"+parentId+";");
		sb.append("status:"+status+";");
		sb.append("url:"+url+";");
		sb.append("code:"+code+";");
		sb.append("seq:"+seq+";");
		sb.append("memo:"+memo+";");
		sb.append("enabledFlag:"+enabledFlag+";");

		return sb.toString();
	}
}