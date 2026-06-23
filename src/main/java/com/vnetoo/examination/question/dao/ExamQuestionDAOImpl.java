package com.vnetoo.examination.question.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import org.springframework.stereotype.Repository;
import com.vnetoo.examination.question.bo.ExamQuestion;

/**
 * @comment 题目表Service实现类
 * @author chenh
 * @date 2013-11-08 
 */
@Repository
@MybatisNamespace("ExamQuestion")
public class ExamQuestionDAOImpl extends BaseDAOImpl<ExamQuestion> implements ExamQuestionDAO{
	
	public Map<String, Integer> getQuestionTypeAndCountByKeyPointsAndRangeType(List<Integer> keyPointIds, String rangeType){
		Map<String, Object>paraMap = new HashMap<String, Object>();
		paraMap.put("list", keyPointIds);
		paraMap.put("rangeType", rangeType);
		List<Map<String, Object>> listMap = this.getSqlSessionTemplate().selectList(getStatement(), paraMap);
		Map<String, Integer> map = new HashMap<String, Integer>();
		for(Map<String, Object> tmp : listMap){
			map.put(tmp.get("TYPE").toString(), Integer.parseInt(tmp.get("TYPENUM").toString()));
		}
		return map;
	}
	
	public List<Integer> getQuestionIds(String type, String lev, List<Integer>keyPointIds, String rangeType){
			Map<String, Object>paraMap = new HashMap<String, Object>();
		paraMap.put("type", type);
		paraMap.put("lev", lev);
		paraMap.put("rangeType", rangeType);
		paraMap.put("list", keyPointIds);
		return this.getSqlSessionTemplate().selectList(getStatement(), paraMap);
	}

	public Integer getQuestionCount(Map<String, Object> map){
		return this.getSqlSessionTemplate().selectOne(getStatement(), map);
	}
	
	public Map<String, List<Integer>> getQuestionTypeAndIds(Map<String, Object> params){
		List<Map<String, Object>> listMap = this.getSqlSessionTemplate().selectList(getStatement(), params);
		Map<String, List<Integer>> map = new HashMap<String, List<Integer>>();
		for(Map<String, Object> tmp : listMap){
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
