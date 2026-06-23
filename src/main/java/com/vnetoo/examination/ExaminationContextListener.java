/**
 * 
 */
package com.vnetoo.examination;

import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vnetoo.common.view.ServletContextListener;
import com.vnetoo.examination.paper.timer.AutoGeneratePaperTimer;

/**
 * @author qinwei
 *
 */
public class ExaminationContextListener extends ServletContextListener {
	private static final Logger log = LoggerFactory.getLogger(ExaminationContextListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		//自动生成试题定时器启动
		AutoGeneratePaperTimer timer = new AutoGeneratePaperTimer();
		timer.start();
		
		FreeMarketUtil.initConfig(event.getServletContext(), "/WEB-INF/htm");
		
		try {
			RoleUrlUtil.init();
		} catch (Exception e) {
			log.error("Application error!", e);
			System.exit(0);
		}
	}

}
