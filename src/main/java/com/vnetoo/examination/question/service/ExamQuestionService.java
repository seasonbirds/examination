package com.vnetoo.examination.question.service;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.question.bo.ExamQuestion;

/**
 * @comment 题目表Service接口
 * @author chenh
 * @date 2013-11-08 
 */
public interface ExamQuestionService extends BaseService<ExamQuestion>{

	public boolean checkCode(String code) ;
	
	public boolean saveExamQuestion(ExamQuestion question);
	
	public Map<String, Integer> getQuestionTypeAndCountByKeyPointsAndRangeType(List<Integer> keyPointIds, String rangeType);
	
	public List<Integer> getQuestionIds(String type, String lev, List<Integer>keyPointIds, String rangeType);
	
	public Integer getQuestionCount(Map<String, Object> map);
	
	public Map<String, List<Integer>> getQuestionTypeAndIds(Map<String, Object> params);
}