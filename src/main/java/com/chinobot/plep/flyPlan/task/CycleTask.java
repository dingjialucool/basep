package com.chinobot.plep.flyPlan.task;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.plep.home.cycle.service.ICycleDetailService;
import com.chinobot.plep.home.report.entity.HolidayDate;
import com.chinobot.plep.home.report.mapper.HolidayDateMapper;
import com.chinobot.plep.home.report.service.IHolidayDateService;
import com.chinobot.plep.home.routedd.entity.Cycle;
import com.chinobot.plep.home.routedd.entity.dto.CycleLeDto;
import com.chinobot.plep.home.routedd.service.ICycleService;

/**
 * 
 * @ClassName: CycleTask   
 * @Description: 生成周期任务 
 * @author: djl  
 * @date:2019年10月23日 下午6:36:34
 */
@Component
public class CycleTask extends QuartzJobBean{

	@Autowired
	private ICycleService cycleService;
	@Autowired
	private ICycleDetailService cycleDetailService;
	@Autowired
	private IHolidayDateService holidayDateService;
	
	private static final Logger log = LoggerFactory.getLogger(CycleTask.class);
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		
		
		addFlyTask();//生成任务
		
	}
	
	
	/**
	 *  生成周期任务
	 * @param list
	 */
	public void addFlyTask() {
		log.info("周期计划开始执行"+new Date());
		//周期计划集合
		List<CycleLeDto> list = cycleService.getCyclePlans();
		
		if(list.size() == 0) {
			return ;
		}
		Map<String, List<CycleLeDto>> groups =
	  			  list.stream().collect(Collectors.groupingBy(CycleLeDto::getUuid));
		
		Iterator<String> iter = groups.keySet().iterator();
		while (iter.hasNext()) {//按组遍历所有的计划
			String cycleId = iter.next();
			List<CycleLeDto> CycleLeDtoLists = groups.get(cycleId);
			CycleLeDto dto = CycleLeDtoLists.get(0);
		
			LocalDate start = dto.getTimeStart();//开始时间(last为null)
			LocalDate end = dto.getTimeEnd();//结束时间
			LocalDate last = dto.getLast();//上一次任务生成的时间
			Integer cycleNum = dto.getCycle();//周期次数
			String cycleUnit = dto.getCycleUnit();//周期类型
			Integer earlyDay = dto.getEarlyDay();//提前天数
			LocalDate nextDate = null;
			//提前天数
			if(CommonUtils.isObjEmpty(last)) {//表示周期计划还未生成任务，则判断timeStart
				nextDate = start;//下一次任务生成的时间
			}else {
				nextDate = getNextDate(cycleNum, cycleUnit, last);//下一次任务生成的时间
			}
			
			buildTask(end, nextDate, earlyDay, CycleLeDtoLists, cycleNum, cycleUnit,cycleId);//生成任务
		}
	}
	
	/**
	 * 遍历组内，生成任务,修改last时间
	 */
	private void buildTask(LocalDate end,LocalDate nextDate,Integer earlyDay,
			List<CycleLeDto> CycleLeDtoLists,Integer cycleNum ,String cycleUnit,String cycleId) {
		
		//第一层判断:是否在周期计划时间范围内
		if(isPeriod(end, nextDate)) {
			boolean bo = isPeriod(LocalDate.now().plusDays(earlyDay), nextDate);
			//满足第一层条件后：第二层判断-->下一次生成任务时间是否在 （当前时间+提前天数内）
			while(isPeriod(LocalDate.now().plusDays(earlyDay), nextDate)) {
				//如果下一次生成任务的时间在今天之前，则今天=生成任务的时间(这种情况出现在计划关闭一段时间后，重新开启计划时)
				if(nextDate.isBefore(LocalDate.now())) {
					nextDate = LocalDate.now();
				}
				LocalDate start = null;
				//满足两层条件后：组内逻辑-->生成任务时间:组内最小的先生成，然后依次生成额任务的时间= 组内间隔时间+组内最小时间(无人机调度日期)
				for (CycleLeDto cycleLeDto : CycleLeDtoLists) {
					//生成任务(调用接口 传参 无人机调度id，日期，周期id)
					long days = 0;
					if(start != null) {
						days = days + start.until(cycleLeDto.getFlyTime(), ChronoUnit.DAYS);
					}
					
					//判断如果是节假日就不生成任务
					LocalDate temDate = nextDate.plusDays(days);
					String formatDay = dateTimeFormatter.format(temDate);
					HolidayDate holidayDate = holidayDateService.getOne(new LambdaQueryWrapper<HolidayDate>().eq(HolidayDate::getHolidayDate, formatDay));
					if(holidayDate == null) {
						cycleDetailService.buildTask(cycleLeDto.getUavDspId(), nextDate.plusDays(days),cycleId);
					}
					
					start = cycleLeDto.getFlyTime();
				}
				//下一次生成任务的时间
				nextDate = getNextDate(cycleNum, cycleUnit, nextDate);
			}
			//若第一次满足第二层判断条件，修改last时间
			if(bo) {
				//生成最后一次任务，因为nextDate是下一次生成任务的时间，所及要减回来
				LocalDate last = getBackDate(cycleNum, cycleUnit, nextDate);
				Cycle cycle = new Cycle();
				cycle.setUuid(cycleId);
				cycle.setLast(last);
				//修改last时间
				cycleService.updateById(cycle);
			}
				
		}
	}
	
	
	/**
	 * 	 判断是否在策略周期内
	 * @param start
	 * @param end
	 * @param last
	 */
	private boolean isPeriod(LocalDate end,LocalDate next) {
		
		if(next.isBefore(end) || next.equals(end)) {
			return true;
		}
		return false;
	}

	
	/**
	 *  获取下一次任务生成时间
	 */
	private LocalDate getNextDate(Integer cycle,String cycleUnit,LocalDate start) {
		
		LocalDate last = LocalDate.now();
		if(cycleUnit == null || cycleUnit == "") {
			cycleUnit = "1";//默认为天
		}
		if(cycleUnit.equals("3")) {//月
			last = start.plusMonths(1*cycle);
		}
		if(cycleUnit.equals("2")) {//周
			last = start.plusWeeks(1*cycle);
		}
		if(cycleUnit.equals("1")) {//天
			last = start.plusDays(1*cycle);
		}				
		
		return last;
	}
	
	//生成最后一次任务，因为nextDate是下一次生成任务的时间，所及要减回来
	private LocalDate getBackDate(Integer cycle,String cycleUnit,LocalDate start) {
		
		LocalDate last = LocalDate.now();
		if(cycleUnit == null || cycleUnit == "") {
			cycleUnit = "1";//默认为天
		}
		if(cycleUnit.equals("3")) {//月
			last = start.minusMonths(1*cycle);
		}
		if(cycleUnit.equals("2")) {//周
			last = start.minusWeeks(1*cycle);
		}
		if(cycleUnit.equals("1")) {//天
			last = start.minusDays(1*cycle);
		}				
		
		return last;
	}
	
	
}
