package com.vnetoo.examination.paper.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import com.vnetoo.examination.paper.bo.PaperQuestionRel;

@Repository
@MybatisNamespace("PaperQuestionRel")
public class PaperQuestionRelDAOImpl extends BaseDAOImpl<PaperQuestionRel> implements
		PaperQuestionRelDAO {

	public Integer insert(PaperQuestionRel pqr){
		return this.getSqlSessionTemplate().insert(getStatement(), pqr);
	}
	
	public void insertBatch(List<PaperQuestionRel> list) {
		this.getSqlSessionTemplate().insert(getStatement(), list);
	}
	
	public List<Integer> getQuestionIdsByPaperId(Integer paperId){
		return this.getSqlSessionTemplate().selectList(getStatement(), paperId);
	}
	
	public Integer getQuestionNumberByPaperIds(List<Integer> paperIds){
		return this.getSqlSessionTemplate().selectOne(getStatement(), paperIds);
	}
	
	public Map<String, List<Integer>> getQuestionTypeAndIdsByPaperId(List<Integer> paperIds){
		List<Map<String, Object>> list = this.getSqlSessionTemplate().selectList(getStatement(), paperIds);
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		for(Map<String, Object> tmp : list){
			String key = tmp.get("TYPE").toString();
			Integer id = Integer.parseInt(tmp.get("ID").toString());
			if(map.containsKey(key)){
				map.get(key).add(id);
			}else{
				List<Integer> l = new ArrayList<Integer>();
				l.add(id);
				map.put(key, l);
			}
		}
		
		return map;
	}
}
