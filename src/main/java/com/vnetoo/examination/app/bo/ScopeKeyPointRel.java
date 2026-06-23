package com.vnetoo.examination.app.bo;

import com.vnetoo.common.bo.BaseObject;

public class ScopeKeyPointRel implements BaseObject {

	private Integer scopeId;
	
	private Integer keyPointId;
	
	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	public Integer getKeyPointId() {
		return keyPointId;
	}

	public void setKeyPointId(Integer keyPointId) {
		this.keyPointId = keyPointId;
	}

	public Integer getId() {
		return null;
	}
}
