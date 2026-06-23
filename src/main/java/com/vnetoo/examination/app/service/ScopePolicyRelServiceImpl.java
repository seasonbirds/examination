package com.vnetoo.examination.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.app.bo.ScopePolicyRel;
import com.vnetoo.examination.app.dao.ScopePolicyRelDAO;

@Service 
public class ScopePolicyRelServiceImpl extends BaseServiceImpl<ScopePolicyRel> implements
		ScopePolicyRelService {

	public ScopePolicyRelDAO getDao() {
		return (ScopePolicyRelDAO)super.dao;
	}
	
	@Resource(name="scopePolicyRelDAOImpl")
	public void setDao(ScopePolicyRelDAO dao) {
		super.dao = dao;
	}
	
	public Integer insert(ScopePolicyRel spr) {
		return dao.insert(spr);
	}
	
	public Integer getPolicyCountByScopeId(Integer scopeId){
		return getDao().getPolicyCountByScopeId(scopeId);
	}


	public Integer getPaperCountByScopeId(Integer scopeId) {
		return getDao().getPaperCountByScopeId(scopeId);
	}
	
	public Integer deleteRel(Integer policyId){
		return getDao().deleteRel(policyId);
	}

	public Integer getScopeIdByPolicyId(Integer policyId) {
		return getDao().getScopeIdByPolicyId(policyId);
	}
	
	public List<Integer> getPolicyIds(Integer scopeId){
		return getDao().getPolicyIds(scopeId);
	}
}
