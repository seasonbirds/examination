package com.vnetoo.examination.paper.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.bo.QuestionAnswer;

/**
 * @comment 试卷表DAO接口
 * @author chenh
 * @date 2013-11-13 
 */
public interface ExamPaperDAO extends BaseDAO<ExamPaper>{
	
	public List<PaperQuestion> getQuestionByPaperId(Integer paperId);
	
	public List<Integer> getPaperIdsByScopeId(Integer scopeId);
	
	public List<QuestionAnswer> getAnswersByPaperId(Integer paperId);
}