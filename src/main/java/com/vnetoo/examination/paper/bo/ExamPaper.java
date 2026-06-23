package com.vnetoo.examination.paper.bo;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 试卷表
 * @author chenh
 * @date 2013-11-13
 */
@SuppressWarnings("serial")
public class ExamPaper extends AbstractExamPaper implements BaseObject{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
	private Integer scopeId;
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:"+id+";");
		sb.append("examPolicyId:"+examPolicyId+";");
		sb.append("paperUrl:"+paperUrl+";");
		sb.append("guid:"+guid+";");
		sb.append("status:"+status+";");
		sb.append("used:"+used+";");
		sb.append("updateDate:"+updateDate+";");
		sb.append("updatedBy:"+updatedBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("enabledFlag:"+enabledFlag+";");
		sb.append("time:"+time+";");
		sb.append("totleScore:"+totleScore+";");
		sb.append("passScore:"+passScore+";");

		return sb.toString();
	}
}