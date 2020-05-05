package com.chinobot.aiuas.bot_collect.resource.service.impl;

import com.chinobot.aiuas.bot_collect.resource.entity.Holiday;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.HolidayVo;
import com.chinobot.aiuas.bot_collect.resource.mapper.HolidayMapper;
import com.chinobot.aiuas.bot_collect.resource.service.IHolidayService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 节日时点表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
@Service
public class HolidayServiceImpl extends BaseService<HolidayMapper, Holiday> implements IHolidayService {

	@Autowired
	private HolidayMapper holidayMapper;
	
	@Override
	public List<HolidayVo> listHolidayByMonth(String month) {
		return holidayMapper.listHolidayByMonth(month);
	}

	@Override
	public String saveHoliday(HolidayVo vo) {
		Holiday holiday = new Holiday();
		BeanUtils.copyProperties(vo, holiday);
		this.saveOrUpdate(holiday);
		return holiday.getUuid();
	}

}
