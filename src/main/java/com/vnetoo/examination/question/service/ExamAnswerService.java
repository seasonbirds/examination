package com.vnetoo.examination.question.service;

import java.util.List;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.question.bo.ExamAnswer;

/**
 * @comment 答案表Service接口
 * @author chenh
 * @date 2013-11-08 
 */
public interface ExamAnswerService extends BaseService<ExamAnswer>{

	public List<ExamAnswer> getAllAnswersByPaperId(Integer paperId);
	public List<ExamAnswer> getAnswersByQuestionId(Integer questionId);
	
}