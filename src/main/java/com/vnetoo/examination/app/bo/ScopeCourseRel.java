package com.vnetoo.examination.app.bo;

import com.vnetoo.common.bo.BaseObject;

public class ScopeCourseRel implements BaseObject {
	private Integer scopeId;
	
	private Integer courseId;

	public Integer getScopeId() {
		return scopeId;
	}

	public void setScopeId(Integer scopeId) {
		this.scopeId = scopeId;
	}

	public Integer getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}
	
	public Integer getId() {
		return null;
	}
}
