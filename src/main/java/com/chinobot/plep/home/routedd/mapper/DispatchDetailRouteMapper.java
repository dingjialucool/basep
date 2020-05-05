package com.chinobot.plep.home.routedd.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.entity.vo.UrgentUavVo;

/**
 * <p>
 * 调度明细-路线表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-07-25
 */
public interface DispatchDetailRouteMapper extends IBaseMapper<DispatchDetailRoute> {
	List<Map> getRouteFlightTotal();
	
	List<UrgentUavVo> getAllUavForUrgent(@Param("deptId") String deptId);
}
