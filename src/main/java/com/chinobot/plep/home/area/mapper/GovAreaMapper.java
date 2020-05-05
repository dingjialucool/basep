package com.chinobot.plep.home.area.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.area.entity.GovArea;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-09-10
 */
public interface GovAreaMapper extends IBaseMapper<GovArea> {

	List<Map> getListByParent(@Param("p") Map<String, Object> param);
}
