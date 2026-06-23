package com.vnetoo.examination.paper.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.paper.bo.PaperQuestionRel;
import com.vnetoo.examination.paper.dao.PaperQuestionRelDAO;

@Service 
public class PaperQuestionRelServiceImpl extends BaseServiceImpl<PaperQuestionRel> implements
		PaperQuestionRelService {

	public PaperQuestionRelDAO getDao() {
		return (PaperQuestionRelDAO)super.dao;
	}
	
	@Resource(name="paperQuestionRelDAOImpl")
	public void setDao(PaperQuestionRelDAO dao) {
		super.dao = dao;
	}
	
	public Integer insert(PaperQuestionRel pqr) {
		return getDao().insert(pqr);
	}

	public void insertBatch(List<PaperQuestionRel> list) {
		getDao().insertBatch(list);
	}

	public List<Integer> getQuestionIdsByPaperId(Integer paperId){
		return getDao().getQuestionIdsByPaperId(paperId);
	}
	
	public Integer getQuestionNumberByPaperIds(List<Integer> paperIds){
		return getDao().getQuestionNumberByPaperIds(paperIds);
	}
	
	public Map<String, List<Integer>> getQuestionTypeAndIdsByPaperId(List<Integer> paperIds){
		return getDao().getQuestionTypeAndIdsByPaperId(paperIds);
	}
}
