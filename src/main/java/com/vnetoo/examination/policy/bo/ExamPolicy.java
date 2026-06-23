package com.vnetoo.examination.policy.bo;

import com.vnetoo.common.bo.BaseObject;
import com.vnetoo.examination.enums.QuestionRangeType;
/**
 * @comment 出题策略
 * @author chenh
 * @date 2013-11-13
 */
@SuppressWarnings("serial")
public class ExamPolicy extends AbstractExamPolicy implements BaseObject{
	/**
	 * 批量操作ID
	 */
	private Integer[] ids;
	
	private Integer scopeId;
	
	private QuestionRangeType examType;
	
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

	public QuestionRangeType getExamType() {
		return examType;
	}

	public void setExamType(QuestionRangeType examType) {
		this.examType = examType;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("time:"+time+";");
		sb.append("totalScore:"+totalScore+";");
		sb.append("passScore:"+passScore+";");
		sb.append("examTimeStart:"+examTimeStart+";");
		sb.append("examTimeEnd:"+examTimeEnd+";");
		sb.append("showAnswer:"+showAnswer+";");
		sb.append("showScore:"+showScore+";");
		sb.append("savePage:"+savePage+";");
		sb.append("isUnify:"+isUnify+";");
		sb.append("memo:"+memo+";");
		sb.append("lastUpdateDate:"+lastUpdateDate+";");
		sb.append("lastUpdatedBy:"+lastUpdatedBy+";");
		sb.append("creationDate:"+creationDate+";");
		sb.append("createdBy:"+createdBy+";");
		sb.append("enabledFlag:"+enabledFlag+";");
		sb.append("paperCount:"+paperCount+";");
		sb.append("generateCycle:"+generateCycle+";");
		sb.append("type:"+type+";");
		sb.append("id:"+id+";");
		sb.append("name:"+name+";");

		return sb.toString();
	}
}