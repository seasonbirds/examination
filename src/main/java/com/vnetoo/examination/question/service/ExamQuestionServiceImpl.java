package com.vnetoo.examination.question.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.vnetoo.examination.question.bo.ExamAnswer;
import com.vnetoo.examination.question.bo.ExamQuestion;
import com.vnetoo.examination.question.dao.ExamAnswerDAO;
import com.vnetoo.examination.question.dao.ExamQuestionDAO;

/**
 * @comment 题目表Service实现类
 * @author chenh
 * @date 2013-11-08 
 */
@Service 
public class ExamQuestionServiceImpl extends BaseServiceImpl<ExamQuestion> implements ExamQuestionService{
	
	private ExamAnswerDAO examAnswerDAO;
	
	public ExamQuestionDAO getDao() {
		// TODO Auto-generated method stub
		return (ExamQuestionDAO)super.dao;
	}
	
	@Resource(name="examQuestionDAOImpl")
	public void setDao(ExamQuestionDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
	
	public ExamAnswerDAO getExamAnswerDAO() {
		return examAnswerDAO;
	}

	@Resource(name="examAnswerDAOImpl")
	public void setExamAnswerDAO(ExamAnswerDAO examAnswerDAO) {
		this.examAnswerDAO = examAnswerDAO;
	}
	
	public boolean checkCode(String code) {
		ExamQuestion bo=new ExamQuestion();
		bo.setCode(code);
		List<ExamQuestion> list = dao.selectList(bo);
		if(list == null || list.isEmpty()){
			return true;
		}
		return false;
	}
	
	public boolean saveExamQuestion(ExamQuestion question){
		if(question == null){
			return false;
		}
		//TODO
		question.setParse("");
		Integer id = dao.insert(question);
		if(id == null || id <= 0){
			return false;
		}
		List<ExamAnswer> l = question.getAnswerList();
		for(int i=0; i<l.size(); i++){
			ExamAnswer ea = l.get(i);
			ea.setQuestionId(id);
			Integer aId = examAnswerDAO.insert(ea);
			if(aId == null || aId <= 0){
				return false;
			}
		}
		
		return true;
	}
	
	public Map<String, Integer> getQuestionTypeAndCountByKeyPointsAndRangeType(List<Integer> keyPointIds, String rangeType){
		return getDao().getQuestionTypeAndCountByKeyPointsAndRangeType(keyPointIds, rangeType);
	}
	
	public List<Integer> getQuestionIds(String type, String lev, List<Integer>keyPointIds, String rangeType){
		return getDao().getQuestionIds(type, lev, keyPointIds, rangeType);
	}
	
	public Integer getQuestionCount(Map<String, Object> map){
		return getDao().getQuestionCount(map);
	}
	
	public Map<String, List<Integer>> getQuestionTypeAndIds(Map<String, Object> params){
		return getDao().getQuestionTypeAndIds(params);
	}
}
