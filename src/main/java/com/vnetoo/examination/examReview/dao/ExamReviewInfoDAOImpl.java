package com.vnetoo.examination.examReview.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.PageParam;
import com.vnetoo.common.page.Paginator;
import com.vnetoo.examination.examMonitor.dao.MonitorInfoDAOImpl;
import com.vnetoo.examination.examReview.bo.ExamReviewInfo;

@Repository
@MybatisNamespace("ExamReviewInfo")
@SuppressWarnings("restriction")
public class ExamReviewInfoDAOImpl implements ExamReviewInfoDAO {
	private static final Logger logger = Logger.getLogger(MonitorInfoDAOImpl.class);
	
	@Resource
	public SqlSessionTemplate sqlSessionTemplate;
	
	public List<ExamReviewInfo> selectPage(ExamReviewInfo eri,
			Paginator<ExamReviewInfo> page) {
		return sqlSessionTemplate.selectList(getStatement(), new PageParam<ExamReviewInfo>(eri, page));
	}
	
	private String getStatement(){
		MybatisNamespace namespace = getClass().getAnnotation(MybatisNamespace.class);
		String name = namespace!=null?namespace.value():"";
		StackTraceElement[] stackElements = new Throwable().getStackTrace();
		if(stackElements.length>1){
			logger.info("SQL Mapper ==> "+name+"."+stackElements[1].getMethodName());
			return name+"."+stackElements[1].getMethodName();
		}
		logger.error("SQL Mapper ==> "+name);
		return name;
	}

}
