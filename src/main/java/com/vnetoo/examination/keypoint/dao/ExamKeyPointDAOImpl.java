package com.vnetoo.examination.keypoint.dao;

import java.util.List;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.BaseDAOImpl;
import org.springframework.stereotype.Repository;
import com.vnetoo.examination.keypoint.bo.ExamKeyPoint;

/**
 * @comment 知识点Service实现类
 * @author chenh
 * @date 2013-11-08 
 */
@Repository
@MybatisNamespace("ExamKeyPoint")
public class ExamKeyPointDAOImpl extends BaseDAOImpl<ExamKeyPoint> implements ExamKeyPointDAO{

	public Integer getNumOfSubKeyPointByPid(Integer pid) {
		return this.getSqlSessionTemplate().selectOne(getStatement(), pid);
	}
	
	public List<Integer> getKeyPointIdListByCourseId(List<Integer> list){
		return this.getSqlSessionTemplate().selectList(getStatement(), list);
	}
}
