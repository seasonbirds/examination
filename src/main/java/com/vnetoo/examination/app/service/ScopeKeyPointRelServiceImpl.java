package com.vnetoo.examination.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.app.bo.ScopeKeyPointRel;
import com.vnetoo.examination.app.dao.ScopeKeyPointRelDAO;

@Service 
public class ScopeKeyPointRelServiceImpl extends BaseServiceImpl<ScopeKeyPointRel> implements
		ScopeKeyPointRelService {

	public ScopeKeyPointRelDAO getDao() {
		// TODO Auto-generated method stub
		return (ScopeKeyPointRelDAO)super.dao;
	}
	
	@Resource(name="scopeKeyPointRelDAOImpl")
	public void setDao(ScopeKeyPointRelDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}

	public boolean update(Integer scopeId, String keyPointIds) {
		dao.delete(scopeId);
		
		if(keyPointIds != null && !"".equals(keyPointIds.trim())){
			String[] ids = keyPointIds.split(",");
			for(String id : ids){
				ScopeKeyPointRel skpr = new ScopeKeyPointRel();
				skpr.setScopeId(scopeId);
				skpr.setKeyPointId(Integer.parseInt(id));
				Integer result = dao.insert(skpr);
				if(result == null || result <= 0){
					return false;
				}
			}
		}
		return true;
	}
	
	public List<Integer> getKeyPointIdListByScopeId(Integer scopeId){
		return getDao().getKeyPointIdListByScopeId(scopeId);
	}
}
