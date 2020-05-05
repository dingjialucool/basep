package com.chinobot.plep.home.routedd.mapper;

import java.util.List;
import java.util.Map;

import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-08-05
 */
public interface DispatchDetailPathMapper extends IBaseMapper<DispatchDetailPath> {
	List<Map> getPathFlightTotal();
}
