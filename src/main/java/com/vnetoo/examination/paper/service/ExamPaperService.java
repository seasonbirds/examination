package com.vnetoo.examination.paper.service;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.bo.QuestionAnswer;

/**
 * @comment 试卷表Service接口
 * @author chenh
 * @date 2013-11-13 
 */
public interface ExamPaperService extends BaseService<ExamPaper>{

	public List<PaperQuestion> getQuestionByPaperId(Integer paperId);
	
	public List<Integer> getPaperIdsByScopeId(Integer scopeId);
	
	public Map<Integer, List<QuestionAnswer>> getAnswersByPaperId(Integer paperId);
}