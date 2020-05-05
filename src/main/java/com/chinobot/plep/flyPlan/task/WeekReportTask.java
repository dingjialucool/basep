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
 * @ClassName: WeekReportTask   
 * @Description: 周频率生成 采查报告 
 * @author: djl  
 * @date:2019年12月24日 下午5:49:09
 */
@Component
public class WeekReportTask extends QuartzJobBean{

	@Autowired
	private IReportService reportService;
	
	private static final Logger log = LoggerFactory.getLogger(WeekReportTask.class);
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		log.info("周频率生成 采查报告");
		int weekofMonth = 0;
		try {
			weekofMonth = getWeekofMonth();
		} catch (ParseException e) {
			log.info("周频率生成 采查报告 出错");
			e.printStackTrace();
		}
		
		//获取年份
		int year = LocalDateTime.now().minusDays(7).getYear();
			
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDateTime startTime = LocalDateTime.now().minusDays(7);
		LocalDateTime endTime = LocalDateTime.now().minusDays(1);
		
		Report report = new Report();
		report.setAreaId("f6d6ecb8b3a94c4d8c8d854e98a94019");//TODO 目前写死燕罗街道
		report.setFrequency("2");//频率周
		report.setType("1");//自动生成方式
		report.setCycle(weekofMonth);//日期是当年的第几周
		report.setYears(year);//年份
		report.setStartTime(dateTimeFormatter.format(startTime));//开始时间
		report.setEndTime(dateTimeFormatter.format(endTime));//结束时间
		reportService.save(report);
		
	}

	/**
	 * 判断日期属于今年的第几个周期
	 * @return
	 * @throws ParseException 
	 */
	private int getWeekofMonth() throws ParseException {
		
		LocalDateTime now = LocalDateTime.now();//当前时间
		LocalDateTime minusDays = now.minusDays(7);//前一周
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dayOfBeforeWeek = dateTimeFormatter.format(minusDays);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = format.parse(dayOfBeforeWeek);
		Calendar calendar = Calendar.getInstance();
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.setTime(date);
		int week = calendar.get(Calendar.WEEK_OF_YEAR);
		
		return week;
	}
	
}
