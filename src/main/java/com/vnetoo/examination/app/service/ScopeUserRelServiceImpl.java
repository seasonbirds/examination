package com.vnetoo.examination.app.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.app.bo.ScopeUserRel;
import com.vnetoo.examination.app.dao.ScopeUserRelDAO;

@Service 
public class ScopeUserRelServiceImpl extends BaseServiceImpl<ScopeUserRel>
		implements ScopeUserRelService {

	public ScopeUserRelDAO getDao() {
		return (ScopeUserRelDAO)super.dao;
	}
	
	@Resource(name="scopeUserRelDAOImpl")
	public void setDao(ScopeUserRelDAO dao) {
		super.dao = dao;
	}
	
	public void insertRel(ScopeUserRel scopeUserRel){
		getDao().insertRel(scopeUserRel);
	}
}
