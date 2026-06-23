package com.vnetoo.examination.app.dao;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.app.bo.ScopeUserRel;

public interface ScopeUserRelDAO extends BaseDAO<ScopeUserRel> {

	public void insertRel(ScopeUserRel scopeUserRel);
}
