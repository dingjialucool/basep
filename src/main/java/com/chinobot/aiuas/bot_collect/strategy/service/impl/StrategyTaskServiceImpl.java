package com.chinobot.aiuas.bot_collect.strategy.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.resource.entity.Holiday;
import com.chinobot.aiuas.bot_collect.resource.mapper.HolidayMapper;
import com.chinobot.aiuas.bot_collect.resource.service.IHolidayService;
import com.chinobot.aiuas.bot_collect.strategy.entity.StrategyTask;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.EventAndClueStatusDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategyReportOfEventTempDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategySerachDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportAllVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportFlightVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportFlyVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportOfEventVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportOfWarnDetailVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportTaskVo;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.StrategyReportVo;
import com.chinobot.aiuas.bot_collect.strategy.mapper.StrategyTaskMapper;
import com.chinobot.aiuas.bot_collect.strategy.service.IStrategyTaskService;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultItemVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;
import com.chinobot.common.utils.TimeUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.monitor.entity.PersonOnline;
import com.chinobot.plep.home.monitor.entity.UavOnline;
import com.chinobot.plep.home.monitor.service.IPersonOnlineService;
import com.chinobot.plep.home.monitor.service.IPersonTrajectoryService;
import com.chinobot.plep.home.monitor.service.IUavOnlineService;
import com.xxl.job.core.log.XxlJobLogger;

import io.swagger.annotations.ApiModelProperty;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 采查策略任务关联表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class StrategyTaskServiceImpl extends BaseService<StrategyTaskMapper, StrategyTask> implements IStrategyTaskService {

	@Autowired
	private StrategyTaskMapper strategyTaskMapper;
	@Autowired
	private IPersonTrajectoryService personTrajectoryService;
	@Autowired
	private IPersonOnlineService personOnlineService;
	@Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
    private IUavOnlineService uavOnlineService; 
	@Autowired
	private HolidayMapper holidayMapper;
	@Autowired
	private IHolidayService holidayService;
	
	@Override
	public IPage<StrategyReportVo> getStrategyList(StrategySerachDto dto) {
		
		Page page  = new Page();
		page.setCurrent(dto.getCurrent());
		page.setSize(dto.getSize());
		return strategyTaskMapper.getStrategyList(page,dto);
	}

	@Override
	public StrategyReportAllVo getStrategyReportDetail(String uuid) {
		
		StrategyReportAllVo vo = new StrategyReportAllVo();
		
		//策略信息
		StrategyReportTaskVo reportOfStrategyTask = strategyTaskMapper.getReportOfStrategyTask(uuid);
		vo.setTaskVo(reportOfStrategyTask);

		//航线规划情况
		List<StrategyReportFlightVo> reportOfFlight = strategyTaskMapper.getReportOfFlight(uuid);
		vo.setFlightVo(reportOfFlight);
		
		//飞行巡查情况
		List<StrategyReportFlyVo> reportOfFlyList = strategyTaskMapper.getReportOfFly(uuid);
		vo.setFlyVo(reportOfFlyList);
		
		List<StrategyReportOfEventTempDTO> reportOfEventAndClueList = strategyTaskMapper.getReportOfEventAndClue(uuid);
		//对事件统计进行封装
		List<StrategyReportOfEventVo> eventListVo = packToEventTotal(reportOfEventAndClueList);
		vo.setEventVo(eventListVo);
		
		//预警信息明细
		List<StrategyReportOfWarnDetailVo> reportOfWarnDetailList = strategyTaskMapper.getReportOfWarnDetail(uuid);
		vo.setWarnDetailVo(reportOfWarnDetailList);
		
		//数量监测
		List<CollectResultVo> resultList = strategyTaskMapper.getResultList(uuid);
		for (CollectResultVo collectResultVo : resultList) {
			List<CollectResultItemVo> removeDuplicateResult = removeDuplicateResult(collectResultVo.getList());
			collectResultVo.setList(removeDuplicateResult);
		}
		
		vo.setResultListVo(resultList);
		
		return vo;
	}

	/**
	 * 过滤掉重复数据
	 * @param users
	 * @return
	 */
	public  List<CollectResultItemVo> removeDuplicateResult(List<CollectResultItemVo> items){
        Set<CollectResultItemVo> set = new TreeSet<CollectResultItemVo>((item1, item2) -> item1.getRsKey().compareTo(item2.getRsKey()));
        set.addAll(items);
        return new ArrayList<>(set);
    }
	
	private List<StrategyReportOfEventVo> packToEventTotal(List<StrategyReportOfEventTempDTO> reportOfEventAndClueList) {
		List<StrategyReportOfEventVo> eventListVo = new ArrayList<StrategyReportOfEventVo>();
		for (StrategyReportOfEventTempDTO dto : reportOfEventAndClueList) {
			StrategyReportOfEventVo eventVo = new StrategyReportOfEventVo();
			eventVo.setEventName(dto.getEventName());
			eventVo.setEventType(dto.getEventType());
			eventVo.setObjectName(dto.getObjectName());
			List<EventAndClueStatusDTO> eventAndClueDtoList = dto.getEventAndClueDto();
			eventVo.setEventCount(eventAndClueDtoList.size());
			//确认数
			Integer qrCount = 0;
			//核查数
			Integer hcCount = 0;
			//治理数
			Integer zlCount = 0;
			//办结数
			Integer bjCount = 0;
			for (EventAndClueStatusDTO vo : eventAndClueDtoList) {
				String businessStatus = vo.getBusinessStatus();
				Integer status = Integer.parseInt(businessStatus);
				if (status >= 90) {
					bjCount ++;
				}
				if (status >= 40) {
					hcCount ++;
				}
				if (status >= 30) {
					zlCount ++;
				}
				if (status >= 20) {
					qrCount ++;
				}
			}
			eventVo.setQrCount(qrCount);
			eventVo.setZlCount(zlCount);
			eventVo.setHcCount(hcCount);
			eventVo.setBjCount(bjCount);
			
			eventListVo.add(eventVo);
		}
		
		return eventListVo;
	}

	@Override
	public void personOnlineStatus() {
		
		XxlJobLogger.log("人员状态在线监听始"+new Date());
		List<PersonOnline> personOnlinesList = personTrajectoryService.getList();
		if(personOnlinesList.size()>0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (PersonOnline personOnline : personOnlinesList) {
				LocalDateTime now = LocalDateTime.now();//当前时间
				LocalDateTime operateTime = personOnline.getOperateTime();//操作时间
				long minutes = operateTime.until(now, ChronoUnit.MINUTES);//当前时间与操作时间间隔多少秒
				if(now.isAfter(operateTime) && minutes>1) { //当前时间在操作时间之后，且间隔时间大于5分钟
					Map map = new HashMap();
					map.put("uuid", personOnline.getUuid());
					map.put("personId", personOnline.getPersonId());
					map.put("onlineStatus", "0");
					map.put("operateTime", sdf.format(new Date()));
					map.put("latitude",personOnline.getLatitude());
					map.put("longitude",personOnline.getLongitude());
					//推送消息
					kafkaTemplate.sendDefault("p_person_online", JSON.toJSONString(map));
					personOnline.setOnlineStatus("0");
					personOnline.setOperateTime(LocalDateTime.now());
					//更新人员状态
					personOnlineService.saveOrUpdate(personOnline);
				}
			}
		}
	}

	@Override
	public void uavOnlineStatus() {
		XxlJobLogger.log("无人机扫描开始"+new Date());
		List<UavOnline> uavOnlines = uavOnlineService.getUavOnlines();
		if(uavOnlines.size()>0) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (UavOnline uavOnline : uavOnlines) {
				LocalDateTime now = LocalDateTime.now();//当前时间
				LocalDateTime operateTime = uavOnline.getOperateTime();//操作时间
				long seconds = operateTime.until(now, ChronoUnit.SECONDS);//当前时间与操作时间间隔多少秒
				if(now.isAfter(operateTime) && seconds>30) { //当前时间在操作时间之后，且间隔时间大于30秒
					Map map = new HashMap();
					map.put("ecode", uavOnline.getUavCode());
					map.put("runStatus", "0");
					map.put("operateTime", sdf.format(new Date()));
					//推送消息
					kafkaTemplate.sendDefault("robot_status", JSON.toJSONString(map));
				}
			}
		}
	}

	@Override
	public void createWeekends() {
		
		//生成本年及下一年的所有周末
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		List<String> weekendOfYearList = TimeUtil.getWeekendOfYear(year);
		weekendOfYearList.addAll(TimeUtil.getWeekendOfYear(year + 1));
		//获取当年及以上数据库中的所有周末日期
		List<String> weekendYear = holidayMapper.getWeekendYear(year);
		for (String weekend : weekendOfYearList) {
			String string = weekendYear.toString();
			if (!string.contains(weekend)) {
				Holiday holiday = new Holiday();
				holiday.setHolidayDate(LocalDate.parse(weekend, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				holiday.setWorkVacation("0");
				holidayService.save(holiday);
			}
		}
	}

}
