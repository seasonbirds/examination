package com.vnetoo.examination.app.bo;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 
 * @author chenh
 * @date 2013-11-11
 */
@SuppressWarnings("serial")
public class AppScope extends AbstractAppScope implements BaseObject{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
	private Integer policyNum;
	
	private Integer paperNum;
	
	private Integer examStatus;
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	public Integer getPolicyNum() {
		return policyNum;
	}

	public void setPolicyNum(Integer policyNum) {
		this.policyNum = policyNum;
	}

	public Integer getPaperNum() {
		return paperNum;
	}

	public void setPaperNum(Integer paperNum) {
		this.paperNum = paperNum;
	}

	public Integer getExamStatus() {
		return examStatus;
	}

	public void setExamStatus(Integer examStatus) {
		this.examStatus = examStatus;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:"+id+";");
		sb.append("name:"+name+";");

		return sb.toString();
	}
}