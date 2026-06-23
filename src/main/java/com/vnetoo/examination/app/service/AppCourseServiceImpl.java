package com.vnetoo.examination.app.service;

import java.util.List;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.vnetoo.examination.app.bo.AppCourse;
import com.vnetoo.examination.app.dao.AppCourseDAO;

/**
 * @comment Service实现类
 * @author chenh
 * @date 2013-11-11 
 */
@Service 
public class AppCourseServiceImpl extends BaseServiceImpl<AppCourse> implements AppCourseService{
	
	public AppCourseDAO getDao() {
		// TODO Auto-generated method stub
		return (AppCourseDAO)super.dao;
	}
	
	@Resource(name="appCourseDAOImpl")
	public void setDao(AppCourseDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}

	public List<AppCourse> getCourseListByScopId(Integer scopeId) {
		return getDao().getCourseListByScopeId(scopeId);
	}
	
	public List<Integer> getCourseIdListByScopeId(Integer scopeId){
		return getDao().getCourseIdListByScopeId(scopeId);
	}
	
	public Integer insert(AppCourse appCourse){
		return getDao().insert(appCourse);
	}
}
