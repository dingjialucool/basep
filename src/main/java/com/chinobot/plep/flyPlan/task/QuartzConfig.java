package com.chinobot.plep.flyPlan.task;


import java.util.Date;

import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @ClassName: QuartzConfig   
 * @Description: 任务触发器与执行规则
 * @author: djl  
 * @date:2019年10月26日 上午10:50:54
 */
//@Configuration
public class QuartzConfig {

	@Bean
    public JobDetail jobQuartz() {
        return JobBuilder.newJob(CycleTask.class).withIdentity("cycleTask").storeDurably().build();
    }

	/**
	 *  触发器执行规则：一天执行一次
	 * @return
	 */
    @Bean
    public Trigger jobQuartzTrigger() {
    	
    	//周期计划的执行时间,当天晚上10点
    	Date startTime = DateBuilder.todayAt(1, 0, 0);
//    	Date startTime = DateBuilder.nextGivenSecondDate(null, 50);
    	
        return TriggerBuilder.newTrigger().forJob(jobQuartz())
                .withIdentity("cycleTask")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
            	        .withIntervalInHours(24) //时间间隔24小时
//                		.withIntervalInSeconds(10) //时间间隔10s
//                        .withRepeatCount(10)) //重复次数10次
            	        .repeatForever()) //重复次数永远
                .build();
    }
    
    
    
    @Bean
    public JobDetail jobQuartz2() {
        return JobBuilder.newJob(UavTask.class).withIdentity("uavTask").storeDurably().build();
    }

	/**
	 *  触发器执行规则：一天执行一次
	 * @return
	 */
    @Bean
    public Trigger jobQuartzTrigger2() {
    	
    	//周期计划的执行时间,当天晚上10点
//    	Date startTime = DateBuilder.todayAt(22, 0, 0);
    	Date startTime = DateBuilder.nextGivenSecondDate(null, 50);
    	
        return TriggerBuilder.newTrigger().forJob(jobQuartz2())
                .withIdentity("uavTask")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                		.withIntervalInSeconds(5) //时间间隔5s
            	        .repeatForever()) //重复次数永远
                .build();
    }
    
    @Bean
    public JobDetail jobOnlinePersonQuartz() {
        return JobBuilder.newJob(PersonOnlineTask.class).withIdentity("personOnlineTask").storeDurably().build();
    }

	/**
	 *  人员在线离线
	 * @return
	 */
    @Bean
    public Trigger jobOnlinePersonQuartzTrigger() {
    	
    	//周期计划的执行时间,项目启动后50秒
    	Date startTime = DateBuilder.nextGivenSecondDate(null, 50);
    	
        return TriggerBuilder.newTrigger().forJob(jobOnlinePersonQuartz())
                .withIdentity("personOnlineTask")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                		.withIntervalInMinutes(1) //时间间隔1分钟
            	        .repeatForever()) //重复次数永远
                .build();
    }
    
		
	  @Bean
	  public JobDetail jobWeekOfReportQuartz() {
	      return JobBuilder.newJob(WeekReportTask.class).withIdentity("weekReportTask").storeDurably().build();
	  }
	
		/**
		 *  周频率生成 采查报告 ，每周一凌晨0点5分执行
		 * @return
		 */
	  @Bean
	  public Trigger jobWeekOfReportQuartzTrigger() {
	  	
	      return TriggerBuilder.newTrigger().forJob(jobWeekOfReportQuartz())
	              .withIdentity("weekReportTask")
	              .withPriority(4)
	              .withSchedule(CronScheduleBuilder.cronSchedule("0 5 0 ? * MON"))
	              .startNow()
	              .build();
	  }	
    
	  
	  @Bean
	  public JobDetail jobMonthOfReportQuartz() {
	      return JobBuilder.newJob(MonthReportTask.class).withIdentity("monthReportTask").storeDurably().build();
	  }
	
		/**
		 *  月频率生成 采查报告 ，每月1号凌晨0点5分执行
		 * @return
		 */
	  @Bean
	  public Trigger jobMonthOfReportQuartzTrigger() {
	  	
	      return TriggerBuilder.newTrigger().forJob(jobMonthOfReportQuartz())
	              .withIdentity("monthReportTask")
	              .withPriority(3)
	              .withSchedule(CronScheduleBuilder.cronSchedule("0 5 0 1 * ?"))
	              .startNow()
	              .build();
	  }	
	  
	  @Bean
	  public JobDetail jobQuarterOfReportQuartz() {
	      return JobBuilder.newJob(QuarterReportTask.class).withIdentity("quarterReportTask").storeDurably().build();
	  }
	
		/**
		 *  季度频率生成 采查报告 ，每年1月1号，4月1号，7月1号，10月1号凌晨0点5分执行
		 * @return
		 */
	  @Bean
	  public Trigger jobQuarterOfReportQuartzTrigger() {
		  
	      return TriggerBuilder.newTrigger().forJob(jobQuarterOfReportQuartz())
	              .withIdentity("quarterReportTask")
	              .withPriority(2)
	              .withSchedule(CronScheduleBuilder.cronSchedule("0 5 0 1 1,4,7,10 ? *"))
	              .startNow()
	              .build();
	  }	

	  @Bean
	  public JobDetail jobYearOfReportQuartz() {
	      return JobBuilder.newJob(YearReportTask.class).withIdentity("yearReportTask").storeDurably().build();
	  }
	
		/**
		 *  年频率生成 采查报告 ，每年1月1号凌晨0点5分执行
		 * @return
		 */
	  @Bean
	  public Trigger jobYearOfReportQuartzTrigger() {
	  	
	      return TriggerBuilder.newTrigger().forJob(jobYearOfReportQuartz())
	              .withIdentity("yearReportTask")
	              .withPriority(1)
	              .withSchedule(CronScheduleBuilder.cronSchedule("0 5 0 1 1 ? *"))
	              .startNow()
	              .build();
	  }
}