package com.vnetoo.examination.question.dao;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.question.bo.ExamQuestion;

/**
 * @comment 题目表DAO接口
 * @author chenh
 * @date 2013-11-08 
 */
public interface ExamQuestionDAO extends BaseDAO<ExamQuestion>{
	
	public Map<String, Integer> getQuestionTypeAndCountByKeyPointsAndRangeType(List<Integer> keyPointIds, String rangeType);
	
	public List<Integer> getQuestionIds(String type, String lev, List<Integer>keyPointIds, String rangeType);
	
	public Integer getQuestionCount(Map<String, Object> map);
	
	public Map<String, List<Integer>> getQuestionTypeAndIds(Map<String, Object> params);
}