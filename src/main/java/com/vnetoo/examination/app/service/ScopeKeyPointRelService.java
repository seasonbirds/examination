package com.vnetoo.examination.app.service;

import java.util.List;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.app.bo.ScopeKeyPointRel;

public interface ScopeKeyPointRelService extends BaseService<ScopeKeyPointRel> {

	public boolean update(Integer scopeId, String keyPointIds);
	
	public List<Integer> getKeyPointIdListByScopeId(Integer scopeId);
}
