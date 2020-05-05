package com.chinobot.plep.home.plan.mapper;

import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.Range;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-06-18
 */
public interface FlyPathMapper extends IBaseMapper<FlyPath> {

	Range getRangeByFlyPath(@Param("p") Map<String, Object> param);
}
