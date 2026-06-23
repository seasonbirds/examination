package com.vnetoo.examination.question.dao;

import java.util.List;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import org.springframework.stereotype.Repository;
import com.vnetoo.examination.question.bo.ExamAnswer;

/**
 * @comment 答案表Service实现类
 * @author chenh
 * @date 2013-11-08 
 */
@Repository
@MybatisNamespace("ExamAnswer")
public class ExamAnswerDAOImpl extends BaseDAOImpl<ExamAnswer> implements ExamAnswerDAO{
	
	public List<ExamAnswer> getAllAnswersByPaperId(Integer paperId){
		return this.getSqlSessionTemplate().selectList(getStatement(), paperId);
	}
	public List<ExamAnswer> selectList(ExamAnswer item){
		return this.getSqlSessionTemplate().selectList(getStatement(), item);
	}
}
