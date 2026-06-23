package com.vnetoo.examination.keypoint.bo;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 知识点
 * @author chenh
 * @date 2013-11-08
 */
@SuppressWarnings("serial")
public class ExamKeyPoint extends AbstractExamKeyPoint implements BaseObject{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
	private String parentName;
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("courseId:"+courseId+";");
		sb.append("id:"+id+";");
		sb.append("name:"+name+";");
		sb.append("lastUpdateDate:"+lastUpdateDate+";");
		sb.append("lastUpdatedBy:"+lastUpdatedBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("enabledFlag:"+enabledFlag+";");
		sb.append("memo:"+memo+";");
		sb.append("ststus:"+status+";");
		sb.append("parentId:"+parentId+";");

		return sb.toString();
	}
}