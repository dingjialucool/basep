package com.chinobot.cityle.warning.mapper;

import java.util.List;
import java.util.Map;

import com.chinobot.cityle.warning.entity.EarlyWarningRecord;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 预警记录表 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-05-16
 */
public interface EarlyWarningRecordMapper extends IBaseMapper<EarlyWarningRecord> {
	
	/**
	 * 最近一个月预警数
	 * @return
	 * @author shizt  
	 * @date 2019年5月16日
	 * @company chinobot
	 */
	List<Map> getMonthEarlyWarningCount();
}
