package com.chinobot.plep.home.cycle.service;

import com.chinobot.plep.home.cycle.entity.CycleDetail;
import com.chinobot.plep.home.cycle.entity.dto.ChangeStateDto;
import com.chinobot.plep.home.cycle.entity.dto.CycleSearhDto;
import com.chinobot.plep.home.cycle.entity.dto.DeleteCycleDto;
import com.chinobot.plep.home.cycle.entity.dto.RouteSearchDto;
import com.chinobot.plep.home.cycle.entity.vo.CycleWithDetailVo;
import com.chinobot.plep.home.cycle.entity.vo.RouteVo;
import com.chinobot.plep.home.routedd.entity.Cycle;

import java.time.LocalDate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangw
 * @since 2019-10-25
 */
public interface ICycleDetailService extends IBaseService<CycleDetail> {

	/**
	 * 周期查询
	 * @param page
	 * @param dto
	 * @return
	 */
	IPage<Cycle> searchCyclePage(Page page, CycleSearhDto dto);
	/**
	 * 路线查询
	 * @param page
	 * @param dto
	 * @return
	 */
	IPage<RouteVo> searchRoutePage(Page page, RouteSearchDto dto);
	/**
	 * 保存周期
	 * @param vo
	 */
	void saveCycle(CycleWithDetailVo vo);
	/**
	 * 获取单个周期
	 * @param uuid
	 * @return
	 */
	CycleWithDetailVo getCycleVo(String uuid);
	/**
	 * 周期开关
	 * @param dto
	 */
	void changeState(ChangeStateDto dto);
	/**
	 * 删除周期
	 * @param dto
	 */
	void deleteCycle(DeleteCycleDto dto);
	/**
	 * 生成任务
	 * @param uavDspId
	 * @param flyTime
	 */
	void buildTask(String uavDspId, LocalDate flyTime, String cycleId);

}
