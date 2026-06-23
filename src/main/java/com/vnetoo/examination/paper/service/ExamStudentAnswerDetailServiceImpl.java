package com.vnetoo.examination.paper.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.paper.bo.ExamStudentAnswerDetail;
import com.vnetoo.examination.paper.dao.ExamStudentAnswerDetailDAO;

@Service 
public class ExamStudentAnswerDetailServiceImpl extends
		BaseServiceImpl<ExamStudentAnswerDetail> implements
		ExamStudentAnswerDetailService {

	public ExamStudentAnswerDetailDAO getDao() {
		return (ExamStudentAnswerDetailDAO)super.dao;
	}
	
	@Resource(name="examStudentAnswerDetailDAOImpl")
	public void setDao(ExamStudentAnswerDetailDAO dao) {
		super.dao = dao;
	}
	
	public Map<Integer, Integer> getAnswerNumberByAnswerId(List<Integer> answerIds){
		return getDao().getAnswerNumberByAnswerId(answerIds);
	}
	
	public Map<Integer, Integer> getScoreByAnswerId(List<Integer> answerIds){
		return getDao().getScoreByAnswerId(answerIds);
	}
}
