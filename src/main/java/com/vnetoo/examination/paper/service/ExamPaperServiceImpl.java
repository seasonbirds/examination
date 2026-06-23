package com.vnetoo.examination.paper.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.bo.QuestionAnswer;
import com.vnetoo.examination.paper.dao.ExamPaperDAO;

/**
 * @comment 试卷表Service实现类
 * @author chenh
 * @date 2013-11-13 
 */
@Service 
public class ExamPaperServiceImpl extends BaseServiceImpl<ExamPaper> implements ExamPaperService{
	
	public ExamPaperDAO getDao() {
		// TODO Auto-generated method stub
		return (ExamPaperDAO)super.dao;
	}
	
	@Resource(name="examPaperDAOImpl")
	public void setDao(ExamPaperDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}
	
	public List<PaperQuestion> getQuestionByPaperId(Integer paperId){
		return getDao().getQuestionByPaperId(paperId);
	}

	/* (non-Javadoc)
	 * @see com.vnetoo.examination.paper.service.ExamPaperService#getPaperIdsByScopeId(java.lang.Integer)
	 */
	public List<Integer> getPaperIdsByScopeId(Integer scopeId) {
		return getDao().getPaperIdsByScopeId(scopeId);
	}

	/* (non-Javadoc)
	 * @see com.vnetoo.examination.paper.service.ExamPaperService#getAnswerByPaperId(java.lang.Integer)
	 */
	public Map<Integer, List<QuestionAnswer>> getAnswersByPaperId(Integer paperId) {
		List<QuestionAnswer> list = getDao().getAnswersByPaperId(paperId);
		Map<Integer, List<QuestionAnswer>> map = new HashMap<Integer, List<QuestionAnswer>>();
		if(list != null && !list.isEmpty()){
			for(QuestionAnswer questionAnswer : list){
				Integer question = questionAnswer.getQuestionId();
				if(map.containsKey(question)){
					map.get(question).add(questionAnswer);
				}else{
					List<QuestionAnswer> answerList = new ArrayList<QuestionAnswer>();
					answerList.add(questionAnswer);
					map.put(question, answerList);
				}
			}
		}
		return map;
	}
}
