package com.vnetoo.examination.paper.dao;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.paper.bo.ExamStudentAnswerDetail;

public interface ExamStudentAnswerDetailDAO extends BaseDAO<ExamStudentAnswerDetail> {

	public Map<Integer, Integer> getAnswerNumberByAnswerId(List<Integer> answerIds);
	
	public Map<Integer, Integer> getScoreByAnswerId(List<Integer> answerIds);
}
