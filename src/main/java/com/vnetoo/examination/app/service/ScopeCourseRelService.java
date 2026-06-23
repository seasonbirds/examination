package com.vnetoo.examination.app.service;

import java.util.List;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.app.bo.ScopeCourseRel;

public interface ScopeCourseRelService extends BaseService<ScopeCourseRel>{

	public Integer insert(ScopeCourseRel spr);
	
	public Integer deleteRel(Integer scopeId);
	
	public List<Integer> getCourseIdsByScopeId(Integer scopeId);
}
