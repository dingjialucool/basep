package com.chinobot.plep.home.routedd.service;

import com.chinobot.plep.home.routedd.entity.Cycle;
import com.chinobot.plep.home.routedd.entity.dto.CycleDto;
import com.chinobot.plep.home.routedd.entity.dto.CycleLeDto;
import com.chinobot.plep.home.routedd.entity.vo.CyclesVo;
import com.chinobot.plep.home.routedd.entity.vo.PageAndCyclesVo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 周期计划表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-10-23
 */
public interface ICycleService extends IBaseService<Cycle> {

	PageAndCyclesVo getCycles(Page page, CycleDto dto);

	CyclesVo getCycle(String uuid);

	boolean delCycle(String uuid);

	List<CycleLeDto> getCyclePlans();
	
}
