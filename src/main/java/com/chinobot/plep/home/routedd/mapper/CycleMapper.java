package com.chinobot.plep.home.routedd.mapper;

import com.chinobot.plep.home.routedd.entity.Cycle;
import com.chinobot.plep.home.routedd.entity.dto.CycleDto;
import com.chinobot.plep.home.routedd.entity.dto.CycleLeDto;
import com.chinobot.plep.home.routedd.entity.vo.CyclesVo;

import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 周期计划表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-10-23
 */
public interface CycleMapper extends IBaseMapper<Cycle> {

	/**
	 * 周期计划列表分页
	 * @param page
	 * @param dto
	 * @return
	 */
	IPage<CyclesVo> getCycles(Page page,CycleDto dto);

	/**
	 * 周期计划修改回显
	 * @param uuid
	 * @return
	 */
	CyclesVo getCycle(String uuid);

	/**
	 * 定时器-->周期计划集合
	 * @return
	 */
	List<CycleLeDto> getCyclePlans();

}
