package com.vnetoo.examination.paper.dao;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import com.vnetoo.examination.paper.bo.ExamStudentAnswerDetail;

@Repository
@MybatisNamespace("ExamStudentAnswerDetail")
public class ExamStudentAnswerDetailDAOImpl extends BaseDAOImpl<ExamStudentAnswerDetail>
				implements ExamStudentAnswerDetailDAO {

	public Map<Integer, Integer> getAnswerNumberByAnswerId(List<Integer> answerIds){
		List<Map<String, Object>> listMap = this.getSqlSessionTemplate().selectList(getStatement(), answerIds);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(Map<String, Object> tmp : listMap){
			map.put( Integer.parseInt(tmp.get("SAID").toString()), Integer.parseInt(tmp.get("ANUM").toString()));
		}
		return map;
	}
	
	public Map<Integer, Integer> getScoreByAnswerId(List<Integer> answerIds){
		List<Map<String, Object>> listMap = this.getSqlSessionTemplate().selectList(getStatement(), answerIds);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(Map<String, Object> tmp : listMap){
			map.put( Integer.parseInt(tmp.get("SAID").toString()), Integer.parseInt(tmp.get("ASCORE").toString()));
		}
		return map;
	}
}
