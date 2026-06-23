package com.vnetoo.examination.app.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.app.bo.AppCourse;

/**
 * @comment DAO接口
 * @author chenh
 * @date 2013-11-11 
 */
public interface AppCourseDAO extends BaseDAO<AppCourse>{
	
	public List<AppCourse> getCourseListByScopeId(Integer scopeId);
	
	public List<Integer> getCourseIdListByScopeId(Integer scopeId);
}