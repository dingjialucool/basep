package com.chinobot.plep.flyPlan.task;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

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
 * @ClassName: QuarterReportTask   
 * @Description: 季度频率生成采查报告  
 * @author: djl  
 * @date:2019年12月25日 上午10:25:16
 */
@Component
public class QuarterReportTask extends QuartzJobBean{

	@Autowired
	private IReportService reportService;
	
	private static final Logger log = LoggerFactory.getLogger(QuarterReportTask.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("季度频率生成 采查报告");
		int quarterofYear = 0;
		try {
			quarterofYear = getQuarterofYear();
		} catch (ParseException e) {
			log.info("季度频率生成 采查报告 出错");
			e.printStackTrace();
		}
		
		//获取年份
		int year = LocalDateTime.now().minusDays(7).getYear();
		
		String startTime = "";
		String endTime = "";
		switch (quarterofYear) {
		case 1:
			startTime = year + "-01-01";
			endTime = year + "-03-31";
			break;
		case 2:
			startTime = year + "-04-01";
			endTime = year + "-06-30";
			break;
		case 3:
			startTime = year + "-07-01";
			endTime = year + "-09-30";
			break;
		case 4:
			startTime = year + "-10-01";
			endTime = year + "-12-31";
			break;
		default:
			break;
		}
		
		
		Report report = new Report();
		report.setAreaId("f6d6ecb8b3a94c4d8c8d854e98a94019");//TODO 目前写死燕罗街道
		report.setFrequency("4");//频率季度
		report.setType("1");//自动生成方式
		report.setCycle(quarterofYear);//日期是当年的第几个季度
		report.setYears(year);//年份
		report.setStartTime(startTime);//开始时间
		report.setEndTime(endTime);//结束时间
		reportService.save(report);
		
	}

	/**
	 * 判断日期属于今年的第几个季度
	 * @return
	 * @throws ParseException 
	 */
	private int getQuarterofYear() throws ParseException {
		
		LocalDateTime now = LocalDateTime.now();//当前时间
		LocalDateTime minusDays = now.minusDays(7);//前一周
		
		int month = minusDays.getMonthValue();
		
		return month/4+1;
	}
	
}
