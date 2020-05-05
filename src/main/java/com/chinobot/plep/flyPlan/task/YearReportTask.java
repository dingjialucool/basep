package com.chinobot.plep.flyPlan.task;


import java.time.LocalDateTime;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.chinobot.plep.home.report.entity.Report;
import com.chinobot.plep.home.report.service.IReportService;

/**
 * 
 * @ClassName: YearReportTask   
 * @Description: 年频率采查报告
 * @author: djl  
 * @date:2019年12月25日 上午11:08:15
 */
@Component
public class YearReportTask extends QuartzJobBean{

	@Autowired
	private IReportService reportService;
	
	private static final Logger log = LoggerFactory.getLogger(YearReportTask.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("年频率生成 采查报告");
		
		//获取年份
		int year = LocalDateTime.now().minusDays(7).getYear();
		
		String startTime = year + "-01-01";
		String endTime = year + "-12-31";
		
		Report report = new Report();
		report.setAreaId("f6d6ecb8b3a94c4d8c8d854e98a94019");//TODO 目前写死燕罗街道
		report.setFrequency("5");//频率年
		report.setType("1");//自动生成方式
		report.setYears(year);//年份
		report.setStartTime(startTime);//开始时间
		report.setEndTime(endTime);//结束时间
		reportService.save(report);
		
	}

	
}
