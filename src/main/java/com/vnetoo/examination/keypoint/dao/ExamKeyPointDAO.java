package com.vnetoo.examination.keypoint.dao;

import java.util.List;

import com.vnetoo.common.dao.BaseDAO;
import com.vnetoo.examination.keypoint.bo.ExamKeyPoint;

/**
 * @comment 知识点DAO接口
 * @author chenh
 * @date 2013-11-08 
 */
public interface ExamKeyPointDAO extends BaseDAO<ExamKeyPoint>{
	
	public Integer getNumOfSubKeyPointByPid(Integer pid);
	
	public List<Integer> getKeyPointIdListByCourseId(List<Integer> list);
}