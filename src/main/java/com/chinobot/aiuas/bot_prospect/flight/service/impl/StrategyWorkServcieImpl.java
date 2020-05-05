package com.chinobot.aiuas.bot_prospect.flight.service.impl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.aiuas.bot_collect.resource.entity.Holiday;
import com.chinobot.aiuas.bot_collect.resource.entity.Weather;
import com.chinobot.aiuas.bot_collect.resource.mapper.HolidayMapper;
import com.chinobot.aiuas.bot_collect.resource.service.IWeatherService;
import com.chinobot.aiuas.bot_collect.strategy.entity.Strategy;
import com.chinobot.aiuas.bot_collect.strategy.mapper.StrategyMapper;
import com.chinobot.aiuas.bot_prospect.flight.constant.StrategyWorkConstant;
import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightWorkMapper;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightService;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.aiuas.bot_prospect.flight.service.IStrategyWorkServcie;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.HttpUtils;
import com.xxl.job.core.log.XxlJobLogger;


/**
 * <p>
 * 策略任务 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-02-24
 */
@Service
public class StrategyWorkServcieImpl implements IStrategyWorkServcie {

	@Autowired
	private IFlightService flightService;
	@Autowired
	private IWeatherService weatherService;
	@Autowired
	private HolidayMapper holidayMapper;
	@Autowired
	private StrategyMapper strategyMapper;
	@Autowired
	private IFlightWorkService flightWorkService;
	@Autowired
	private FlightWorkMapper flightWorkMapper;
	
	
	/**
	 * 根据策略定时生成任务
	 */
	@Override
	public void workSchedulingByStrategy() {
		XxlJobLogger.log("开始执行策略生成航班定时任务！！！");
		//查出所有航班上传完毕状态为正常的非专项策略
		List<Strategy> strategyList = strategyMapper.listStrategyOnline();
		//天气策略，先调天气预报接口。获取到了预报结果集合（单条记录为单天的）存入表中，并将之前的置为 无效
		queryWeather();
		List<Weather> weatherList = weatherService.list(CommonUtils.getEqQueryWrapper("is_deleted","adcode", 0, StrategyWorkConstant.SS_WEATHER_CITY_CODE));
		//遍历策略集合
		for(Strategy strategy : strategyList) {
			try {
				if(StrategyWorkConstant.CIRCLE_STRATEGY_TYPE.equals(strategy.getStrategyType())) {//周期
					circleWork(strategy);
				}
				if(StrategyWorkConstant.WEATHER_STRATEGY_TYPE.equals(strategy.getStrategyType())) {//气候
					weatherWork(strategy, weatherList);
				}
				if(StrategyWorkConstant.SEASON_STRATEGY_TYPE.equals(strategy.getStrategyType())) {//时点
					seasonWork(strategy);
				}
			} catch (Exception e) {
				XxlJobLogger.log("策略名：{},id:{},在生成航班时出错：{}", strategy.getStrategyName(), strategy.getUuid(), ExceptionUtils.getStackTrace(e));
			}
		}
		
		XxlJobLogger.log("策略生成航班定时任务执行结束！！！");
	}
	
	/**
	 * 气候策略生成任务
	 * @param strategy
	 */
	private void weatherWork(Strategy strategy, List<Weather> weatherList) {
		Integer workCount = strategy.getWorkCount();
		if(workCount == null || workCount==0) {
			workCount = 1;
		}
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		//从表中查出预报结果，遍历预报结果，若天气与策略天气匹配(高等级同时触发低等级)，
		List<LocalDate> matchList = weatherMatch(weatherList, strategy.getStrategyValue());
		//找到预报日期的生效区间，若日期在当前日期之前则剔除，得到 日期集合
		String strategyPlan = strategy.getStrategyPlan();
		if(StringUtils.isNotBlank(strategyPlan)) {
			String[] plans = strategyPlan.split(",");
			for(LocalDate matchDate : matchList) {
				for(String plan : plans) {
					LocalDate plusDay = matchDate.plusDays(Integer.parseInt(plan));
					if(!plusDay.isBefore(LocalDate.now()) && !LocalDate.now().plusDays(StrategyWorkConstant.ADVANCE_DAY).isBefore(plusDay)) {
						dateList.add(plusDay);
					}
					
				}
			}
		}
		//遍历日期集合调用generatorWork,不用去重，任务生成之前会做重复性校验
		for(LocalDate localDate : dateList) {
			generatorWork(strategy.getUuid(), localDate, workCount);
		}
	}
	
	
	
	/**
	 * 时点策略生成任务
	 * @param strategy
	 */
	private void seasonWork(Strategy strategy) {
		Integer workCount = strategy.getWorkCount();
		if(workCount == null || workCount==0) {
			workCount = 1;
		}
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		//若是时点策略，先sql查询找到符合条件的日期
		List<Holiday> holidays = holidayMapper.listHoliday(strategy.getStrategyValue());
		//找到生效区间，若日期在当前日期之前则剔除，若日期-当前日期>提前天数则剔除，得到 日期集合
		String strategyPlan = strategy.getStrategyPlan();
		if(holidays.size()>0 && StringUtils.isNotBlank(strategyPlan)) {
			String[] plans = strategyPlan.split(",");
			for(Holiday holiday : holidays){
				LocalDate holidayDate = holiday.getHolidayDate();
				for(String plan : plans) {
					LocalDate plusDay = holidayDate.plusDays(Integer.parseInt(plan));
					if(!plusDay.isBefore(LocalDate.now()) && !LocalDate.now().plusDays(StrategyWorkConstant.ADVANCE_DAY).isBefore(plusDay)) {
						dateList.add(plusDay);
					}
				}
			}
			
		}
		//遍历日期集合调用generatorWork
		for(LocalDate localDate : dateList) {
			generatorWork(strategy.getUuid(), localDate, workCount);
		}
	}
	
	/**
	 * 周期策略生成任务
	 */
	private void circleWork(Strategy strategy) {
		Integer workCount = strategy.getWorkCount();
		if(workCount == null || workCount==0) {
			workCount = 1;
		}
		//若是周期策略
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		//判断周期类型
		//若是每天，则以当前日期开始，生成提前天数这么多次
		if(StrategyWorkConstant.DAY_CIRCLE_TYPE.equals(strategy.getStrategyValue())) {
			LocalDate dateNow = LocalDate.now();
			for(int i=0; i<=StrategyWorkConstant.ADVANCE_DAY; i++) {
				dateList.add(dateNow.plusDays(i));
			}
		}
		//若是每周，从今天开始循环至提前天数，根据生效时间匹配。得到日期集合
		if(StrategyWorkConstant.WEEK_CIRCLE_TYPE.equals(strategy.getStrategyValue())) {
			String strategyPlan = strategy.getStrategyPlan();
			if(StringUtils.isNotBlank(strategyPlan)) {
				String[] plans = strategyPlan.split(",");
				LocalDate nowDate = LocalDate.now();
				for(int i=0; i<=StrategyWorkConstant.ADVANCE_DAY; i++) {
					LocalDate plusDay = nowDate.plusDays(i);
					String weekDay = getWeekDay(plusDay);
					for(String plan : plans) {
						if(plan.equals(weekDay)) {
							//匹配上了
							dateList.add(plusDay);
							break;
						}
					}
				}
			}
			
		}
		//若是每月，从今天开始循环至提前天数，根据生效时间匹配。得到日期集合
		if(StrategyWorkConstant.MONTH_CIRCLE_TYPE.equals(strategy.getStrategyValue())) {
			String strategyPlan = strategy.getStrategyPlan();
			String[] plans = strategyPlan.split(",");
			LocalDate nowDate = LocalDate.now();
			for(int i=0; i<=StrategyWorkConstant.ADVANCE_DAY; i++) {
				LocalDate plusDay = nowDate.plusDays(i);
				int dayOfMonth = plusDay.getDayOfMonth();
				for(String plan : plans) {
					int parseInt = Integer.parseInt(plan);
					if(dayOfMonth == parseInt) {
						//匹配上了
						dateList.add(plusDay);
						break;
					}
					//月底最后一天特殊处理
					if(parseInt == 31 && isLastDay(plusDay)) {
						dateList.add(plusDay);
						break;
					}
				}
			}
		}
		//遍历日期集合调用generatorWork
		for(LocalDate localDate : dateList) {
			generatorWork(strategy.getUuid(), localDate, workCount);
		}
	}
	/**
	 * 气候匹配
	 * @param weatherList
	 * @param type
	 * @param value
	 * @return
	 */
	private List<LocalDate> weatherMatch(List<Weather> weatherList , String value) {
		List<LocalDate> dateList = new ArrayList<LocalDate>();
		String type = null;
		String weaterName = null;//气候名称
		int windPower = 999;//风力
		int temp = 999;//温度
		if(StrategyWorkConstant.BY.equals(value)) {
			weaterName = StrategyWorkConstant.BY_VAL;
			type = StrategyWorkConstant.RAIN;
		}
		if(StrategyWorkConstant.DBY.equals(value)) {
			weaterName = StrategyWorkConstant.DBY_VAL;
			type = StrategyWorkConstant.RAIN;
		}
		if(StrategyWorkConstant.TDBY.equals(value)) {
			weaterName = StrategyWorkConstant.TDBY_VAL;
			type = StrategyWorkConstant.RAIN;
		}
		if(StrategyWorkConstant.DF.equals(value)) {
			windPower = StrategyWorkConstant.DF_VAL;
			type = StrategyWorkConstant.WIND;
		}
		if(StrategyWorkConstant.KF.equals(value)) {
			windPower = StrategyWorkConstant.KF_VAL;
			type = StrategyWorkConstant.WIND;
		}
		if(StrategyWorkConstant.TF.equals(value)) {
			windPower = StrategyWorkConstant.TF_VAL;
			type = StrategyWorkConstant.WIND;
		}
		if(StrategyWorkConstant.HSYJGW.equals(value)) {
			temp = StrategyWorkConstant.HSYJGW_VAL;
			type = StrategyWorkConstant.TEMP;
		}
		if(StrategyWorkConstant.CSYJGW.equals(value)) {
			temp = StrategyWorkConstant.CSYJGW_VAL;
			type = StrategyWorkConstant.TEMP;
		}
		if(StrategyWorkConstant.REDYJGW.equals(value)) {
			temp = StrategyWorkConstant.REDYJGW_VAL;
			type = StrategyWorkConstant.TEMP;
		}
		for(Weather weather : weatherList) {
			if(StrategyWorkConstant.RAIN.equals(type)) {//雨，模糊匹配
				if(weather.getDayWeather().contains(weaterName) || weather.getNightWeather().contains(weaterName)) {
					dateList.add(weather.getCastDate());
				}
			}
			if(StrategyWorkConstant.WIND.equals(type)) {//风
				if((StringUtils.isNotBlank(weather.getDayPower()) && Integer.parseInt(weather.getDayPower().replace("<", "").replace(">", "").replace("=", "")) >= windPower)
						|| (StringUtils.isNotBlank(weather.getNightPower()) && Integer.parseInt(weather.getNightPower().replace("<", "").replace(">", "").replace("=", "")) >= windPower)) {
					dateList.add(weather.getCastDate());
				}
				
			}
			if(StrategyWorkConstant.TEMP.equals(type)) {//温度
				if((StringUtils.isNotBlank(weather.getDayTemp()) && Integer.parseInt(weather.getDayTemp().replace("<", "").replace(">", "").replace("=", "")) >= temp)
						|| (StringUtils.isNotBlank(weather.getNightTemp()) && Integer.parseInt(weather.getNightTemp().replace("<", "").replace(">", "").replace("=", "")) >= temp)) {
					dateList.add(weather.getCastDate());
				}
			}
		}
		return dateList;
	}
	
	/**
	 * 更新数据库的天气
	 */
	@Transactional
	@Override
	public void queryWeather() {
		try {
			JSONObject amapWeather = HttpUtils.amapWeather("all", StrategyWorkConstant.SS_WEATHER_CITY_CODE);
			if(amapWeather != null) {
				UpdateWrapper<Weather> updateWrapper = new UpdateWrapper<Weather>();
				updateWrapper.set("is_deleted", 1);
				updateWrapper.eq("adcode", StrategyWorkConstant.SS_WEATHER_CITY_CODE);
				updateWrapper.eq("is_deleted", 0);
				//把之前天气置为无效
				weatherService.update(updateWrapper);
				String province = amapWeather.getString("province");
				String city = amapWeather.getString("city");
				String adcode = amapWeather.getString("adcode");
				String reportTimeString = amapWeather.getString("reporttime");
				DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				LocalDateTime reportTime = LocalDateTime.parse(reportTimeString, dateTimeFormatter);
				JSONArray casts = amapWeather.getJSONArray("casts");
				for(int i=0; i<casts.size(); i++) {
					JSONObject cast = casts.getJSONObject(i);
					Weather weather = new Weather();
					weather.setAdcode(adcode);
					weather.setCity(city);
					weather.setProvince(province);
					weather.setReportTime(reportTime);
					weather.setCastDate(LocalDate.parse(cast.getString("date"), dateFormatter));
					weather.setDayPower(cast.getString("daypower"));
					weather.setDayTemp(cast.getString("daytemp"));
					weather.setDayWeather(cast.getString("dayweather"));
					weather.setDayWind(cast.getString("daywind"));
					weather.setNightPower(cast.getString("nightpower"));
					weather.setNightTemp(cast.getString("nighttemp"));
					weather.setNightWeather(cast.getString("nightweather"));
					weather.setNightWind(cast.getString("nightwind"));
					weatherService.save(weather);
				}
			}
		} catch (Exception e) {
			XxlJobLogger.log("查询天气时出错：{}", e);
		}
		
	}
	

	/**
	 * 获取日期周几
	 * @param date
	 * @return
	 */
	private String getWeekDay(LocalDate date) {
		String[][] strArray = {{"MONDAY", "1"}, {"TUESDAY", "2"}, {"WEDNESDAY", "3"}, {"THURSDAY", "4"}, {"FRIDAY", "5"}, {"SATURDAY", "6"}, {"SUNDAY", "7"}};
    	String k = String.valueOf(date.getDayOfWeek());
    	//获取行数
    	for (int i = 0; i < strArray.length; i++) {
    	    if (k.equals(strArray[i][0])) {
    	        k = strArray[i][1];
    	       break;
    	     }
    	}
		return k;
	}
	/**
	 * 判断日期是否为月底最后一天
	 * @return
	 */
	private boolean isLastDay(LocalDate localDate) {
	    ZoneId zone = ZoneId.systemDefault();
	    Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
	    Date date = Date.from(instant);
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int now = cal.get(Calendar.DAY_OF_MONTH);
        if(lastDay == now) {
        	return true;
        }
	    return false;
	}
	@Transactional
	@Override
	public boolean generatorWork(String strategyId, LocalDate workDate, int count) {
//		Map<String, Object> param = new HashMap<String, Object>(2);
//		param.put("strategyId", strategyId);
//		param.put("flyDate", workDate);
//		Long countBystrategyAndDate = flightWorkMapper.countBystrategyAndDate(param);
//		if(countBystrategyAndDate > 0L) {
//			return false;//代表该策略该天已生成过任务了
//		}
		QueryWrapper<Flight> fightWrapper = CommonUtils.getEqQueryWrapper("is_deleted","strategy_id", 0, strategyId);
		List<Flight> list = flightService.list(fightWrapper);
		if(list.size() > 0) {
			Integer maxBatch = flightWorkMapper.getMaxBatch(strategyId);
			for(Flight flight : list) {
				Integer realCount = flightWorkMapper.selectCount(CommonUtils.getEqQueryWrapper("is_deleted","flight_uuid","flight_date", 0, flight.getUuid(), workDate));
				if(realCount >= count) {
					continue;
				}
				FlightWork flightWork = new FlightWork();
				flightWork.setFlightUuid(flight.getUuid());
				flightWork.setFlightDate(workDate);
				flightWork.setWorkStatus("1");
				flightWork.setBatch(maxBatch+1);
				//默认带上上次的无人机和驾驶员
				QueryWrapper<FlightWork> workWrapper = CommonUtils.getEqQueryWrapper("flight_uuid","is_deleted", flight.getUuid(), 0);
				workWrapper.isNotNull("uav_uuid").isNotNull("person_uuid");
				workWrapper.orderByDesc("flight_date");
				List<FlightWork> workList = flightWorkService.list(workWrapper);
				if(workList.size() > 0) {
					flightWork.setUavUuid(workList.get(0).getUavUuid());
					flightWork.setPersonUuid(workList.get(0).getPersonUuid());
				}
				for(int i=0; i< count-realCount; i++) {
					flightWork.setUuid(null);
					flightWorkService.save(flightWork);
					XxlJobLogger.log("生成了一条任务，策略主键: {}，任务主键: {}", strategyId, flightWork.getUuid());
				}
			}
		}
		return true;
	}

}
