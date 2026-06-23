package com.vnetoo.examination.examMonitor.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.vnetoo.common.page.Paginator;
import com.vnetoo.examination.examMonitor.bo.MonitorInfo;
import com.vnetoo.examination.examMonitor.dao.MonitorInfoDAO;

@Service 
@SuppressWarnings("restriction")
public class MonitorInfoServiceImpl implements MonitorInfoService {

	@Resource
	private MonitorInfoDAO monitorInfoDAO;
	
	public List<MonitorInfo> selectPage(MonitorInfo mi,
			Paginator<MonitorInfo> page) {
		return monitorInfoDAO.selectPage(mi, page);
	}

}
