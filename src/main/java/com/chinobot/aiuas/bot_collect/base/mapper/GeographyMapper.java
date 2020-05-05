package com.chinobot.aiuas.bot_collect.base.mapper;

import com.chinobot.aiuas.bot_collect.base.entity.Geography;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 地理信息表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-09
 */
public interface GeographyMapper extends IBaseMapper<Geography> {

	Long getMaxSort(String busiType);

}
