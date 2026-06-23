package com.vnetoo.examination.app.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.app.bo.ScopeKeyPointRel;

public interface ScopeKeyPointRelDAO extends BaseDAO<ScopeKeyPointRel> {

	public Integer insert(ScopeKeyPointRel skpr);
	
	public List<Integer> getKeyPointIdListByScopeId(Integer scopeId);
	
	public Integer deleteRel(Integer scopeId);
}
