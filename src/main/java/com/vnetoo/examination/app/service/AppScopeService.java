package com.vnetoo.examination.app.service;

import java.util.List;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.app.bo.AppScope;

/**
 * @comment Service接口
 * @author chenh
 * @date 2013-11-11 
 */
public interface AppScopeService extends BaseService<AppScope>{

	/**
	 * 删除考试记录及相关数据
	 */
	public void delete(Integer appscopeId);
	
	/**
	 * 保存考试及相关记录
	 */
	public void save(AppScope appScope, Boolean courseModFlag, String courseIds, Boolean keyPointModFlag, String keyPointIds);
	
	public List<AppScope> getAppScopeByIds(Integer[] array);
	
	/**
	 * 根据考试类型和考试课程ID查找考试
	 */
	public List<AppScope> getAppScopeByTypeAndCourse(String type, Integer courseId);
}