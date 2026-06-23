package com.vnetoo.examination.keypoint.service;

import java.util.List;

import javax.annotation.Resource;
import com.vnetoo.common.service.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.vnetoo.examination.keypoint.bo.ExamKeyPoint;
import com.vnetoo.examination.keypoint.dao.ExamKeyPointDAO;

/**
 * @comment 知识点Service实现类
 * @author chenh
 * @date 2013-11-08 
 */
@Service 
public class ExamKeyPointServiceImpl extends BaseServiceImpl<ExamKeyPoint> implements ExamKeyPointService{
	
	public ExamKeyPointDAO getDao() {
		// TODO Auto-generated method stub
		return (ExamKeyPointDAO)super.dao;
	}
	
	@Resource(name="examKeyPointDAOImpl")
	public void setDao(ExamKeyPointDAO dao) {
		// TODO Auto-generated method stub
		super.dao = dao;
	}

	public Integer getNumOfSubKeyPointByPid(Integer pid) {
		return getDao().getNumOfSubKeyPointByPid(pid);
	}
	
	public List<Integer> getKeyPointIdListByCourseId(List<Integer> list){
		return getDao().getKeyPointIdListByCourseId(list);
	}
}
