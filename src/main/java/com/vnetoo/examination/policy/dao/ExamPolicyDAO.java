package com.vnetoo.examination.policy.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.policy.bo.ExamPolicy;

/**
 * @comment 出题策略DAO接口
 * @author chenh
 * @date 2013-11-13 
 */
public interface ExamPolicyDAO extends BaseDAO<ExamPolicy>{
	
	public List<ExamPolicy> getPolicyWithAutoPaper();
	
	public List<ExamPolicy> getPolicyByScopeIds(List<Integer> policyIds);
	
	public ExamPolicy getPolicyByPaperGuid(String guid);
}