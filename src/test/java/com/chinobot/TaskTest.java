//package com.chinobot;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import com.chinobot.cityle.inspect.task.NormalTask;
//import com.chinobot.cityle.inspect.task.QuartzManager;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class TaskTest {
//	@Autowired
//    QuartzManager quartzManager;
//	@Test
//	public void test() {
//		String jobName = "动态任务调度";
//        String jobGroupName = "任务组名";
//        String triggerName = "触发器名";
//        String triggerGroupName = "触发器组名";
//        quartzManager.addJob(jobName,jobGroupName,triggerName,triggerGroupName, NormalTask.class,10);
//
//	}
//}
