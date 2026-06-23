package com.vnetoo.examination.paper.service;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.paper.bo.ExamStudentAnswerDetail;

public interface ExamStudentAnswerDetailService extends
		BaseService<ExamStudentAnswerDetail> {

	public Map<Integer, Integer> getAnswerNumberByAnswerId(List<Integer> answerIds);
	
	public Map<Integer, Integer> getScoreByAnswerId(List<Integer> answerIds);
}
