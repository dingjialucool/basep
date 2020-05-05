package com.chinobot.plep.home.cycle.mapper;

import com.chinobot.plep.home.cycle.entity.CycleDetail;
import com.chinobot.plep.home.cycle.entity.dto.CycleSearhDto;
import com.chinobot.plep.home.cycle.entity.dto.RouteSearchDto;
import com.chinobot.plep.home.cycle.entity.vo.CycleWithDetailVo;
import com.chinobot.plep.home.cycle.entity.vo.RouteDetailVo;
import com.chinobot.plep.home.cycle.entity.vo.RouteVo;
import com.chinobot.plep.home.routedd.entity.Cycle;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-10-25
 */
public interface CycleDetailMapper extends IBaseMapper<CycleDetail> {

	IPage<Cycle> searchCyclePage(Page page, @Param("dto") CycleSearhDto dto);
	
	IPage<RouteVo> searchRoute(Page page, @Param("dto") RouteSearchDto dto);
	
	List<RouteDetailVo> getRouteDetaill(@Param("dspId") String dspId);

	CycleWithDetailVo getCycleVo(@Param("uuid") String uuid);
	
	List<String> getDetailDspIds(@Param("cycleId") String cycleId);

}
