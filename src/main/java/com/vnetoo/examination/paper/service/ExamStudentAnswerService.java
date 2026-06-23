package com.vnetoo.examination.paper.service;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.paper.bo.ExamStudentAnswer;

public interface ExamStudentAnswerService extends BaseService<ExamStudentAnswer> {

	public List<ExamStudentAnswer> getStudentAnswerByPaperIdAndSId(Integer sid, List<Integer> paperIds);
	
	/**
	 * 根据学生ID和考试的ID获取学生的考试状态
	 */
	public Map<Integer, Integer> getAnswerStatusByScopeIdsAndSId(List<Integer> scopeIds, Integer studentId);
}
