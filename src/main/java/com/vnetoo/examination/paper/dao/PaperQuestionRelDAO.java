package com.vnetoo.examination.paper.dao;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.paper.bo.PaperQuestionRel;

public interface PaperQuestionRelDAO extends BaseDAO<PaperQuestionRel> {

	public Integer insert(PaperQuestionRel pqr);
	
	public void insertBatch(List<PaperQuestionRel> list);
	
	public List<Integer> getQuestionIdsByPaperId(Integer paperId);
	
	public Integer getQuestionNumberByPaperIds(List<Integer> paperIds);
	
	public Map<String, List<Integer>> getQuestionTypeAndIdsByPaperId(List<Integer> paperIds);
}
