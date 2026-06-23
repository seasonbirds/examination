package com.vnetoo.examination.app.dao;

import java.util.List;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import org.springframework.stereotype.Repository;
import com.vnetoo.examination.app.bo.AppCourse;

/**
 * @comment Service实现类
 * @author chenh
 * @date 2013-11-11 
 */
@Repository
@MybatisNamespace("AppCourse")
public class AppCourseDAOImpl extends BaseDAOImpl<AppCourse> implements AppCourseDAO{

	public List<AppCourse> getCourseListByScopeId(Integer scopeId) {
		return getSqlSessionTemplate().selectList(getStatement(), scopeId);
	}
	
	public List<Integer> getCourseIdListByScopeId(Integer scopeId){
		return getSqlSessionTemplate().selectList(getStatement(), scopeId);
	}
	
}
