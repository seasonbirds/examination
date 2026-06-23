package com.vnetoo.examination.paper.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import com.vnetoo.examination.paper.bo.ExamStudentAnswer;

@Repository
@MybatisNamespace("ExamStudentAnswer")
public class ExamStudentAnswerDAOImpl extends BaseDAOImpl<ExamStudentAnswer>
		implements ExamStudentAnswerDAO {

	public List<ExamStudentAnswer> getStudentAnswerByPaperIdAndSId(Integer sid, List<Integer> paperIds){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("studentId", sid);
		map.put("paperIds", paperIds);
		return this.getSqlSessionTemplate().selectList(getStatement(), map);
	}
	
	public Map<Integer, Integer> getAnswerStatusByScopeIdsAndSId(List<Integer> scopeIds, Integer studentId){
		if(scopeIds == null || scopeIds.isEmpty() || studentId == null || studentId <= 0){
			return null;
		}
		
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("studentId", studentId);
		paraMap.put("scopeIds", scopeIds);
		
		List<Map<String, Object>> listMap = this.getSqlSessionTemplate().selectList(getStatement(), paraMap);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(Map<String, Object> tmp : listMap){
			map.put(Integer.parseInt(tmp.get("SCOPE_ID").toString()), Integer.parseInt(tmp.get("STATUS").toString()));
		}
		
		return map;
	}
}
