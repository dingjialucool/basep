package com.chinobot.plep.home.plan.mapper;

import com.chinobot.plep.home.plan.entity.Range;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 巡查范围表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-06-16
 */
public interface RangeMapper extends IBaseMapper<Range> {

	IPage<VoAddressBase<Map>> getRangeAddr(Page page, @Param("p") Map<String, Object> param);
	
	VoAddressBase<Map> getRangeAddr(@Param("p") Map<String, String> param);
}
