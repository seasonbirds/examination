package com.vnetoo.examination.app.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.app.bo.ScopePolicyRel;

public interface ScopePolicyRelDAO extends BaseDAO<ScopePolicyRel> {

	public Integer insert(ScopePolicyRel spr);
	
	public Integer getPolicyCountByScopeId(Integer scopeId);
	
	public Integer getPaperCountByScopeId(Integer scopeId);
	
	public Integer deleteRel(Integer policyId);
	
	public Integer getScopeIdByPolicyId(Integer policyId);
	
	public List<Integer> getPolicyIds(Integer scopeId);
}
