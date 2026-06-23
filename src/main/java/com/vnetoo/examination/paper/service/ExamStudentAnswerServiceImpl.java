package com.vnetoo.examination.paper.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.paper.bo.ExamStudentAnswer;
import com.vnetoo.examination.paper.dao.ExamStudentAnswerDAO;

@Service 
public class ExamStudentAnswerServiceImpl extends
		BaseServiceImpl<ExamStudentAnswer> implements ExamStudentAnswerService {

	public ExamStudentAnswerDAO getDao() {
		return (ExamStudentAnswerDAO)super.dao;
	}
	
	@Resource(name="examStudentAnswerDAOImpl")
	public void setDao(ExamStudentAnswerDAO dao) {
		super.dao = dao;
	}
	
	public List<ExamStudentAnswer> getStudentAnswerByPaperIdAndSId(Integer sid, List<Integer> paperIds){
		return getDao().getStudentAnswerByPaperIdAndSId(sid, paperIds);
	}
	
	public Map<Integer, Integer> getAnswerStatusByScopeIdsAndSId(List<Integer> scopeIds, Integer studentId){
		return getDao().getAnswerStatusByScopeIdsAndSId(scopeIds, studentId);
	}
}
