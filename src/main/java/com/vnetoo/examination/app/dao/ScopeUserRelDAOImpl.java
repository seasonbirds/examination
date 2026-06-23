package com.vnetoo.examination.app.dao;

import org.springframework.stereotype.Repository;
import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import com.vnetoo.examination.app.bo.ScopeUserRel;

@Repository
@MybatisNamespace("ScopeUserRel")
public class ScopeUserRelDAOImpl extends BaseDAOImpl<ScopeUserRel> implements
		ScopeUserRelDAO {

	public void insertRel(ScopeUserRel scopeUserRel){
		this.getSqlSessionTemplate().insert(getStatement(), scopeUserRel);
	}
}
