package com.vnetoo.examination.app.bo;

import com.vnetoo.common.bo.BaseObject;

public class ScopePolicyRel implements BaseObject {

	private Integer scopeId;
	
	private Integer policyId;
	
	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	public Integer getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Integer policyId) {
		this.policyId = policyId;
	}

	public Integer getId() {
		return null;
	}

}
