package com.vnetoo.examination.paper.dao;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.paper.bo.ExamStudentAnswer;

public interface ExamStudentAnswerDAO extends BaseDAO<ExamStudentAnswer> {

	public List<ExamStudentAnswer> getStudentAnswerByPaperIdAndSId(Integer sid, List<Integer> paperIds);
	
	public Map<Integer, Integer> getAnswerStatusByScopeIdsAndSId(List<Integer> scopeIds, Integer studentId);
}
