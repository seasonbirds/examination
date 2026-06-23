package com.vnetoo.examination.app.bo;

import com.vnetoo.common.bo.BaseObject;

public class ScopeUserRel implements BaseObject {
	
	/**
	 * @alias 考试编号
	 */
	protected java.lang.Integer scopeId;
	
	/**
	 * @alias 用户编号
	 */
	protected java.lang.Integer userId;

	public Integer getId() {
		return null;
	}

	public java.lang.Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(java.lang.Integer scopeId) {
		this.scopeId = scopeId;
	}

	public java.lang.Integer getUserId() {
		return userId;
	}

	public void setUserId(java.lang.Integer userId) {
		this.userId = userId;
	}
}
