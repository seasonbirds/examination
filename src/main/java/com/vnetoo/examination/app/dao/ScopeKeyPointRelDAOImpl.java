package com.vnetoo.examination.app.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import com.vnetoo.examination.app.bo.ScopeKeyPointRel;

@Repository
@MybatisNamespace("ScopeKeyPointRel")
public class ScopeKeyPointRelDAOImpl extends BaseDAOImpl<ScopeKeyPointRel> implements
		ScopeKeyPointRelDAO {
	public Integer insert(ScopeKeyPointRel skpr){
		return this.getSqlSessionTemplate().insert(getStatement(), skpr);
	}
	
	public List<Integer> getKeyPointIdListByScopeId(Integer scopeId){
		return this.getSqlSessionTemplate().selectList(getStatement(), scopeId);
	}

	public Integer deleteRel(Integer scopeId) {
		return this.getSqlSessionTemplate().delete(getStatement(), scopeId);
	}
}
