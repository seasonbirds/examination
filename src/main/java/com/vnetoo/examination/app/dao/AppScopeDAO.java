package com.vnetoo.examination.app.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.app.bo.AppScope;

/**
 * @comment DAO接口
 * @author chenh
 * @date 2013-11-11 
 */
public interface AppScopeDAO extends BaseDAO<AppScope>{
	
	public List<AppScope> getAppScopeByIds(Integer[] array);
	
	public List<AppScope> getAppScopeByTypeAndCourse(String type, Integer courseId);
}