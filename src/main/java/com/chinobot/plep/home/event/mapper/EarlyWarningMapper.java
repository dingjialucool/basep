package com.chinobot.plep.home.event.mapper;

import com.chinobot.plep.home.event.entity.EarlyWarning;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 风险预警表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-06-26
 */
public interface EarlyWarningMapper extends IBaseMapper<EarlyWarning> {

	IPage<Map> getEvent(Page page, @Param("p")Map<String, Object> param);

	List<Map> getEvent2(@Param("p")Map<String, Object> param);

	Map getDispath(@Param("p") Map<String, Object> param);
	
	List<Map> getEarlyInfoBar(@Param("p") Map<String, Object> param);
	
	List<Map> getDeptName();

}
