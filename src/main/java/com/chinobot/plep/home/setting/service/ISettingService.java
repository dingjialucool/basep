package com.chinobot.plep.home.setting.service;

import com.chinobot.plep.home.setting.entity.RoleMenuVo;
import com.chinobot.plep.home.setting.entity.Setting;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.entity.BaseMenu;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 系统配置表 服务类
 * </p>
 *
 * @author djl
 * @since 2019-08-21
 */
public interface ISettingService extends IBaseService<Setting> {

	IPage<Map> getBaseDatas(Page page,Map<String, Object> param);

	boolean updateRole(RoleMenuVo vo);

	IPage<Map> getRoles(Page page,Map<String, Object> param);

	IPage<Map> getMenus(Page page, Map<String, Object> param);

	IPage<Map> getHelps(Page page, Map<String, Object> param);

	List<Map> getTreeMenu(Map<String, Object> param);

	Map getOnlyRole(Map<String, Object> param);

	Map getFileInfo(String val);


}
