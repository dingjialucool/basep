//package com.chinobot;
//
//import java.util.Date;
//
//import org.quartz.JobBuilder;
//import org.quartz.JobDetail;
//import org.quartz.Scheduler;
//import org.quartz.SchedulerFactory;
//import org.quartz.SimpleScheduleBuilder;
//import org.quartz.SimpleTrigger;
//import org.quartz.TriggerBuilder;
//import org.quartz.impl.StdSchedulerFactory;
//
//import com.chinobot.cityle.inspect.task.NormalTask;
//
////import com.chinobot.common.task.CronDateUtils;
//
//public class QuartzDemo {
//	public static void main(String[] args) throws Exception {
//        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
//        Scheduler scheduler = schedulerFactory.getScheduler();
//        // String cron = CronDateUtils.getCron(new Date());
//        // 启动scheduler
//        scheduler.start();
//        // 创建HelloworldJob的JobDetail实例，并设置name/group
//        JobDetail jobDetail = JobBuilder.newJob(NormalTask.class)
//                .withIdentity("myJob","myJobGroup1")
//                //JobDataMap可以给任务传递参数
//                .usingJobData("job_param","job_param1")
//                .build();
//        // 创建Trigger触发器设置使用cronSchedule方式调度
//		/*
//		 * Trigger trigger = TriggerBuilder.newTrigger()
//		 * .withIdentity("myTrigger","myTriggerGroup1")
//		 * .usingJobData("job_trigger_param","job_trigger_param1") .startNow()
//		 * //.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(
//		 * 5).repeatForever())
//		 * .withSchedule(CronScheduleBuilder.cronSchedule("58 34 16 02 04 ? 2019"))
//		 * .build();
//		 */
//        // 注册JobDetail实例到scheduler以及使用对应的Trigger触发时机
////        scheduler.scheduleJob(jobDetail,trigger);
//        
//        SimpleTrigger trigger2 = (SimpleTrigger) TriggerBuilder.newTrigger()
//        		.withIdentity("trigger3", "group1")
//        	    .startAt(new Date())  // if a start time is not given (if this line were omitted), "now" is implied
//        	    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//        	        .withIntervalInSeconds(10)
//        	        .withRepeatCount(10)) // note that 10 repeats will give a total of 11 firings
//        	    .forJob(jobDetail) // identify job with handle to its JobDetail itself                   
//        	    .build();
//        scheduler.scheduleJob(jobDetail,trigger2);
//    }
//}
