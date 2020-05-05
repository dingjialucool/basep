package com.chinobot.plep.home.report.service.impl;

import com.chinobot.plep.home.report.entity.Report;
import com.chinobot.plep.home.report.entity.dto.FixedPointOfTimeDTO;
import com.chinobot.plep.home.report.entity.vo.FlyMessageVo;
import com.chinobot.plep.home.report.entity.vo.FlyPointsVo;
import com.chinobot.plep.home.report.entity.vo.ReportVo;
import com.chinobot.plep.home.report.entity.vo.WarnImgVo;
import com.chinobot.plep.home.report.mapper.ReportMapper;
import com.chinobot.plep.home.report.service.IReportService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-12-24
 */
@Service
public class ReportServiceImpl extends BaseService<ReportMapper, Report> implements IReportService {

	@Autowired
	private ReportMapper reportMapper;
	@Autowired
	private IDeptService deptService;
	
	@Override
	public IPage<ReportVo> getAllReports(Page page,Map<String,Object> param) {
		
		if(param.get("type")!=null && param.get("type")!="" ) {
        	param = toParam(param,"type");
        }
		if(param.get("frequency")!=null && param.get("frequency")!="" ) {
        	param = toParam(param,"frequency");
        }
		
		return reportMapper.getAllReports(page,param);
	}

	private Map toParam(Map param, String type) {
		
    	String str = (String) param.get(type);
    	if(str.indexOf(',')>0) {
    		String[] split = str.split(",");
    		param.put(type, split);
    	}else {
			String[] split = new String[1];
			split[0] = str;
			param.put(type, split);
		}
		return param;
	}
	
	@Override
	public Map<String, Object> getChart(String uuid) {
		return reportMapper.getChart(uuid);
	}

	@Override
	public List<Map> getRouteLineOfCheckPoint(Map mapRouteParam) {
		
		return reportMapper.getRouteLineOfCheckPoint(mapRouteParam);
	}

	@Override
	public FlyMessageVo getFlyMessage(Map mapRouteParam) {
		
		FlyMessageVo vo = new FlyMessageVo();
		
		Map routeMap = new HashMap();
		routeMap.put("deptId", mapRouteParam.get("deptId"));
		routeMap.put("endTime", mapRouteParam.get("endTime"));
		Map routeNumMap = reportMapper.getRouteNum(routeMap);
		//航线数量
		Map map = reportMapper.getRouteNum(mapRouteParam);
		
		vo.setRouteNum(routeNumMap.get("routeNum").toString());
		//里程
		double flightTotal = (double) map.get("flightTotal");
		if(CommonUtils.isObjEmpty(flightTotal)) {
			vo.setMilgage(0);
		}else {
			vo.setMilgage(flightTotal);
		}
		//规划飞行次数
		Long flyPlanNum = reportMapper.getFlyNum(mapRouteParam);
		vo.setFlyPlanNum(flyPlanNum);
		//实际飞行次数
		mapRouteParam.put("status", "3");
		Long flyNum = reportMapper.getFlyNum(mapRouteParam);
		vo.setFlyNum(flyNum);
		//飞行执行比例
		if(flyNum == 0 || flyPlanNum == 0) {
			vo.setFlyRatio("0");
		}else {
			DecimalFormat df=new DecimalFormat("0.00");//设置保留位数
		    String flyRatio = df.format((float)flyNum/flyPlanNum*100);
		    vo.setFlyRatio(flyRatio);
		}
		
	    //预警数量
	    Long warnNum = reportMapper.getWarnNum(mapRouteParam);
	    vo.setWarnNum(warnNum);
	    
		return vo;
	}

	@Override
	public List<WarnImgVo> getWarnMeaage(Map mapRouteParam) {
		
		List<WarnImgVo> warnImgList = reportMapper.getWarnImg(mapRouteParam);
		for (WarnImgVo warnImgVo : warnImgList) {
			int size = warnImgVo.getFlyPointsVo().size();
			warnImgVo.setNum(size);
			List<FlyPointsVo> flyPointsVoList = warnImgVo.getFlyPointsVo();
			int basicNum = 0;
			int eventNum = 0;
			for (FlyPointsVo vo : flyPointsVoList) {
				if("basic_img".equals(vo.getModule())) {
					basicNum = basicNum + 1;
				}
				if("event_img".equals(vo.getModule())) {
					eventNum = eventNum + 1;
				}
			}
			warnImgVo.setWarnNum(basicNum>=eventNum?basicNum:eventNum);
			//一开始加载页面只加载3个
//			if(size>3) {
//				List<FlyPointsVo> subList = warnImgVo.getFlyPointsVo().subList(0,3);
//				warnImgVo.setFlyPointsVo(subList);
//			}
		}
		return warnImgList;
	}

	@Override
	public IPage<FlyPointsVo> getPointMore(Page page, Map param) {
		
		return reportMapper.getWarnImgOfPage(page,param);
	}

	@Override
	public Object getDetailOfPoint(Page page, Map param,boolean type) {
		
		
		List<FixedPointOfTimeDTO> fixedPointList = null;
		IPage<FixedPointOfTimeDTO> fixedPointOfTimePage = null;
		if(!type) {
			 fixedPointList = reportMapper.getFixedPointOfTime(param);
		}else {
			fixedPointOfTimePage = reportMapper.getFixedPointOfTime(page,param);
			fixedPointList = fixedPointOfTimePage.getRecords();
		}
		
		Page fixedPointPage = new Page(1, 3);
		//获取每个定点下前三条飞行数据、飞行数据总数
		for (FixedPointOfTimeDTO fixedPointOfTimeDTO : fixedPointList) {
			param.put("pointId", fixedPointOfTimeDTO.getPointId());
			IPage<Map> flyImg = reportMapper.getFlyImg(fixedPointPage, param);
			List<Map> records = flyImg.getRecords();
			long total = flyImg.getTotal();
			fixedPointOfTimeDTO.setFlyImgList(records);
			fixedPointOfTimeDTO.setFlyImgNum(total);
		}
		if(type) {
			return fixedPointOfTimePage.setRecords(fixedPointList);
		}
		
		return fixedPointList;
	}

	@Override
	public Map getChartByCustomizeWay(Map mapRouteParam) {
		
		Map map = new HashMap();
		map.put("deptId", mapRouteParam.get("deptId"));
		
		//时间范围内定点数量
		Long chartOfRangeNum = reportMapper.getChartByCustomizeWay(mapRouteParam);
		
		String start = (String) mapRouteParam.get("startTime");
		String end = (String) mapRouteParam.get("endTime");
		
		//存量数量
		map.put("endTime", end);
		map.put("startTime", null);
		Long pointNum = reportMapper.getChartByCustomizeWay(map);
		
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate startTime = LocalDate.parse(start, fmt);
		LocalDate endTime = LocalDate.parse(end, fmt);
		//计算两个日期相差多少天
		long until = startTime.until(endTime, ChronoUnit.DAYS);
		LocalDate relativeStartTime = startTime.minusDays(until);
		LocalDate relativeEndTime = endTime.minusDays(until);
		LocalDate compareStartTime = startTime.minusYears(1);
		LocalDate compareEndTime = endTime.minusYears(1);
		//用于环比的时间
		map.put("startTime", relativeStartTime);
		map.put("endTime", relativeEndTime);
		Long chartOfRelativeNum = reportMapper.getChartByCustomizeWay(map);
		
		//用于同比的时间
		map.put("startTime", compareStartTime);
		map.put("endTime", compareEndTime);
		Long chartOfCompareNum = reportMapper.getChartByCustomizeWay(map);
		
		Map<String,Object> param = new HashMap<String, Object>();
		//存量数量
		param.put("num", pointNum);
		//存量同比
		if(chartOfCompareNum == 0 && chartOfRangeNum !=0) {
			param.put("yoy", 100);
		}else if(chartOfCompareNum != 0 ){
			param.put("yoy", (chartOfRangeNum-chartOfCompareNum)/chartOfCompareNum*100);
		}else {
			param.put("yoy", 0);
		}
		
		//存量环比
		if(chartOfRelativeNum == 0 && chartOfRangeNum !=0) {
			param.put("mom", 100);
		}else if(chartOfRelativeNum != 0 ){
			param.put("mom", (chartOfRangeNum-chartOfRelativeNum)/chartOfRelativeNum*100);
		}else {
			param.put("mom", 0);
		}
		
		String  deptId = (String) mapRouteParam.get("deptId");
		param.put("dname", deptService.getById(deptId).getDname());
		return param;
	}

}
