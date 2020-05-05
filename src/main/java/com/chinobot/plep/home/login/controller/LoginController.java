package com.chinobot.plep.home.login.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl.Role;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.UserRole;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.cityle.base.service.IUserRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.MD5Util;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.common.utils.TokenUtil;
import com.chinobot.framework.web.entity.BaseMenu;
import com.chinobot.framework.web.service.IBaseMenuService;
import com.chinobot.plep.home.area.entity.GovArea;
import com.chinobot.plep.home.area.service.IGovAreaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @ClassName: LoginController   
 * @Description: 用户登录 
 * @author: dingjialu  
 * @date:2019年4月13日 下午4:15:07
 */
@Api(tags= {"登录接口"})
@RestController
@RequestMapping("/api")
public class LoginController {

	@Autowired
	private IPersonService personService;
	
	@Autowired
	private IBaseMenuService baseMenuService;
	
	@Autowired
	private IDeptService deptService;
	
	@Autowired
	private IUserRoleService userRoleService;
	
	@Autowired
	private IRoleService roleService;
	
	@Autowired
	private IGovAreaService areaService;
	
	@ApiOperation(value = "用户登录", notes = "参数- Map param")
	@PostMapping("/login")
//	@RequestMapping("/login")
	public Result login(@RequestBody Map<String, Object> param) {
		if(param == null || "".equals(param)) {
			return ResultFactory.fail(null);
		}
		if(param.get("username") == null || param.get("password") == null) {
			return ResultFactory.fail(null);
		}
	
		QueryWrapper<Person> queryWrapper = new QueryWrapper();
        queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
        queryWrapper.eq("phone",param.get("username")).eq("password",MD5Util.getMD5(param.get("password").toString())).or(i ->i.eq("email",param.get("username")).eq("password",MD5Util.getMD5(param.get("password").toString())).eq("data_status", GlobalConstant.DATA_STATUS_VALID));
        Person one = personService.getOne(queryWrapper);
        
        if(one !=null) {
        	System.out.println("登录成功！");
        	//id 当前用户ID
        	String id = one.getUuid();
        	
        	QueryWrapper<Dept> deptWrapper = new QueryWrapper();
        	deptWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
        	deptWrapper.eq("uuid",one.getDeptId());
        	Dept dept = deptService.getOne(deptWrapper);
        	//查询行政区划
        	GovArea area = areaService.getById(dept.getAreaId());
        	dept.setAreaCode(area.getCode());
        	List<Map> roleList = roleService.getRoleByPersonId(one.getUuid());
        	String isSysRole = "1";
        	if(roleList.size()>0) {
        		isSysRole = (String) roleList.get(0).get("isSys");
        	}
        	
        	int ttlMillis =  30; 
        	//audience 接收该JWT的一方，是否使用是可选的
        	String token = null;
			try {
				token = TokenUtil.createToken(one,dept,roleList,ttlMillis);
			} catch (Exception e) {
				e.printStackTrace();
			}
        	HashMap<String,Object> result = new HashMap<>();
        	result.put("userId", id);
        	result.put("token", token);
        	result.put("userName", one.getPname());
        	result.put("deptName", dept.getDname());
        	result.put("deptId", dept.getUuid());
        	result.put("userMenu", getPersonBaseMenu(id));
        	result.put("isSysRole", isSysRole);
        	return ResultFactory.success(result);
        }
        System.out.println("登录失败！");
        return ResultFactory.fail(null);
	}
	/**
	 * 获取用户菜单信息
	 * @param uuid
	 * @return
	 */
	private List getPersonBaseMenu(String uuid){
		List<BaseMenu> menuList = baseMenuService.searchMenuByPersonId(uuid);
		List result = new ArrayList();
		Map<String, String> type = new HashMap();
		for (int a=0;a<menuList.size() ; a++) {
			type = new HashMap();
			type.put("title", menuList.get(a).getTitleValue());
			type.put("key", menuList.get(a).getKeyValue());
			type.put("icon", menuList.get(a).getIconValue());
			type.put("path", menuList.get(a).getPathValue());
			type.put("uuid", menuList.get(a).getUuid());
			result.add(type);
		}
		return result;
	}
}
