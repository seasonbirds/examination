package com.vnetoo.examination.app.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.service.BaseServiceImpl;
import com.vnetoo.examination.app.bo.ScopeCourseRel;
import com.vnetoo.examination.app.dao.ScopeCourseRelDAO;

@Service
public class ScopeCourseRelServiceImpl extends BaseServiceImpl<ScopeCourseRel>
		implements ScopeCourseRelService {

	public ScopeCourseRelDAO getDao() {
		return (ScopeCourseRelDAO)super.dao;
	}
	
	@Resource(name="scopeCourseRelDAOImpl")
	public void setDao(ScopeCourseRelDAO dao) {
		super.dao = dao;
	}
	
	public Integer insert(ScopeCourseRel spr) {
		return getDao().insert(spr);
	}

	public Integer deleteRel(Integer scopeId) {
		return getDao().deleteRel(scopeId);
	}

	public List<Integer> getCourseIdsByScopeId(Integer scopeId) {
		return getDao().getCourseIdsByScopeId(scopeId);
	}

}
