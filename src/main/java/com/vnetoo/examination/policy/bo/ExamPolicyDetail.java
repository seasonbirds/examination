package com.vnetoo.examination.policy.bo;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 出题策略明细
 * @author chenh
 * @date 2013-11-13
 */
@SuppressWarnings("serial")
public class ExamPolicyDetail extends AbstractExamPolicyDetail implements BaseObject{
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
		sb.append("examPolicyId:"+examPolicyId+";");
		sb.append("type:"+type+";");
		sb.append("lev:"+lev+";");
		sb.append("questionCount:"+questionCount+";");
		sb.append("perScore:"+perScore+";");
		sb.append("lastUpdateDate:"+lastUpdateDate+";");
		sb.append("lastUpdatedBy:"+lastUpdatedBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("enabledFlag:"+enabledFlag+";");

		return sb.toString();
	}
}