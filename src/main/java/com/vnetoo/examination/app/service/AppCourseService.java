package com.vnetoo.examination.app.service;

import java.util.List;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.app.bo.AppCourse;

/**
 * @comment Service接口
 * @author chenh
 * @date 2013-11-11 
 */
public interface AppCourseService extends BaseService<AppCourse>{

	public List<AppCourse> getCourseListByScopId(Integer scopeId);
	
	public List<Integer> getCourseIdListByScopeId(Integer scopeId);
	
	public Integer insert(AppCourse appCourse);
}