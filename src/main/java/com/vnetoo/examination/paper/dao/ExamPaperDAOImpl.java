package com.vnetoo.examination.paper.dao;

import java.util.List;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import org.springframework.stereotype.Repository;
import com.vnetoo.examination.paper.bo.ExamPaper;
import com.vnetoo.examination.paper.bo.PaperQuestion;
import com.vnetoo.examination.paper.bo.QuestionAnswer;

/**
 * @comment 试卷表Service实现类
 * @author chenh
 * @date 2013-11-13 
 */
@Repository
@MybatisNamespace("ExamPaper")
public class ExamPaperDAOImpl extends BaseDAOImpl<ExamPaper> implements ExamPaperDAO{

	/* (non-Javadoc)
	 * @see com.vnetoo.examination.paper.dao.ExamPaperDAO#getQuestionByPaperId(java.lang.Integer)
	 */
	public List<PaperQuestion> getQuestionByPaperId(Integer paperId) {
		return this.getSqlSessionTemplate().selectList(getStatement(), paperId);
	}

	/* (non-Javadoc)
	 * @see com.vnetoo.examination.paper.dao.ExamPaperDAO#getPaperIdsByScopeId(java.lang.Integer)
	 */
	public List<Integer> getPaperIdsByScopeId(Integer scopeId) {
		return this.getSqlSessionTemplate().selectList(getStatement(), scopeId);
	}

	/* (non-Javadoc)
	 * @see com.vnetoo.examination.paper.dao.ExamPaperDAO#getAnswerByPaperId(java.lang.Integer)
	 */
	public List<QuestionAnswer> getAnswersByPaperId(Integer paperId) {
		return this.getSqlSessionTemplate().selectList(getStatement(), paperId);
	}
	
}
