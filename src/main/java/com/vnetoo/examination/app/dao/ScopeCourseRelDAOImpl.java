package com.vnetoo.examination.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import com.vnetoo.examination.app.bo.ScopeCourseRel;

@Repository
@MybatisNamespace("ScopeCourseRel")
public class ScopeCourseRelDAOImpl extends BaseDAOImpl<ScopeCourseRel> implements
		ScopeCourseRelDAO {
	
	public Integer insert(ScopeCourseRel scr){
		return this.getSqlSessionTemplate().insert(getStatement(), scr);
	}
	
	public Integer deleteRel(Integer scopeId) {
		return this.getSqlSessionTemplate().delete(getStatement(), scopeId);
	}

	public List<Integer> getCourseIdsByScopeId(Integer scopeId) {
		return this.getSqlSessionTemplate().selectList(getStatement(), scopeId);
	}
}
