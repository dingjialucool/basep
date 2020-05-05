package com.chinobot.framework.web.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.UserRole;
import com.chinobot.cityle.base.service.IUserRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.entity.BaseMenu;
import com.chinobot.framework.web.service.IBaseMenuService;
import com.chinobot.plep.home.setting.mapper.SettingMapper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 菜单 前端控制器
 * </p>
 *
 * @author zhoull
 * @since 2019-05-31
 */
@Api(tags= {"菜单类接口"})
@RestController
@RequestMapping("/api/menu")
public class MenuController extends BaseController {

	@Autowired
	private IBaseMenuService baseMenuService;
	@Autowired
	private SettingMapper settingMapper;
	@Autowired
	private IUserRoleService userRoleService;
	/**
	 * 获取菜单
	 * @return
	 * @author zhoull  
	 * @date 2019年5月31日
	 * @company chinobot
	 */
//	@RequestMapping("")
	@ApiOperation(value = "获取菜单列表", notes = "无参数")
	@GetMapping("")
	public Result getBaseMenu() {
		Person personInfo =  ThreadLocalUtil.getResources();
//		QueryWrapper<UserRole> queryWrapper = new QueryWrapper<UserRole>();
//		queryWrapper.eq("person_id", personInfo.getUuid()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		UserRole userRole = userRoleService.getOne(queryWrapper);
		
		if(!CommonUtils.isObjEmpty(personInfo)) {
			List<BaseMenu> menuList = baseMenuService.searchMenuByPersonId(personInfo.getUuid());
			List result = new ArrayList();
			Map<String, Object> type = new HashMap();
			for (int a=0;a<menuList.size() ; a++) {
				type = new HashMap();
				type.put("title", menuList.get(a).getTitleValue());
				type.put("key", menuList.get(a).getKeyValue());
				type.put("icon", menuList.get(a).getIconValue());
				type.put("moduleType", menuList.get(a).getMenuType());
				type.put("path", menuList.get(a).getPathValue());
				Map paMap = new HashMap();
				paMap.put("uuid", menuList.get(a).getUuid());
				paMap.put("personId", personInfo.getUuid());
				List<Map> childList = settingMapper.getRoleMenus(paMap);
				if(childList!=null&&childList.size()>0) {
					type.put("childList", childList);
				}
				result.add(type);
			}
//			System.err.println("result: " + JSON.toJSONString(result));
			return ResultFactory.success(result);
		}
		return null;
		
	}
	
	@ApiOperation(value = "获取系统列表", notes = "无参数")
	@GetMapping("/systemList")
	public Result systemList() {
		Person personInfo =  ThreadLocalUtil.getResources();
		if(!CommonUtils.isObjEmpty(personInfo)) {
			List<BaseMenu> menuList = baseMenuService.searchMenuByPersonId(personInfo.getUuid());
			List result = new ArrayList();
			Map<String, Object> type = new HashMap();
			for (int a=0;a<menuList.size() ; a++) {
				type = new HashMap();
				type.put("title", menuList.get(a).getTitleValue());
				type.put("key", menuList.get(a).getKeyValue());
				type.put("icon", menuList.get(a).getIconValue());
				type.put("moduleType", menuList.get(a).getMenuType());
				type.put("path", menuList.get(a).getPathValue());
				type.put("systemId", menuList.get(a).getUuid());
				result.add(type);
			}
			return ResultFactory.success(result);
		}
		return null;
		
	}
	
	@ApiOperation(value = "获取系统菜单列表", notes = "无参数")
	@ApiImplicitParams({
		@ApiImplicitParam(name="systemId",value="系统菜单主键",required=true,paramType="query")
	})
	@GetMapping("/getSystemMenu")
	public Result getSystemMenu(String systemId) {
		Person personInfo =  ThreadLocalUtil.getResources();
		
		if(!CommonUtils.isObjEmpty(personInfo)) {
			List<BaseMenu> menuList = baseMenuService.searchMenuByPersonIdAndSystem(personInfo.getUuid(), systemId);
			List result = new ArrayList();
			Map<String, Object> type = new HashMap();
			for (int a=0;a<menuList.size() ; a++) {
				type = new HashMap();
				type.put("titleValue", menuList.get(a).getTitleValue());
				type.put("keyValue", menuList.get(a).getKeyValue());
				type.put("icon", menuList.get(a).getIconValue());
				type.put("moduleType", menuList.get(a).getMenuType());
				type.put("pathValue", menuList.get(a).getPathValue());
				Map paMap = new HashMap();
				paMap.put("uuid", menuList.get(a).getUuid());
				paMap.put("personId", personInfo.getUuid());
				List<Map> childList = settingMapper.getRoleMenus(paMap);
				if(childList!=null&&childList.size()>0) {
					type.put("childList", childList);
				}
				result.add(type);
			}
//			System.err.println("result: " + JSON.toJSONString(result));
			return ResultFactory.success(result);
		}
		return null;
		
	}
}
