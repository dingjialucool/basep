package com.chinobot.plep.home.plan.mapper;

import com.chinobot.plep.home.plan.entity.FlyArea;


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
 * @author huangw
 * @since 2019-06-17
 */
public interface FlyAreaMapper extends IBaseMapper<FlyArea> {

	 IPage<Map> getFlyAreaPage(Page page,@Param("p") Map param);

	boolean updatePoint(@Param("p") Map param);

	boolean updateTimePoint(@Param("p") Map param);

}
