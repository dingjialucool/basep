package com.chinobot.plep.home.plan.service;

import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.Range;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangw
 * @since 2019-06-18
 */
public interface IFlyPathService extends IBaseService<FlyPath> {

	Range getRangeByFlyPath(Map<String, Object> param);
}
