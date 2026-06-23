package com.vnetoo.examination.question.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.question.bo.ExamAnswer;

/**
 * @comment 答案表DAO接口
 * @author chenh
 * @date 2013-11-08 
 */
public interface ExamAnswerDAO extends BaseDAO<ExamAnswer>{
	
	/**
	 * 根据试卷ID获取所有试题的答案
	 */
	public List<ExamAnswer> getAllAnswersByPaperId(Integer paperId);
	public List<ExamAnswer> selectList(ExamAnswer item);
}