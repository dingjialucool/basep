package com.chinobot.plep.home.setting.service.impl;

import com.chinobot.plep.home.setting.entity.MenuRole;
import com.chinobot.plep.home.setting.entity.RoleMenuVo;
import com.chinobot.plep.home.setting.entity.Setting;
import com.chinobot.plep.home.setting.mapper.SettingMapper;
import com.chinobot.plep.home.setting.service.IMenuRoleService;
import com.chinobot.plep.home.setting.service.ISettingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Role;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.framework.web.entity.BaseMenu;
import com.chinobot.framework.web.service.IBaseMenuService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-08-21
 */
@Service
public class SettingServiceImpl extends BaseService<SettingMapper, Setting> implements ISettingService {

	@Autowired
	private SettingMapper settingMapper;
	@Autowired
	private IMenuRoleService menuRoleService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IBaseMenuService baseMenuService;
	@Override
	public IPage<Map> getBaseDatas(Page page, Map<String, Object> param) {
		
		return settingMapper.getBaseDatas(page,param);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean updateRole(RoleMenuVo vo) {
		
		boolean bo = false;
		Role role = vo.getRole();
		//更新角色
		bo = roleService.saveOrUpdate(role);
		//更新菜单角色
		//先删除已有的所有关联
		UpdateWrapper<MenuRole> updateWrapper = new UpdateWrapper<MenuRole>();
		updateWrapper.eq("role_id", role.getUuid()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		bo = menuRoleService.update(updateWrapper);
		//关联保存菜单角色
		List<MenuRole> roleMenu = vo.getRoleMenu();
		for (MenuRole menuRole : roleMenu) {//遍历所有菜单
//			QueryWrapper<BaseMenu> queryWrapper = new QueryWrapper<BaseMenu>();
//			queryWrapper.eq("parent_id", menuRole.getMenuId()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//			List<BaseMenu> list = baseMenuService.list(queryWrapper);
//			if(list.size()>0) {//若菜单有子菜单，则保存所有子菜单与角色的关联
//				for(BaseMenu baseMenu:list) {
//					MenuRole menuRole2 = new MenuRole();
//					menuRole2.setRoleId(role.getUuid());
//					menuRole2.setMenuId(baseMenu.getUuid());
//					menuRoleService.save(menuRole2);
//				}
//			}
//			
//			QueryWrapper<BaseMenu> queryWrapperPar = new QueryWrapper<BaseMenu>();
//			queryWrapperPar.eq("uuid", menuRole.getMenuId()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//			BaseMenu one = baseMenuService.getOne(queryWrapperPar);
//			if(one.getParentId()!=null && one.getParentId()!="") {//若菜单有父菜单
//				MenuRole menuRole3 = new MenuRole();
//				menuRole3.setRoleId(role.getUuid());
//				menuRole3.setMenuId(one.getParentId());
//				menuRoleService.save(menuRole3);
//			}
			
			menuRole.setRoleId(role.getUuid());
			menuRoleService.save(menuRole);//保存菜单与角色的关联
		}
		return bo;
	}

	@Override
	public IPage<Map> getRoles(Page page,Map<String, Object> param) {
			
		if(param.get("isSys")!=null && param.get("isSys")!="" ) {
        	String str = (String) param.get("isSys");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("isSys", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("isSys", split);
			}
        }
		return settingMapper.getRoles(page,param);
	}

	@Override
	public IPage<Map> getMenus(Page page, Map<String, Object> param) {
		
		if(param.get("menuType")!=null && param.get("menuType")!="" ) {
        	String str = (String) param.get("menuType");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("menuType", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("menuType", split);
			}
        }
		return settingMapper.getMenus(page,param);
	}

	@Override
	public IPage<Map> getHelps(Page page, Map<String, Object> param) {
		
		return settingMapper.getHelps(page,param);
	}

	@Override
	public List<Map> getTreeMenu(Map<String, Object> param) {
		List<Map> list = settingMapper.getTreeMenu(param);
		for (Map map : list) {
			String uuid = (String) map.get("uuid");
			QueryWrapper<BaseMenu> queryWrapper = new QueryWrapper<BaseMenu>();
			queryWrapper.eq("parent_id",uuid).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			queryWrapper.orderByAsc("order_by");
			List<BaseMenu> childList = baseMenuService.list(queryWrapper);
			if(childList!=null&&childList.size()>0) {
				//三级
				for(BaseMenu cbm : childList) {
					QueryWrapper<BaseMenu> queryWrapper2 = new QueryWrapper<BaseMenu>();
					queryWrapper2.eq("parent_id",cbm.getUuid()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
					queryWrapper2.orderByAsc("order_by");
					List<BaseMenu> gchildList = baseMenuService.list(queryWrapper2);
					if(gchildList!=null&&gchildList.size()>0) {
						cbm.setChildren(gchildList);
					}
				}
				map.put("childList", childList);
			}
		}
		return list;
	}

	@Override
	public Map getOnlyRole(Map<String, Object> param) {
		String uuidString = (String) param.get("uuid");
		QueryWrapper<Role> roleQueryWrapper  = new QueryWrapper<Role>();
		roleQueryWrapper.eq("uuid", uuidString);
		Role role = roleService.getOne(roleQueryWrapper);
		List<Map> list = settingMapper.getOnlyRole(param);
//		for (Map map : list) {
//			String uuid = (String) map.get("uuid");
//			Map paMap = new HashMap();
//			paMap.put("uuid", uuid);
//			paMap.put("roleId", role.getUuid());
//			List<Map> childList = settingMapper.getRoleMenu(paMap);
//			if(childList!=null&&childList.size()>0) {
//				map.put("childList", childList);
//			}
//		}
		Map roleMap = new HashMap();
		roleMap.put("role", role);
		roleMap.put("menuList", list);
		return roleMap;
	}

	@Override
	public Map getFileInfo(String val) {
		
		return settingMapper.getFileInfo(val);
	}


}
