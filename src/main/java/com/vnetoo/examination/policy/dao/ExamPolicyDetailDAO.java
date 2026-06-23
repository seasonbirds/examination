package com.vnetoo.examination.policy.dao;

import java.util.List;
import java.util.Map;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.policy.bo.ExamPolicyDetail;

/**
 * @comment 出题策略明细DAO接口
 * @author chenh
 * @date 2013-11-13 
 */
public interface ExamPolicyDetailDAO extends BaseDAO<ExamPolicyDetail>{
	
	public void deleteByPolicyId(Integer policyId);
	
	public Map<Integer, Integer> getQuestionNumberByPolicys(List<Integer> list);
}