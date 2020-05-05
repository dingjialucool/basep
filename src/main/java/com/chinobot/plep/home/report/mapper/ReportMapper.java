package com.chinobot.plep.home.report.mapper;

import com.chinobot.plep.home.report.entity.Report;
import com.chinobot.plep.home.report.entity.dto.FixedPointOfTimeDTO;
import com.chinobot.plep.home.report.entity.vo.FlyPointsVo;
import com.chinobot.plep.home.report.entity.vo.ReportVo;
import com.chinobot.plep.home.report.entity.vo.WarnImgVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-12-24
 */
public interface ReportMapper extends IBaseMapper<Report> {

	/**
	 * 采查报告分页
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<ReportVo> getAllReports(Page page,@Param("p")Map<String,Object> param);

	/**
	 * 获取存量数量、同比、环比
	 * @Param: [uuid]
	 * @Return: java.util.Map<java.lang.String,java.lang.Integer>
	 * @Author: shizt
	 * @Date: 2019/12/25 11:56
	 */
	Map<String, Object> getChart(@Param("uuid") String uuid);

	/**
	 * 采查报告-航线规划
	 * @param mapRouteParam
	 * @return
	 */
	List<Map> getRouteLineOfCheckPoint(@Param("p") Map mapRouteParam);

	/**
	 * 时间范围内航线数量 和里程
	 * @param mapRouteParam
	 * @return
	 */
	Map getRouteNum(@Param("p") Map mapRouteParam);

	/**
	 * 飞行次数
	 * @param mapRouteParam
	 * @return
	 */
	Long getFlyNum(@Param("p") Map mapRouteParam);

	/**
	 * 预警数量
	 * @param mapRouteParam
	 * @return
	 */
	Long getWarnNum(@Param("p") Map mapRouteParam);
	
	/**
	 * 预警信息
	 * @param mapRouteParam
	 * @return
	 */
	List<WarnImgVo> getWarnImg(@Param("p") Map mapRouteParam);
	
	/**
	 * 预警信息-点击更多
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<FlyPointsVo>  getWarnImgOfPage(Page page,@Param("p")Map<String,Object> param);

	/**
	 * 采查报告-存量动工明细
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<FixedPointOfTimeDTO> getFixedPointOfTime(Page page,@Param("p") Map param);
	
	/**
	 * 采查报告-存量动工明细-导出pdf-不分页
	 * @param page
	 * @param param
	 * @return
	 */
	List<FixedPointOfTimeDTO> getFixedPointOfTime(@Param("p") Map param);
	
	/**
	 * 采查报告-存量动工明细
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<Map> getFlyImg(Page page,@Param("p") Map param);

	/**
	 * 通过自定义方式生成的报告获取定点同比环比量
	 * @param mapRouteParam
	 * @return
	 */
	Long getChartByCustomizeWay(@Param("p") Map mapRouteParam);
}
