package com.chinobot.plep.home.point.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.point.entity.FixedPoint;

import com.chinobot.plep.home.point.entity.vo.FlyPointVo;

import com.chinobot.plep.home.point.entity.vo.FixedPointVo;


/**
 * <p>
 * 定点表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
public interface FixedPointMapper extends IBaseMapper<FixedPoint> {

	List<FixedPointVo> getAllPoint(@Param("p") Map<String, String> param);
	
	IPage<Map> getAllPointBak(Page page, @Param("p") Map<String, String> param);

	IPage<Map> getPointPage(Page page, @Param("p") Map<String, Object> param);
	
	List<Map> getAllRouteLine(@Param("p") Map<String, String> param);
	
	List<Map> getRouteLine(@Param("uuid") String uuid);
	
	/**
	 * 获取航线下面定点的所有基准图信息
	 * @param param
	 * @return
	 */
	List<Map> getFlyLinePointImg(@Param("p") Map<String, Object> param);
	

	/**
	 * 查询周边范围的定点
	 * @param param
	 * @return
	 */
	List<Map> getRangePoint(@Param("p") Map<String, Object> param);

	/**
	 * 查询该街道下的定点数量
	 * @param param
	 * @return
	 */
	Map getFixPointCount(@Param("p") Map<String, Object> param);

	/**
	 * 查询该街道下的航线数量
	 * @param param
	 * @return
	 */
	Map getCountOfLine(@Param("p") Map<String, Object> param);

	/**
	 * 航线下的定点数量
	 * @param param
	 * @return
	 */
	Map getFixPointCounts(@Param("p") Map<String, Object> param);

	/**
	 * 获取单个定点信息
	 * @param pointMap
	 * @return
	 */
	List<Map> getPointInfo(@Param("p") Map<String,Object> pointMap);

	/**
	 *  获取单个定点下所有的飞行数据
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<FlyPointVo> getFlyImgList(Page page, @Param("p")Map<String, Object> param);

	/**
	 * 获取飞行图，压缩上传到文件服务器
	 * @param param
	 * @return
	 */
	List<Map> getFlyImgToZip(@Param("p")Map<String, Object> param);

	
	/**
	 *  获取单个定点下所有的飞行数据(不需要分页)
	 * @param page
	 * @param param
	 * @return
	 */
	List<Map> getFlyImgListNoPage(@Param("p") Map<String, Object> param);

	/**
	 *  获取所有定点（没有分页）
	 * @param param
	 * @return
	 */
	List<Map> getAllPointNoPage(@Param("p") Map<String, Object> param);
	
	/**
	 * 查询该街道下的航线数量(定点绑定)
	 * @param param
	 * @return
	 */
	Map getCountOfLineWithPoint(@Param("p") Map<String, Object> param);
	
	/**
	 * 获取某个定点下所有航点的基准图
	 * @param uuid
	 * @return
	 */
	List<Map> getBasicImgList(String uuid);
}
