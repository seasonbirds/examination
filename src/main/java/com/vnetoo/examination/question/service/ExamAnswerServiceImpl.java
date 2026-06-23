package com.vnetoo.examination.question.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.question.bo.ExamAnswer;
import com.vnetoo.examination.question.dao.ExamAnswerDAO;

/**
 * @comment 答案表Service实现类
 * @author chenh
 * @date 2013-11-08 
 */
@Service 
public class ExamAnswerServiceImpl extends BaseServiceImpl<ExamAnswer> implements ExamAnswerService{
	
	public ExamAnswerDAO getDao() {
		// TODO Auto-generated method stub
		return (ExamAnswerDAO)super.dao;
	}
	
	@Resource(name="examAnswerDAOImpl")
	public void setDao(ExamAnswerDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
	
	/**
	 * 根据试卷Id获取所有试题的答案
	 */
	public List<ExamAnswer> getAllAnswersByPaperId(Integer paperId){
		return getDao().getAllAnswersByPaperId(paperId);
	}
	
	public List<ExamAnswer> getAnswersByQuestionId(Integer questionId){
		ExamAnswer item=new ExamAnswer();
		item.setQuestionId(questionId);
		return getDao().selectList(item);
	}
}
