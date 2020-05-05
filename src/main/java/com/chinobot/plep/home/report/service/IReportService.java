package com.chinobot.plep.home.report.service;

import com.chinobot.plep.home.report.entity.Report;
import com.chinobot.plep.home.report.entity.dto.FixedPointOfTimeDTO;
import com.chinobot.plep.home.report.entity.vo.FlyMessageVo;
import com.chinobot.plep.home.report.entity.vo.FlyPointsVo;
import com.chinobot.plep.home.report.entity.vo.ReportVo;
import com.chinobot.plep.home.report.entity.vo.WarnImgVo;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author djl
 * @since 2019-12-24
 */
public interface IReportService extends IBaseService<Report> {

	/**
	 * 采查报告分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ReportVo> getAllReports(Page page,Map<String,Object> param);

	/**
	 * 获取存量数量、同比、环比
	 * @Param: [uuid]
	 * @Return: java.util.Map<java.lang.String,java.lang.Integer>
	 * @Author: shizt
	 * @Date: 2019/12/25 11:56
	 */
	Map<String, Object> getChart(String uuid);

	/**
	 * 采查报告-航线规划
	 * @param mapRouteParam
	 * @return
	 */
	List<Map> getRouteLineOfCheckPoint(Map mapRouteParam);

	/**
	 * 时间范围内飞行航线信息
	 * @param mapRouteParam
	 * @return
	 */
	FlyMessageVo getFlyMessage(Map mapRouteParam);

	/**
	 * 采查报告-预警信息
	 */
	List<WarnImgVo> getWarnMeaage(Map mapRouteParam);

	/**
	 * 采查报告-预警信息点击更多
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<FlyPointsVo> getPointMore(Page page, Map param);

	/**
	 * 采查报告-存量动工明细
	 * @param page
	 * @param param
	 * @return
	 */
	Object getDetailOfPoint(Page page, Map param,boolean type);

	/**
	 * 通过自定义方式生成的报告获取定点同比环比量
	 * @param mapRouteParam
	 */
	Map getChartByCustomizeWay(Map mapRouteParam);
}
