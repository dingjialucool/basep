package com.chinobot.plep.flyPlan.task;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
 * @ClassName: MonthReportTask   
 * @Description: 月频率生成 采查报告 
 * @author: djl  
 * @date:2019年12月25日 上午10:23:09
 */
@Component
public class MonthReportTask extends QuartzJobBean{

	@Autowired
	private IReportService reportService;
	
	private static final Logger log = LoggerFactory.getLogger(MonthReportTask.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("月频率生成 采查报告");
		int monthofYear = 0;
		try {
			monthofYear = getMonthofYear();
		} catch (ParseException e) {
			log.info("月频率生成 采查报告 出错");
			e.printStackTrace();
		}
		
		//判断该月有多少天
		int dayOfMonth = LocalDate.now().minusDays(7)
        .withMonth(monthofYear)
        .lengthOfMonth();
		
		
		//获取年份
		int year = LocalDateTime.now().minusDays(7).getYear();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime startTime = LocalDateTime.now().minusDays(dayOfMonth);
		LocalDateTime endTime = LocalDateTime.now().minusDays(1);
		
		
		Report report = new Report();
		report.setAreaId("f6d6ecb8b3a94c4d8c8d854e98a94019");//TODO 目前写死燕罗街道
		report.setFrequency("3");//频率月
		report.setType("1");//自动生成方式
		report.setCycle(monthofYear);//日期是当年的第几个月
		report.setYears(year);//年份
		report.setStartTime(dateTimeFormatter.format(startTime));//开始时间
		report.setEndTime(dateTimeFormatter.format(endTime));//结束时间
		reportService.save(report);
		
	}

	/**
	 * 判断日期属于今年的第几个月
	 * @return
	 * @throws ParseException 
	 */
	private int getMonthofYear() throws ParseException {
		
		LocalDateTime now = LocalDateTime.now();//当前时间
		LocalDateTime minusDays = now.minusDays(7);//前一周
		
		int month = minusDays.getMonthValue();
		
		return month;
	}
	
}
