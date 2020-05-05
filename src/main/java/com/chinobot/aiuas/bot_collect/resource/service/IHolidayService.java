package com.chinobot.aiuas.bot_collect.resource.service;

import java.util.List;

import com.chinobot.aiuas.bot_collect.resource.entity.Holiday;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.HolidayVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 节日时点表 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
public interface IHolidayService extends IBaseService<Holiday> {

	List<HolidayVo> listHolidayByMonth(String month);
	
	String saveHoliday(HolidayVo vo);
}
