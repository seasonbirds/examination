package com.vnetoo.examination.paper.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.vnetoo.common.AppContext;
import com.vnetoo.examination.policy.bo.CreatePaperResult;
import com.vnetoo.examination.policy.bo.ExamPolicy;
import com.vnetoo.examination.policy.service.ExamPolicyService;

/**
 * 试卷生成定时器
 */
public class AutoGeneratePaperTimer {
	private static final Logger log = Logger.getLogger(AutoGeneratePaperTimer.class);
	//定时器线程数
	private int threadSize = 1;
	//定时器运行时刻：每天2:00
	private int timePoint = 2;
	//定时器运行间隔：一天
	private long period = 24 * 60 * 60 * 1000;
	
	public void start(){
		Calendar current = Calendar.getInstance();
		long currentMillis = current.getTimeInMillis();
		//下一天的两点启动定时器
		if(current.get(Calendar.HOUR_OF_DAY) >= timePoint){
			current.add(Calendar.DAY_OF_MONTH, 1);
		}
		
		current.set(Calendar.HOUR_OF_DAY, timePoint);
		current.set(Calendar.MINUTE, 0);
		current.set(Calendar.SECOND, 0);
		current.set(Calendar.MILLISECOND, 0);
		
		long delay = current.getTimeInMillis() - currentMillis;
		ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(threadSize);
		executor.scheduleAtFixedRate(new AutoGeneratePaperTask(), delay, period, TimeUnit.MILLISECONDS);
		log.info("Timer for generated paper automatically starts successfully.");
	}
	
	/**
	 * 自动生成试卷的线程任务
	 */
	class AutoGeneratePaperTask implements Runnable{
		public void run() {
			log.info("Task for generated paper automatically begin to running.");
			try{
				ExamPolicyService examPolicyService = (ExamPolicyService) AppContext.getBean("examPolicyServiceImpl");
				List<ExamPolicy> list = examPolicyService.getPolicyWithAutoPaper();
				if(list == null || list.isEmpty()){
					return;
				}
				
				Calendar current = Calendar.getInstance();
				long currentDayMil = current.getTimeInMillis();
				//每个策略依次生成试卷
				for(ExamPolicy examPolicy : list){
					Date start = examPolicy.getExamTimeStart();
					Integer generateCycle = examPolicy.getGenerateCycle();
					long interval = currentDayMil - start.getTime();
					long day = interval / (24 * 60 * 60 * 1000);
					//时间间隔是周期的整数倍才生成试卷
					if((day != 0) && (day % generateCycle == 0)){
						 CreatePaperResult result = examPolicyService.createPaper(examPolicy.getId(), examPolicy.getCreatedBy(), null);
						 log.info(String.format("Create paper with policy id %d is complete, with the result code %s, result type %s and result level %s.",
	                    		 examPolicy.getId(),result.getCode(), result.getType(), result.getLev()));
					}
				}
			}catch(Exception e){
				log.error("Task for generated paper automatically occur exception", e);
			}
		}
	}

	public int getThreadSize() {
		return threadSize;
	}

	public void setThreadSize(int threadSize) {
		this.threadSize = threadSize;
	}

	public int getTimePoint() {
		return timePoint;
	}

	public void setTimePoint(int timePoint) {
		this.timePoint = timePoint;
	}

	public long getPeriod() {
		return period;
	}

	public void setPeriod(long period) {
		this.period = period;
	}
}
