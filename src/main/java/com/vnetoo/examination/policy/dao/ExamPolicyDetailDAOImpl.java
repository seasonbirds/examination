package com.vnetoo.examination.policy.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import org.springframework.stereotype.Repository;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;

/**
 * @comment 出题策略明细Service实现类
 * @author chenh
 * @date 2013-11-13 
 */
@Repository
@MybatisNamespace("ExamPolicyDetail")
public class ExamPolicyDetailDAOImpl extends BaseDAOImpl<ExamPolicyDetail> implements ExamPolicyDetailDAO{
	
	public void deleteByPolicyId(Integer policyId){
		this.getSqlSessionTemplate().update(this.getStatement(), policyId);
	}
	
	public Map<Integer, Integer> getQuestionNumberByPolicys(List<Integer> list){
		List<Map<String, Object>> listMap = this.getSqlSessionTemplate().selectList(getStatement(), list);
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		for(Map<String, Object> tmp : listMap){
			map.put( Integer.parseInt(tmp.get("EPID").toString()), Integer.parseInt(tmp.get("QNUM").toString()));
		}
		return map;
	}
}
