package com.vnetoo.system.role.bo;

import com.vnetoo.common.bo.BaseObject;
import com.vnetoo.common.enums.Enabled;
/**
 * @comment 系统角色
 * @author chenh
 * @date 2013-11-18
 */
@SuppressWarnings("serial")
public class SysRole extends AbstractSysRole implements BaseObject{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
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
		sb.append("description:"+description+";");
		sb.append("status:"+status+";");
		sb.append("enabledFlag:"+enabledFlag+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("lastUpdatedBy:"+lastUpdatedBy+";");
		sb.append("lastUpdateDate:"+lastUpdateDate+";");

		return sb.toString();
	}
	
	private Enabled[] statusForQuery;

	public Enabled[] getStatusForQuery() {
		return statusForQuery;
	}

	public void setStatusForQuery(Enabled[] statusForQuery) {
		this.statusForQuery = statusForQuery;
	}
}