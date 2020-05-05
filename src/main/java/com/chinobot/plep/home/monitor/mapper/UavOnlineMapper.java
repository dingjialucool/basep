package com.chinobot.plep.home.monitor.mapper;

import com.chinobot.plep.home.monitor.entity.UavOnline;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author laihb
 * @since 2019-07-26
 */
public interface UavOnlineMapper extends IBaseMapper<UavOnline> {

	IPage<Map> uavList(Page page,@Param("p") Map<String, Object> param);

	List<UavOnline> getUavOnlines();

}
