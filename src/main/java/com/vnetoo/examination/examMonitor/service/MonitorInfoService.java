package com.vnetoo.examination.examMonitor.service;

import java.util.List;

import com.vnetoo.common.page.Paginator;
import com.vnetoo.examination.examMonitor.bo.MonitorInfo;

public interface MonitorInfoService {

	public List<MonitorInfo> selectPage(MonitorInfo mi, Paginator<MonitorInfo> page);
}
