package com.vnetoo.examination.app.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;

import org.springframework.stereotype.Repository;

import com.vnetoo.examination.app.bo.AppScope;

/**
 * @comment Service实现类
 * @author chenh
 * @date 2013-11-11 
 */
@Repository
@MybatisNamespace("AppScope")
public class AppScopeDAOImpl extends BaseDAOImpl<AppScope> implements AppScopeDAO{
	
	public List<AppScope> getAppScopeByIds(Integer[] array){
		return this.getSqlSessionTemplate().selectList(getStatement(), array);
	}
	
	public List<AppScope> getAppScopeByTypeAndCourse(String type, Integer courseId){
		if(type == null || courseId == null){
			return null;
		}
		
		Map<String, Object>paraMap = new HashMap<String, Object>();
		paraMap.put("type", type);
		paraMap.put("courseId", courseId);
		
		return this.getSqlSessionTemplate().selectList(getStatement(), paraMap);
	}
}
