package com.vnetoo.examination.question.bo;

import java.util.List;

import com.vnetoo.common.bo.BaseObject;
/**
 * @comment 题目表
 * @author chenh
 * @date 2013-11-08
 */
@SuppressWarnings("serial")
public class ExamQuestion extends AbstractExamQuestion implements BaseObject{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
	private List<ExamAnswer> answerList;
	
	private Integer courseId;
	
	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}
	
	public List<ExamAnswer> getAnswerList() {
		return answerList;
	}

	public void setAnswerList(List<ExamAnswer> answerList) {
		this.answerList = answerList;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("id:"+id+";");
		sb.append("code:"+code+";");
		sb.append("keyPointId:"+keyPointId+";");
		sb.append("lev:"+lev+";");
		sb.append("content:"+content+";");
		sb.append("type:"+type+";");
		sb.append("memo:"+memo+";");
		sb.append("lastUpdateDate:"+lastUpdateDate+";");
		sb.append("lastUpdatedBy:"+lastUpdatedBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("enabledFlag:"+enabledFlag+";");
		sb.append("parse:"+parse+";");
		sb.append("rangeType:"+rangeType+";");

		return sb.toString();
	}
}