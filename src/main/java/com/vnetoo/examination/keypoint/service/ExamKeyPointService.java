package com.vnetoo.examination.keypoint.service;

import java.util.List;

import com.vnetoo.common.service.BaseService;
import com.vnetoo.examination.keypoint.bo.ExamKeyPoint;

/**
 * @comment 知识点Service接口
 * @author chenh
 * @date 2013-11-08 
 */
public interface ExamKeyPointService extends BaseService<ExamKeyPoint>{

	public Integer getNumOfSubKeyPointByPid(Integer pid);
	
	public List<Integer> getKeyPointIdListByCourseId(List<Integer> list);
}