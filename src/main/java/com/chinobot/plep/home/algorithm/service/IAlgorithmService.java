package com.chinobot.plep.home.algorithm.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.algorithm.entity.Algorithm;
import com.chinobot.plep.home.point.entity.FixedPoint;

/**
 * <p>
 * 算法表 服务类
 * </p>
 *
 * @author djl
 * @since 2019-08-25
 */
public interface IAlgorithmService extends IBaseService<Algorithm> {

	IPage<Map> getAlgorithmList(Page page, Map<String, Object> param);

	void saveAlgorithm(Map<String, Object> param);

	void commitAlgorithm(Map<String, Object> param);
	
	//调用楼顶加建的算法
	void execAlgorithm(String flyModule, String taskId, Map<String, File> imgMap);

	//调用存量变化的算法
	void execAlgorithm(String flyModule, Map<String, File> imgMap, Map<String, String> pointMap, String taskId);
	
	//根据飞行任务找到区域巡查对应的算法
	List<Algorithm> getPathAlgorithmByTask(Map<String, Object> param);

	//根据定点id找到定点追踪对应的算法
	List<Algorithm> getRouterAlgorithmByPoint(Map<String, Object> param);

	//根据飞行任务找到定点追踪对应的算法
	List<Algorithm> getRouterAlgorithmByTask(Map<String, Object> param);
	
	List<FixedPoint> getPointsByFlyId(String routeId);
}
