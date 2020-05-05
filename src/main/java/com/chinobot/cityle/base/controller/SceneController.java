//package com.chinobot.cityle.base.controller;
//
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.alibaba.fastjson.JSON;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.base.entity.Dept;
//import com.chinobot.cityle.base.entity.Role;
//import com.chinobot.cityle.base.entity.Scene;
//import com.chinobot.cityle.base.entity.SceneTask;
//import com.chinobot.cityle.base.entity.SceneTaskRole;
//import com.chinobot.cityle.base.entity.SceneTaskTemplet;
//import com.chinobot.cityle.base.entity.vo.SceneTaskRoleVo;
//import com.chinobot.cityle.base.entity.vo.VoAddressBase;
//import com.chinobot.cityle.base.service.IDeptService;
//import com.chinobot.cityle.base.service.IRoleService;
//import com.chinobot.cityle.base.service.ISceneService;
//import com.chinobot.cityle.base.service.ISceneTaskRoleService;
//import com.chinobot.cityle.base.service.ISceneTaskService;
//import com.chinobot.cityle.base.service.ISceneTaskTempletService;
//import com.chinobot.common.constant.GlobalConstant;
//import com.chinobot.common.domain.Result;
//import com.chinobot.common.file.service.IFileBusService;
//import com.chinobot.common.utils.CommonUtils;
//import com.chinobot.common.utils.ResultFactory;
//import com.chinobot.framework.web.controller.BaseController;
//
///**
// * <p>
// * 场景 前端控制器
// * </p>
// *
// * @author shizt
// * @since 2019-03-16
// */
//@RestController
//@RequestMapping("/api/base/scene")
//public class SceneController extends BaseController {
//
//	@Autowired
//	private ISceneService sceneService;
//	@Autowired
//	private IFileBusService fileBusService;
//	@Autowired
//	private ISceneTaskTempletService sceneTaskTempletService;
//	@Autowired
//	private ISceneTaskService sceneTaskService;
//	@Autowired
//	private IDeptService deptService;
//	@Autowired
//	private IRoleService roleService;
//	@Autowired
//	private ISceneTaskRoleService sceneTaskRoleService;
//	/**
//	 * 场景 分页列表
//	 * @param
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月18日
//	 * @company chinobot
//	 */
//	@RequestMapping("/page")
//	public Result scenePage(Page page, @RequestParam Map<String, String> param){
//		
//		return ResultFactory.success(sceneService.getScenePage(page, param));
//	}
//	
//	/**
//	 * 场景/地址 分页列表
//	 * @param
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月18日
//	 * @company chinobot
//	 */
//	@RequestMapping("/sceneAddrPage")
//	public Result sceneAddrPage(Page page, @RequestParam Map<String, String> param){
//		
//		return ResultFactory.success(sceneService.getSceneAddrPage(page, param));
//	}
//
//	/**
//	 * 根据id获取场景/地址
//	 * @param uuid
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月26日
//	 * @company chinobot
//	 */
//	@RequestMapping("")
//	public Result getSceneAddr(String uuid) {
//		Map result = new HashMap<>();
//		result.put("sceneAddr", sceneService.getSceneAddrByid(uuid));
//		
//		Map param = new HashMap<>();
//		param.put("busId", uuid);
//		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
//		param.put("module", "scene_album");
//		result.put("files", fileBusService.getFileIdByBusId(param));
//		
//		return ResultFactory.success(result);
//	}
//
//	/**
//	 * 场景 新增修改
//	 * @param voAddressBase
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月19日
//	 * @company chinobot
//	 */
//	@RequestMapping("/edit")
//	public Result sceneEdit(@RequestBody VoAddressBase<Scene> voAddressBase) {
//		
//		return sceneService.editScene(voAddressBase);
//	}
//
//	/**
//	 * 场景 删除
//	 * @param scene
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月18日
//	 * @company chinobot
//	 */
//	@RequestMapping("/del")
//	public Result sceneDel(@RequestBody Scene scene) {
//		scene.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
//
//		return ResultFactory.success(sceneService.updateById(scene));
//	}
//	
//	/**
//	 * 巡查内容模板 分页
//	 * @param page
//	 * @param param
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月10日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task/templetPage")
//	public Result getTaskTempletPage(Page page, @RequestParam Map<String, Object> param) {
//		
//		return ResultFactory.success(sceneTaskTempletService.getTaskTempletPage(page, param));
//	}
//	
//	/**
//	 * 根据id获取巡查内容模板
//	 * @param page
//	 * @param param
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月10日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task/templet")
//	public Result getTaskTemplet(String uuid) {
//		System.out.println("---------"+JSON.toJSONString(sceneTaskTempletService.getTaskTempletById(uuid)));
//		
//		return ResultFactory.success(sceneTaskTempletService.getTaskTempletById(uuid));
//	}
//	
//	/**
//	 * 巡查内容模板 编辑
//	 * @param sceneTaskTemplet
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月10日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task/templetEdit")
//	public Result taskTempletEdit(@RequestBody SceneTaskTemplet sceneTaskTemplet) {
//		
//		return ResultFactory.success(sceneTaskTempletService.saveOrUpdate(sceneTaskTemplet));
//	}
//	
//	/**
//	 * 巡查内容模板 删除
//	 * @param sceneTaskTemplet
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月18日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task/templetDel")
//	public Result taskTempletDel(@RequestBody SceneTaskTemplet sceneTaskTemplet) {
//		sceneTaskTemplet.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
//
//		return ResultFactory.success(sceneTaskTempletService.updateById(sceneTaskTemplet));
//	}
//	
//	/**
//	 * 巡查内容 分页
//	 * @param page
//	 * @param param
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月10日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task/page")
//	public Result getTaskPage(Page page, @RequestParam Map<String, String> param) {
//		
//		return ResultFactory.success(sceneTaskService.getTaskPage(page, param));
//	}
//	
//	/**
//	 * 根据id获取巡查内容
//	 * @param page
//	 * @param param
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月10日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task")
//	public Result getTask(@RequestParam Map<String, String> param) {
//		Map result = new HashMap<>();
//		if(CommonUtils.isNotEmpty(param.get("uuid"))) {
//			result.put("info", sceneTaskService.getTaskById(param.get("uuid")));
//		}
//		if(CommonUtils.isNotEmpty(param.get("sceneType"))) {
//			Map templetParam = new HashMap<>();
//			templetParam.put("sceneType", param.get("sceneType"));
//			templetParam.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
//			
//			result.put("templetList", sceneTaskTempletService.getTaskTempletByParam(templetParam));
//		}
//		
//		QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();
//		deptWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		result.put("dept", deptService.list(deptWrapper));
//		
//		return ResultFactory.success(result);
//	}
//	
//	/**
//	 * 巡查内容 编辑
//	 * @param sceneTask
//	 * @return
//	 * @author shizt  
//	 * @date 2019年4月10日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task/edit")
//	public Result taskEdit(@RequestBody SceneTaskRoleVo sceneTaskRoleVo) {
//		sceneTaskService.saveOrUpdateSceneTaskRole(sceneTaskRoleVo);
//		return ResultFactory.success();
//	}
//	
//	/**
//	 *  回显巡查任务 角色
//	 * @param uuid
//	 * @return
//	 */
//	@RequestMapping("/task/show/role")
//	public Result showSceneTaskRole(@RequestParam(required = false) String uuid) {
//		
//		Map result = new HashMap();
//		//角色列表
//		result.put("role", roleService.getRoleList(new HashMap<>()));
//		
//
//		QueryWrapper<SceneTaskRole> queryWrapper = new QueryWrapper<>();
//		queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID).eq("scene_task_id", uuid);
//		queryWrapper.select("role_id");
//		List<SceneTaskRole> list = sceneTaskRoleService.list(queryWrapper);
//		String roleId = null;
//		List list2 =new ArrayList<>();
//		for (SceneTaskRole sceneTaskRole : list) {
//			roleId = sceneTaskRole.getRoleId();
//			
//			QueryWrapper<Role> queryWrapper2 = new QueryWrapper<>();
//			queryWrapper2.eq("data_status", GlobalConstant.DATA_STATUS_VALID).eq("uuid", roleId);
//			queryWrapper2.select("rname","is_sys","uuid");
//			Role one = roleService.getOne(queryWrapper2);
//			if(one!=null) {
//				Map map = new HashMap<>();
//				map.put("roleId", one.getUuid());
//				map.put("rname", one.getRname());
//				map.put("isSys", one.getIsSys());
//				list2.add(map);
//			}
//		}
//		
//		
//		//场景任务角色
//		result.put("sceneRole", list2);
//		
//		return ResultFactory.success(result);
//	}
//	
//	
//	/**
//	 * 巡查内容 删除
//	 * @param sceneTask
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月18日
//	 * @company chinobot
//	 */
//	@RequestMapping("/task/del")
//	public Result taskDel(@RequestBody SceneTask sceneTask) {
//		sceneTask.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
//
//		return ResultFactory.success(sceneTaskService.updateById(sceneTask));
//	}
//	
//	
////	/**
////	 * 更新场景的数据
////	 * @param scene
////	 * @return Scene
////	 */
////	@PostMapping
////	public Result fresh(@RequestBody Scene scene) {
////		return ResultFactory.success(sceneService.updateById(scene));
////	}
//}
