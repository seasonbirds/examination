package com.vnetoo.examination.question.bo;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 答案表
 * @author chenh
 * @date 2013-11-08
 */
@SuppressWarnings("serial")
public class ExamAnswer extends AbstractExamAnswer implements BaseObject{
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
		sb.append("content:"+content+";");
		sb.append("valid:"+valid+";");
		sb.append("lastUpdateDate:"+lastUpdateDate+";");
		sb.append("lastUpdatedBy:"+lastUpdatedBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("enabledFlag:"+enabledFlag+";");
		sb.append("answerIdx:"+answerIdx+";");
		sb.append("questionId:"+questionId+";");

		return sb.toString();
	}
}