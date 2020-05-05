package com.chinobot.aiuas.bot_collect.resource.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinobot.aiuas.bot_collect.resource.entity.Holiday;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.HolidayVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 节日时点表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
public interface HolidayMapper extends IBaseMapper<Holiday> {
	
	List<Holiday> listHoliday(@Param("holiday") String holiday);

	List<HolidayVo> listHolidayByMonth(@Param("month") String month);

	/**
	 * 查出数据表中的已存在的当年及以后所有周末
	 * @param year
	 * @return
	 */
	List<String> getWeekendYear(int year);
}
