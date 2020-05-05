//package com.chinobot.cityle.base.controller;
//
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.base.entity.Dept;
//import com.chinobot.cityle.base.entity.DeptGrid;
//import com.chinobot.cityle.base.entity.Grid;
//import com.chinobot.cityle.base.entity.vo.VoAddressBaseScenesDeptGrids;
//import com.chinobot.cityle.base.service.IDeptGridService;
//import com.chinobot.cityle.base.service.IDeptService;
//import com.chinobot.cityle.base.service.IGridService;
//import com.chinobot.cityle.base.service.ISceneService;
//import com.chinobot.common.constant.GlobalConstant;
//import com.chinobot.common.domain.Result;
//import com.chinobot.common.file.service.IFileBusService;
//import com.chinobot.common.utils.CommonUtils;
//import com.chinobot.common.utils.ResultFactory;
//import com.chinobot.framework.web.controller.BaseController;
//
///**
// * <p>
// * 网格 前端控制器
// * </p>
// *
// * @author shizt
// * @since 2019-03-26
// */
//@RestController
//@RequestMapping("/api/base/grid")
//public class GridController extends BaseController {
//	
//	@Autowired
//	private IGridService gridService;
//	@Autowired
//	private IDeptService deptService;
//	@Autowired
//	private IDeptGridService deptGridService;
//	@Autowired
//	private ISceneService sceneService;
//	@Autowired
//	private IFileBusService fileBusService;
//	
//	/**
//	 * 网格/地址 列表
//	 * @return
//	 * @author shizt  
//	 * @date 2019年3月27日
//	 * @company chinobot
//	 */
//	@RequestMapping("/gridAddrList")
//	public Result gridAddrList(@RequestParam Map<String, String> param) {
//		
//		return ResultFactory.success(gridService.getGridAddrList(param));
//	}
//	
//	/**
//	 * 网格/地址 分页列表
//	 * @return
//	 * @author shizt  
//	 * @throws Exception 
//	 * @date 2019年3月27日
//	 * @company chinobot
//	 */
//	@RequestMapping("/gridAddrPage")
//	public Result gridAddrPage(Page page, @RequestParam Map<String, String> param) throws Exception {
//		
//		return ResultFactory.success(gridService.getGridAddrPage(page, param));
//	}
//
//	/**
//	 * 根据id获取网格
//	 * @param uuid
//	 * @return
//	 * @author shizt  
//	 * @date 2019年3月26日
//	 * @company chinobot
//	 */
//	@RequestMapping("")
//	public Result getGridAddr(String uuid) {
//		Map map = new HashMap();
//		if(CommonUtils.isNotEmpty(uuid)) {
//			map.put("info", gridService.getGridAddrByid(uuid));
//		}
//		// 待修改网格的场景
//		map.put("editGridScene", sceneService.getEditGridScene(uuid));
//		
//		// 部门列表
//		QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();
//		deptWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//		map.put("deptList", deptService.list(deptWrapper));
//		// 已关联网格的部门列表
//		Map paramDept = new HashMap<>();
//		paramDept.put("data_status", GlobalConstant.DATA_STATUS_VALID);
//		paramDept.put("grid_id", uuid);
//		map.put("deptGridList", deptGridService.getDeptGrid(paramDept));
//		
//		Map param = new HashMap<>();
//		param.put("busId", uuid);
//		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
//		param.put("module", "grid_album");
//		map.put("files", fileBusService.getFileIdByBusId(param));
//		
//		return ResultFactory.success(map);
//	}
//	
//	/**
//	 * 网格 新增修改
//	 * @param voAddressBase
//	 * @return
//	 * @author shizt  
//	 * @date 2019年3月27日
//	 * @company chinobot
//	 */
//	@RequestMapping("/edit")
//	public Result edit(@RequestBody VoAddressBaseScenesDeptGrids<Grid> voAddressBaseScenesDeptGrids) {
//		
//		return gridService.editGrid(voAddressBaseScenesDeptGrids);
//	}
//	
//	/**
//	 * 删除
//	 * @param grid
//	 * @return
//	 * @author shizt
//	 * @date 2019年3月18日
//	 * @company chinobot
//	 */
//	@RequestMapping("/del")
//	public Result del(@RequestBody Grid grid) {
//		
//		return gridService.delGrid(grid);
//	}
//}
