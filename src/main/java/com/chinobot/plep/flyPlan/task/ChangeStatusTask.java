package com.chinobot.plep.flyPlan.task;

import java.time.LocalDate;
import java.util.List;

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

/**
 *  
 * @ClassName: ChangeStatusTask   
 * @Description: 用来改变已失效的计划的状态  和 启用状态
 * @author: dingjialu  
 * @date:2019年6月26日 上午10:03:23
 */
@Component
public class ChangeStatusTask extends QuartzJobBean{

	@Autowired
	private IFlyPlanService flyPlanService;
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		//查询 当天时间不在计划周期内的所有计划
		QueryWrapper<FlyPlan> queryWrapper = new QueryWrapper<FlyPlan>();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.eq("use_status", "1");
		List<FlyPlan> list = flyPlanService.list(queryWrapper);
		for (FlyPlan flyPlan : list) {
			LocalDate start = flyPlan.getTimeStart();//开始时间
			LocalDate end = flyPlan.getTimeEnd();//结束时间
			LocalDate last = flyPlan.getLast();//上一次任务生成的时间
			if(!isPeriod(start, end, last)) {
				//若计划不在周期内，改变计划的启用状态和计划状态
				changeStatus(flyPlan.getUuid(),start,last);
			}
		}
	}

	/**
	 * 若计划不在周期内，改变计划的启用状态和计划状态
	 * @param uuid
	 */
	private void changeStatus(String uuid,LocalDate start,LocalDate last) {
		UpdateWrapper<FlyPlan> updateWrapper = new UpdateWrapper<FlyPlan>();
		updateWrapper.eq("uuid", uuid).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		if(last == null) {
			if(LocalDate.now().isBefore(start)) {
				updateWrapper.set("status", "9");//计划状态改成已停止
			}else {
				updateWrapper.set("status", "2");//计划状态改成已完成
			}
		}else {
			if(LocalDate.now().isBefore(start) || LocalDate.now().isBefore(last)) {
				updateWrapper.set("status", "9");//计划状态改成已停止
			}else {
				updateWrapper.set("status", "2");//计划状态改成已完成
			}
		}
		
		
		updateWrapper.set("use_status", "0");//计划关闭
		flyPlanService.update(updateWrapper);
	}


	/**
	 * 	 判断计划是否在策略周期内
	 * @param start
	 * @param end
	 * @param last
	 */
	private boolean isPeriod(LocalDate start,LocalDate end,LocalDate last) {
		LocalDate nowDate  = LocalDate.now();
		
		
		//判断今天是否在策略周期内
		if(nowDate.isAfter(start) && nowDate.isBefore(end) || nowDate.equals(start) || nowDate.equals(end)) {
			return true;
		}
		return false;
	}
}
