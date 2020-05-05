package com.chinobot.plep.home.setting.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Role;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ZipUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.framework.web.entity.BaseMenu;
import com.chinobot.framework.web.entity.Basedata;
import com.chinobot.framework.web.service.IBaseMenuService;
import com.chinobot.framework.web.service.IBasedataService;
import com.chinobot.plep.home.help.entity.Help;
import com.chinobot.plep.home.help.service.IHelpService;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.setting.entity.MenuRole;
import com.chinobot.plep.home.setting.entity.RoleMenuVo;
import com.chinobot.plep.home.setting.entity.Setting;
import com.chinobot.plep.home.setting.service.IMenuRoleService;
import com.chinobot.plep.home.setting.service.ISettingService;
import com.drew.lang.StringUtil;
import com.google.common.collect.Maps;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import net.dongliu.apk.parser.bean.UseFeature;


/**
 * <p>
 * 系统配置表 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-08-21
 */
@Api(tags= {"配置类接口"})
@RestController
@RequestMapping("/api/setting")
public class SettingController extends BaseController {
	
	@Autowired
	private ISettingService settingService;
	@Autowired
	private IRoleService roleService;
	@Autowired
	private IBasedataService basedataService;
	@Autowired
	private IMenuRoleService menuRoleService;
	@Autowired
	private IBaseMenuService baseMenuService;
	@Autowired
	private IHelpService helpService;
	@Autowired
	private IUploadFileService fileService;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Value("${config.tempPath}")
	private String tempPath;
	
	
	@ApiOperation(value = "初始化系统配置", notes = "无参数")
	@GetMapping("")
//	@RequestMapping("")
	public Result initSetting() {
		List<Setting> list = settingService.list();
		Map map = new HashMap();
		for (Setting setting : list) {
			map.put(setting.getCode(), setting.getVal());
		}
		return ResultFactory.success(map);
	}
	
	
	@ApiOperation(value = "对码值做新增修改", notes = "参数- 码值basedata")
	@PostMapping("/updateBaseData")
//	@RequestMapping("/updateBaseData")
	public Result getBasedates(@RequestBody Basedata basedata) {
		
		return ResultFactory.success(basedataService.saveOrUpdate(basedata));
	}
	
	
	@ApiOperation(value = "查询码值列表", notes = "参数- 分页page,Map param")
	@GetMapping("/getBaseDatas")
//	@RequestMapping("/getBaseDatas")
	public Result getBaseDatas(Page page,@RequestParam  Map<String, Object> param) {
		
		return ResultFactory.success(settingService.getBaseDatas(page,param));
	}
	
	
	@ApiOperation(value = "根据类型名称模糊查询字段名称  -->父类码值", notes = "参数- Map param")
	@GetMapping("/getParBaseDate")
//	@RequestMapping("/getParBaseDate")
	public Result getParBaseDate(@RequestParam  Map<String, Object> param) {
		String typeName = (String) param.get("typename");
		String uuid = (String) param.get("uuid");
		Map map = new HashMap();
		QueryWrapper<Basedata> queryWrapper = new QueryWrapper<Basedata>();
		queryWrapper.eq("typename", typeName).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		if(uuid!=null || uuid!="") {
			queryWrapper.ne("uuid", uuid);
		}
		List<Basedata> list = basedataService.list(queryWrapper);
		map.put("parList", list);
		QueryWrapper<Basedata> queryWrapper2 = new QueryWrapper<Basedata>();
		queryWrapper2.eq("typename", typeName).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<Basedata> list2 = basedataService.list(queryWrapper2);
		if(list2.size()>0) {
			map.put("typecode", list2.get(0));
		}
		
		
		return ResultFactory.success(map);
	}
	
	
	@ApiOperation(value = "删除码值", notes = "参数- Map param")
	@GetMapping("/delBaseDate")
//	@RequestMapping("/delBaseDate")
	public Result delBaseDate(@RequestParam  Map<String, Object> param) {
		String uuid = (String) param.get("uuid");
		UpdateWrapper<Basedata> updateWrapper = new UpdateWrapper<Basedata>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		return ResultFactory.success(basedataService.update(updateWrapper));
	}
	
	
	@ApiOperation(value = "新增修改角色、角色菜单", notes = "参数- 角色菜单包装类")
	@PostMapping("/updateRole")
//	@RequestMapping("/updateRole")
	public Result updateRole(@RequestBody RoleMenuVo vo) {
		
		return ResultFactory.success(settingService.updateRole(vo));
	}
	
	@ApiOperation(value = "获取树形菜单", notes = "参数- Map param")
	@GetMapping("/getTreeMenu")
//	@RequestMapping("/getTreeMenu")
	public Result getTreeMenu(@RequestParam  Map<String, Object> param) {
		
		return ResultFactory.success(settingService.getTreeMenu(param));
	}
	
	/**
	 * 查询角色列表
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "查询角色列表", notes = "参数- 分页page,Map param")
	@GetMapping("/getRoles")
//	@RequestMapping("/getRoles")
	public Result getRoles(Page page,@RequestParam Map<String, Object> param) {
		String uuid = (String) param.get("uuid");
		if(uuid!=null && uuid!="") {
			return ResultFactory.success(settingService.getOnlyRole(param));
		}
		return ResultFactory.success(settingService.getRoles(page,param));
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "删除角色", notes = "参数- Map param")
	@GetMapping("/delRole")
//	@RequestMapping("/delRole")
	public Result delRole(@RequestParam  Map<String, Object> param) {
		String uuid = (String) param.get("uuid");
		boolean bo = false;
		//删除角色
		UpdateWrapper<Role> updateWrapper = new UpdateWrapper<Role>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		bo = roleService.update(updateWrapper);
		//删除菜单角色
		UpdateWrapper<MenuRole> updateWrapperMenuRole = new UpdateWrapper<MenuRole>();
		updateWrapperMenuRole.eq("role_id", uuid).eq("data_status", GlobalConstant.DATA_STATUS_INVALID);
		updateWrapperMenuRole.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		bo = menuRoleService.update(updateWrapperMenuRole);
		
		return ResultFactory.success(bo);
	}
	
	
	@ApiOperation(value = "查询菜单列表", notes = "参数- 分页page,Map param")
	@GetMapping("/getMenus")
//	@RequestMapping("/getMenus")
	public Result getMenus(Page page,@RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(settingService.getMenus(page,param));
	}
	
	@ApiOperation(value = "获取父类目录", notes = "参数- Map param")
	@GetMapping("/getParMenus")
//	@RequestMapping("/getParMenus")
	public Result getMenus(@RequestParam Map<String, Object> param) {
		QueryWrapper<BaseMenu> queryWrapper = new QueryWrapper<BaseMenu>();
		queryWrapper.eq("menu_type", "2").eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<BaseMenu> list = baseMenuService.list(queryWrapper);
		return ResultFactory.success(list);
	}
	
	
	@ApiOperation(value = "新增修改菜单列表", notes = "参数- 菜单baseMenu")
	@PostMapping("/updateMenu")
//	@RequestMapping("/updateMenu")
	public Result updateMenu(@RequestBody BaseMenu baseMenu) {
		
		return ResultFactory.success(baseMenuService.saveOrUpdate(baseMenu));
	}
	
	
	@ApiOperation(value = "删除菜单", notes = "参数- Map param")
	@GetMapping("/delMenu")
//	@RequestMapping("/delMenu")
	public Result delMenu(@RequestParam  Map<String, Object> param) {
		String uuid = (String) param.get("uuid");
		UpdateWrapper<BaseMenu> updateWrapper = new UpdateWrapper<BaseMenu>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		return ResultFactory.success(baseMenuService.update(updateWrapper));
	}
	
	
	@ApiOperation(value = "查询帮助列表", notes = "参数-分页page,Map param")
	@GetMapping("/getHelps")
//	@RequestMapping("/getHelps")
	public Result getHelps(Page page,@RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(settingService.getHelps(page,param));
	}
	
	
	@ApiOperation(value = "新增修改帮助列表", notes = "参数-帮助help对象")
	@PostMapping("/updateHelp")
//	@RequestMapping("/updateHelp")
	public Result updateHelp(@RequestBody Help help) {
		
		return ResultFactory.success(helpService.saveOrUpdate(help));
	}
	
	
	@ApiOperation(value = "删除帮助", notes = "参数-Map param")
	@GetMapping("/delHelp")
//	@RequestMapping("/delHelp")
	public Result delHelp(@RequestParam  Map<String, Object> param) {
		String uuid = (String) param.get("uuid");
		UpdateWrapper<Help> updateWrapper = new UpdateWrapper<Help>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		return ResultFactory.success(helpService.update(updateWrapper));
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	@ApiOperation(value = "新增修改系统配置", notes = "参数-setting集合")
	@PostMapping("/updateSetting")
//	@RequestMapping("/updateSetting")
	public Result updateSetting(@RequestBody List<Setting> vo) {
		
		for (Setting setting : vo) {
			settingService.saveOrUpdate(setting);
		}
		return ResultFactory.success();
	}
	
	
	@ApiOperation(value = "查询系统配置", notes = "无参数")
	@GetMapping("/getSettings")
//	@RequestMapping("/getSettings")
	public Result getSettings() {

		QueryWrapper<Setting> queryWrapper = new QueryWrapper<Setting>();
		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<Setting> list = settingService.list(queryWrapper);
		Map map = new HashMap();
		for (Setting setting : list) {
			if(setting.getCode().equals("appFileAPK")) {
				//获取文件信息
				Map fileMap = Maps.newHashMap();
				fileMap.put("settingMessage", setting);
				fileMap.put("fileMessage", settingService.getFileInfo(setting.getVal()));
				map.put(setting.getCode(), fileMap);
				continue;
			}
			map.put(setting.getCode(), setting);
		}
		return ResultFactory.success(map);
	}
	
	/**
	 * 获取帮助 的模型名称
	 */
//	@RequestMapping("/getHelpList")
	@ApiOperation(value = "获取帮助类信息列表", notes = "参数-模型名称")
	@GetMapping("/getHelpList")
	public Result getHelpList(@RequestParam Map<String, Object> param) {
		String moduleName = (String) param.get("moduleName");
		QueryWrapper<Help> queryWrapper = new QueryWrapper<Help>();
		queryWrapper.eq("module_name", moduleName).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		queryWrapper.orderByAsc("sort");
		queryWrapper.select("uuid","title","sort");
		List<Help> list = helpService.list(queryWrapper);
		
		return ResultFactory.success(list);
	}
	
	/**
	 * 获取单个帮助 
	 */
	@ApiOperation(value = "获取单个帮助信息", notes = "参数-Map param")
	@GetMapping("/getOnlyHelp")
//	@RequestMapping("/getOnlyHelp")
	public Result getOnlyHelp(@RequestParam Map<String, Object> param) {
		String helpId = (String) param.get("uuid");
		QueryWrapper<Help> queryWrapper = new QueryWrapper<Help>();
		queryWrapper.eq("uuid", helpId);
		Help help = helpService.getOne(queryWrapper);
		
		return ResultFactory.success(help);
	}
	
	/**
	 * 获取apk上传版本
	 */
	@ApiOperation(value = "获取apk上传版本", notes = "参数-Map param")
	@GetMapping("/getApkVersion")
	public Result getApkVersion(@ApiParam(name = "fileId", value = "文件id", required = true) @RequestParam(value = "fileId", required = true) String fileId,
			@ApiParam(name = "uuid", value = "setting主键", required = true) @RequestParam(value = "uuid", required = true) String uuid) {
		
		UploadFile uploadFile = fileService.getById(fileId);
		String path = uploadFile.getPath();//文件路径
		byte[] downloadFile = fastDFSClient.downloadFile (path);//下载文件
		//将Byte数组转换成文件
		ZipUtil.getFileByBytes(downloadFile, tempPath, "app-release");
		File file = new File(tempPath , "app-release");
		//获取apk版本号
		Map map = Maps.newHashMap();
		try (ApkFile apkFile = new ApkFile(file)) {
            ApkMeta apkMeta = apkFile.getApkMeta();
            
    		map.put("apkVersion", apkMeta.getVersionName());
    		if(apkFile!=null) {
    			//更新setting数据
    			Setting setting = new Setting();
    			if(CommonUtils.isEmpty(uuid)) {
    				//apk版本号
    				setting.setCode("apkCode");
    				setting.setDescription("apk版本号");
    				setting.setVal(apkMeta.getVersionCode().toString());
    			}else {
    				setting = settingService.getById(uuid);
    				//当前apk版本与上传apk版本比较，若当前版本小于上传版本，则更新
					setting.setVal(apkMeta.getVersionCode().toString());
					//是否需要更新
					map.put("isUpdate", true);
    			}
    			settingService.saveOrUpdate(setting);
    		}
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
			ZipUtil.deleteDirOfFiles(tempPath);//删除文件夹下的所有文件
		}
		
		return ResultFactory.success(map);
	}
	
}
