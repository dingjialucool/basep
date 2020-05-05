package com.chinobot.plep.home.algorithm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.algorithm.entity.Algorithm;
import com.chinobot.plep.home.point.entity.FixedPoint;

/**
 * <p>
 * 算法表 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-08-25
 */
public interface AlgorithmMapper extends IBaseMapper<Algorithm> {

	IPage<Map> getAlgorithmList(Page page, @Param("param") Map<String, Object> param);
	
	Map getSceneByAlgorithm(@Param("param") Map<String, Object> param);
	
	void stopAlgorithm(@Param("param") Map<String, Object> param);
	
	List<Algorithm> getPathAlgorithmByTask(@Param("param") Map<String, Object> param);

	List<Algorithm> getRouterAlgorithmByPoint(@Param("param") Map<String, Object> param);
	
	List<Algorithm> getRouterAlgorithmByTask(@Param("param") Map<String, Object> param);

	List<FixedPoint> getPointsByFlyId(String routeId);
	
}
