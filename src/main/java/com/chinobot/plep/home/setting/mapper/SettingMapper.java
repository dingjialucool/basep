package com.chinobot.plep.home.setting.mapper;

import com.chinobot.plep.home.setting.entity.Setting;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 系统配置表 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-08-21
 */
public interface SettingMapper extends IBaseMapper<Setting> {

	IPage<Map> getBaseDatas(Page page, @Param("p") Map<String, Object> param);

	IPage<Map> getRoles(Page page, @Param("p") Map<String, Object> param);

	IPage<Map> getMenus(Page page, @Param("p") Map<String, Object> param);

	IPage<Map> getHelps(Page page, @Param("p") Map<String, Object> param);

	List<Map> getTreeMenu(@Param("p") Map<String, Object> param);

	List<Map> getOnlyRole(@Param("p") Map<String, Object> param);

	List<Map> getRoleMenu(@Param("p") Map paMap);

	List<Map> getRoleMenus(@Param("p") Map paMap);

	Map getFileInfo(String val);

}
