package com.vnetoo.examination.examMonitor.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.vnetoo.common.annotation.MybatisNamespace;
import com.vnetoo.common.dao.PageParam;
import com.vnetoo.common.page.Paginator;
import com.vnetoo.examination.examMonitor.bo.MonitorInfo;

@Repository
@MybatisNamespace("MonitorInfo")
@SuppressWarnings("restriction")
public class MonitorInfoDAOImpl implements MonitorInfoDAO {
	private static final Logger logger = Logger.getLogger(MonitorInfoDAOImpl.class);
	
	@Resource
	public SqlSessionTemplate sqlSessionTemplate;
	
	public List<MonitorInfo> selectPage(MonitorInfo mi,
			Paginator<MonitorInfo> page) {
		return sqlSessionTemplate.selectList(getStatement(), new PageParam<MonitorInfo>(mi, page));
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
