package com.chinobot.framework.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.entity.BaseMenu;

/**
 * <p>
 * 菜单 Mapper 接口
 * </p>
 *
 * @author zhoull
 * @since 2019-05-31
 */
public interface BaseMenuMapper extends IBaseMapper<BaseMenu> {

	List<BaseMenu> searchMenuByPersonId(@Param("personId")String personId);

	List<BaseMenu> searchMenuByPersonIdAndSystem(@Param("personId")String personId, @Param("systemId")String systemId);
}
