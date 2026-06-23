package com.vnetoo.examination.app.bo;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 
 * @author chenh
 * @date 2013-11-11
 */
@SuppressWarnings("serial")
public class AppCourse extends AbstractAppCourse implements BaseObject{
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

		return sb.toString();
	}
}