package com.vnetoo.examination.paper.service;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.paper.bo.PaperQuestionRel;

public interface PaperQuestionRelService extends BaseService<PaperQuestionRel> {

	public Integer insert(PaperQuestionRel pqr);
	
	public void insertBatch(List<PaperQuestionRel> list);
	
	public List<Integer> getQuestionIdsByPaperId(Integer paperId);
	
	public Integer getQuestionNumberByPaperIds(List<Integer> paperIds);
	
	public Map<String, List<Integer>> getQuestionTypeAndIdsByPaperId(List<Integer> paperIds);
}
