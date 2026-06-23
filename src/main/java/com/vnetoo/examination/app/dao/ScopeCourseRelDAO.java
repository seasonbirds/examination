package com.vnetoo.examination.app.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.app.bo.ScopeCourseRel;

public interface ScopeCourseRelDAO extends BaseDAO<ScopeCourseRel> {

	public Integer insert(ScopeCourseRel spr);
	
	public Integer deleteRel(Integer scopeId);
	
	public List<Integer> getCourseIdsByScopeId(Integer scopeId);
	
}
