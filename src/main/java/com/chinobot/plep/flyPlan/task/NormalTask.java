package com.chinobot.plep.flyPlan.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.plep.flyPlan.entity.FlyPlan;
import com.chinobot.plep.flyPlan.service.IFlyPlanService;
import com.chinobot.plep.home.plan.entity.FlyTask;
import com.chinobot.plep.home.plan.service.IFlyTaskService;

/**
 * 
 * @ClassName: NormalTask   
 * @Description: 日常巡查任务  
 * @author: dingjialu  
 * @date:2019年4月8日 上午10:30:19
 */
@Component
public class NormalTask extends QuartzJobBean{

	@Autowired
	private IFlyTaskService flyTaskService;
	@Autowired
	private IFlyPlanService flyPlanService;

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// updateOutofDate();
		QueryWrapper<FlyPlan> queryWrapper = new QueryWrapper<FlyPlan>();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.eq("use_status", "1");
		List<FlyPlan> list = flyPlanService.list(queryWrapper);
		System.out.println("-------------list-------------"+list );
		addFlyTask(list);//生成任务
	}
	
	
	/**
	 *  生成日常任务
	 * @param list
	 */
	private void addFlyTask(List<FlyPlan> list) {
		
		List<Map> areaTotalList = new ArrayList(); 
		List<Map> buildTotalList = new ArrayList();
		List<Map> areaSpeciaList = new ArrayList();
		List<Map> buildSpeciaList = new ArrayList();
		//遍历所有的计划
		for (FlyPlan flyPlan : list) {
			String strategy = flyPlan.getStrategy();
			String planType = flyPlan.getPlanType();
			LocalDate start = flyPlan.getTimeStart();//开始时间
			LocalDate end = flyPlan.getTimeEnd();//结束时间
			LocalDate last = flyPlan.getLast();//上一次任务生成的时间
			//若计划为飞行范围计划，且计划类型为周期，则关联查询范围下所有的区域，一个区域一个任务
			if ((strategy.equals("1") || strategy.equals("2")) && planType.equals("1")) {
				List<Map> areaList = flyPlanService.getArea(flyPlan.getUuid());
				for (Map map : areaList) {
					addToMap(map, flyPlan);
					areaTotalList.add(map);
				}
			}
			
			//若计划为飞行建筑计划，且计划类型为周期，则关联查询建筑，一个建筑一个任务
			if ((strategy.equals("3") || strategy.equals("4")) && planType.equals("1")) {
				List<Map> buildList = flyPlanService.getBuild(flyPlan.getUuid());
				for (Map map : buildList) {
					addToMap(map, flyPlan);
					buildTotalList.add(map);
				}
			}
			
			//若计划为飞行范围计划，且计划类型为专项，则关联查询范围下所有的区域，一个区域一个任务
			if ((strategy.equals("1") || strategy.equals("2")) && planType.equals("2") && isPeriodSpecial(start, end, last)) {
				List<Map> areaList = flyPlanService.getArea(flyPlan.getUuid());
				for (Map map : areaList) {
					addToMap(map, flyPlan);
					areaSpeciaList.add(map);
				}
			}
			
			//若计划为飞行建筑计划，且计划类型为专项，则关联查询建筑，一个建筑一个任务
			if ((strategy.equals("3") || strategy.equals("4")) && planType.equals("2") && isPeriodSpecial(start, end, last)) {
				List<Map> buildList = flyPlanService.getBuild(flyPlan.getUuid());
				for (Map map : buildList) {
					addToMap(map, flyPlan);
					buildSpeciaList.add(map);
				}
			}
			
		}
		//生成周期飞行区域任务
		for (Map areaMap : areaTotalList) {
			AreaTask(areaMap);
		}
		//生成周期飞行建筑任务
		for (Map buildMap : buildTotalList) {
			buildingTask(buildMap);
		}
		
		//生成专项飞行区域任务
		for (Map areaMap : areaSpeciaList) {
			AreaSpecialTask(areaMap);
		}
		//生成专项飞行建筑任务
		for (Map buildMap : buildSpeciaList) {
			buildingSpecialTask(buildMap);
		}
	}
	
	/**
	 * 	 判断专项任务是否在策略周期内
	 * @param start
	 * @param end
	 * @param last
	 */
	private boolean isPeriodSpecial(LocalDate start,LocalDate end,LocalDate last) {
		
		if(last == null) {
			return true;
		}
		if(last.isBefore(start)) {
			return false;
		}
		return false;
	}
	
	//生成专项飞行建筑任务
	private void buildingSpecialTask(Map buildMap) {
		if (buildMap!=null) {
			String planId = (String) buildMap.get("planId");
			String areaId = (String) buildMap.get("buildId");
			FlyTask flyTask = new FlyTask();
			flyTask.setPlanId(planId);
			flyTask.setPointId(areaId);
			LocalDate start = (LocalDate) buildMap.get("start");//开始时间
			flyTask.setCreateTime(start.atStartOfDay());
			flyTask.setOperateTime(start.atStartOfDay());
			boolean bo = flyTaskService.save(flyTask);
			if(bo) {//更新last字段
				updataLast(start.atStartOfDay(),planId);
			}
		}
		
	}

	//更新专项计划的last字段
	private void updataLast(LocalDateTime start,String planId) {
		UpdateWrapper<FlyPlan> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("uuid", planId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		updateWrapper.set("last", start.minusDays(20));
		flyPlanService.update(updateWrapper);
		
	}


	//生成专项飞行区域任务
	private void AreaSpecialTask(Map areaMap) {
		if(areaMap!=null) {
			String planId = (String) areaMap.get("planId");
			String areaId = (String) areaMap.get("areaId");
			FlyTask flyTask = new FlyTask();
			flyTask.setPlanId(planId);
			flyTask.setPointId(areaId);
			LocalDate start = (LocalDate) areaMap.get("start");//开始时间
			flyTask.setCreateTime(start.atStartOfDay());
			flyTask.setOperateTime(start.atStartOfDay());
			boolean bo = flyTaskService.save(flyTask);
			if(bo) {//更新last字段
				updataLast(start.atStartOfDay(),planId);
			}
		}
		
	}


	private void buildingTask(Map buildMap) {
		
		String planId = (String) buildMap.get("planId");
		LocalDate start = (LocalDate) buildMap.get("start");//开始时间
		LocalDate end = (LocalDate) buildMap.get("end");//结束时间
		LocalDate last = (LocalDate) buildMap.get("last");//上一次任务生成的时间
		String gapType = (String) buildMap.get("gapType");//间隔类型
		int gapCycle =(int) Double.parseDouble(buildMap.get("gapCycle").toString());//间隔周期
		//判断是否在策略周期内
		if(isPeriod(start,end,last)) {
			//判断间隔类型是否为小时
			if(gapType.equals("1")) {
				int number = 8/gapCycle;     //在这个时间间隔内生成多个个任务
				for(int i=0;i<number;i++) {
					FlyTask flyTask = buildBuildingTask(buildMap);//新增区域周期任务
					flyTaskService.save(flyTask);
				}
				updateFlyTask(planId,1,"2",last);
			}else {
				FlyTask flyTask = buildBuildingTask(buildMap);//新增区域周期任务
				flyTaskService.save(flyTask);
				//更新last字段
				updateFlyTask(planId,gapCycle,gapType,last);
			}		
		}
	}

	//生成建筑飞行任务
	private FlyTask buildBuildingTask(Map buildMap) {
		String planId = (String) buildMap.get("planId");
		String buildId = (String) buildMap.get("buildId");
		FlyTask flyTask = new FlyTask();
		flyTask.setPlanId(planId);
		flyTask.setBuildingId(buildId);
		flyTask.setCreateTime(LocalDateTime.now());
		flyTask.setOperateTime(LocalDateTime.now());
		return flyTask;
	}


	//将飞行计划的一些参数拼接在 map中
	private Map addToMap(Map map,FlyPlan flyPlan) {
		
		map.put("start", flyPlan.getTimeStart());
		map.put("end", flyPlan.getTimeEnd());
		map.put("last", flyPlan.getLast());
		map.put("gapType", flyPlan.getCycleUnit());
		if(flyPlan.getCycle()!=null) {
			map.put("gapCycle", Float.parseFloat(flyPlan.getCycle().toString()));
		}
		return map;
	}
	
	//生成区域飞行任务
	private FlyTask buildAreaTask(Map map) {
		String planId = (String) map.get("planId");
		String areaId = (String) map.get("areaId");
		FlyTask flyTask = new FlyTask();
		flyTask.setPlanId(planId);
		flyTask.setPointId(areaId);
		flyTask.setCreateTime(LocalDateTime.now());
		flyTask.setOperateTime(LocalDateTime.now());
		return flyTask;
	}
	
	/**
	 * 生成飞行区域任务(一个飞行区域对应一个任务)
	 */
	private void AreaTask(Map areaMap) {
		
		String planId = (String) areaMap.get("planId");
		LocalDate start = (LocalDate) areaMap.get("start");//开始时间
		LocalDate end = (LocalDate) areaMap.get("end");//结束时间
		LocalDate last = (LocalDate) areaMap.get("last");//上一次任务生成的时间
		String gapType = (String) areaMap.get("gapType");//间隔类型
		int gapCycle =(int) Double.parseDouble(areaMap.get("gapCycle").toString());//间隔周期
		//判断是否在策略周期内
		if(isPeriod(start,end,last)) {
			//判断间隔类型是否为小时
			if(gapType.equals("1")) {
				int number = 8/gapCycle;     //在这个时间间隔内生成多个个任务
				for(int i=0;i<number;i++) {
					FlyTask flyTask = buildAreaTask(areaMap);//新增区域周期任务
					flyTaskService.save(flyTask);
				}
				updateFlyTask(planId,1,"2",last);
			}else {
				FlyTask flyTask = buildAreaTask(areaMap);//新增区域周期任务
				flyTaskService.save(flyTask);
				//更新last字段
				updateFlyTask(planId,gapCycle,gapType,last);
			}		
		}
		
	}
	
	/**
	 * 	 判断是否在策略周期内
	 * @param start
	 * @param end
	 * @param last
	 */
	private boolean isPeriod(LocalDate start,LocalDate end,LocalDate last) {
		//如果为last表示还未生成过日常任务
		LocalDate date = null;
		if(last == null || "".equals(last)) {
			date = start;
		}else {
			date = last;
		}
		LocalDate nowDate  = LocalDate.now();
		
		
		//判断今天是否在策略周期内
		if(nowDate.isAfter(date) && nowDate.isBefore(end) || nowDate.equals(date) || (date.isBefore(end) && nowDate.equals(end))) {
			return true;
		}
		return false;
	}

	
	/**
	 *  更新last字段时间
	 */
	private void updateFlyTask(String planId,Integer gapCycle,String gapType,LocalDate last) {
		if(last == null || "".equals(last)) {
			System.out.println(last+"last----------------------------------");
			last = LocalDate.now();
		}
		Integer longToFly = longToFly(gapType, gapCycle);//多久飞一次
		if(gapType == null || gapType == "") {
			gapType = "2";//默认为天
		}
		if(gapType.equals("3")) {//周
			last = last.plusWeeks(1*gapCycle);
		}
		if(gapType.equals("2")) {//天
			last = last.plusDays(longToFly);
		}				
		UpdateWrapper<FlyPlan> updateWrapper = new UpdateWrapper<>();
		updateWrapper.eq("uuid", planId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		updateWrapper.set("last", last);
		flyPlanService.update(updateWrapper);		
	}
	
	
	/**
	 * 根据间隔类型，间隔周期计算出多久飞行一次
	 * @param gapType
	 * @param gapCycle
	 * @return
	 */
	private Integer longToFly(String gapType,Integer gapCycle) {
		Integer sum = null;
		if(gapType == null || gapType == "") {
			gapType = "2";
		}
		if(gapType.equals("3")) {
			sum = 7*gapCycle;
		}
		if(gapType.equals("2")) {
			sum = 1*gapCycle;
		}else {
			sum = 1;
		}
		return sum;
	}
	
}
