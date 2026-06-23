package com.vnetoo.examination.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import com.vnetoo.examination.app.bo.ScopePolicyRel;

@Repository
@MybatisNamespace("ScopePolicyRel")
public class ScopePolicyRelDAOImpl extends BaseDAOImpl<ScopePolicyRel> implements
		ScopePolicyRelDAO {

	public Integer insert(ScopePolicyRel spr){
		return this.getSqlSessionTemplate().insert(getStatement(), spr);
	}

	public Integer getPolicyCountByScopeId(Integer scopeId) {
		return this.getSqlSessionTemplate().selectOne(getStatement(), scopeId);
	}
	
	public Integer getPaperCountByScopeId(Integer scopeId){
		return this.getSqlSessionTemplate().selectOne(getStatement(), scopeId);
	}
	
	public Integer deleteRel(Integer policyId){
		return this.getSqlSessionTemplate().delete(getStatement(), policyId);
	}

	public Integer getScopeIdByPolicyId(Integer policyId) {
		return this.getSqlSessionTemplate().selectOne(getStatement(), policyId);
	}

	public List<Integer> getPolicyIds(Integer scopeId) {
		return this.getSqlSessionTemplate().selectList(getStatement(), scopeId);
	}
}
