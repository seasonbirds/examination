package com.vnetoo.examination.policy.dao;

import java.util.List;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;

import org.springframework.stereotype.Repository;

import com.vnetoo.examination.policy.bo.ExamPolicy;

/**
 * @comment 出题策略Service实现类
 * @author chenh
 * @date 2013-11-13 
 */
@Repository
@MybatisNamespace("ExamPolicy")
public class ExamPolicyDAOImpl extends BaseDAOImpl<ExamPolicy> implements ExamPolicyDAO{
	
	public List<ExamPolicy> getPolicyWithAutoPaper(){
		return this.getSqlSessionTemplate().selectList(getStatement());
	}
	
	public List<ExamPolicy> getPolicyByScopeIds(List<Integer> policyIds){
		return this.getSqlSessionTemplate().selectList(getStatement(), policyIds);
	}
	
	public ExamPolicy getPolicyByPaperGuid(String guid){
		return this.getSqlSessionTemplate().selectOne(getStatement(), guid);
	}
}
