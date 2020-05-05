//package com.chinobot.cityle.base.service.impl;
//
//import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.base.entity.AddressBase;
//import com.chinobot.cityle.base.entity.DeptGrid;
//import com.chinobot.cityle.base.entity.Grid;
//import com.chinobot.cityle.base.entity.Scene;
//import com.chinobot.cityle.base.entity.vo.VoAddressBase;
//import com.chinobot.cityle.base.entity.vo.VoAddressBaseScenesDeptGrids;
//import com.chinobot.cityle.base.mapper.GridMapper;
//import com.chinobot.cityle.base.service.IAddressBaseService;
//import com.chinobot.cityle.base.service.IDeptGridService;
//import com.chinobot.cityle.base.service.IGridService;
//import com.chinobot.cityle.base.service.ISceneService;
//import com.chinobot.common.constant.GlobalConstant;
//import com.chinobot.common.domain.Result;
//import com.chinobot.common.file.entity.FileBus;
//import com.chinobot.common.file.service.IFileBusService;
//import com.chinobot.common.utils.ResultFactory;
//import com.chinobot.framework.web.service.impl.BaseService;
//
///**
// * <p>
// * 网格 服务实现类
// * </p>
// *
// * @author shizt
// * @since 2019-03-26
// */
//@Service
//public class GridServiceImpl extends BaseService<GridMapper, Grid> implements IGridService {
//
//	@Autowired
//	private GridMapper gridMapper;
//	@Autowired
//	private IGridService gridService;
//	@Autowired
//	private IAddressBaseService addressBaseService;
//	@Autowired
//	private IDeptGridService deptGridService;
//	@Autowired
//	private ISceneService sceneService;
//	@Autowired
//	private IFileBusService fileBusService;
//	
//	@Override
//	public List<Map> getGridAddrList(Map<String, String> param) {
//		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
//		
//		return gridMapper.getGridAddrList(param);
//	}
//	
//	@Override
//	public IPage<VoAddressBase<Map>> getGridAddrPage(Page page, Map<String, String> param) {
//		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
//		
//		return gridMapper.getGridAddr(page, param);
//	}
//
//	@Override
//	public VoAddressBase<Map> getGridAddrByid(String uuid) {
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
//		param.put("uuid", uuid);
//		
//		return gridMapper.getGridAddr(param);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public Result editGrid(VoAddressBaseScenesDeptGrids<Grid> voAddressBaseScenesDeptGrids) {
//		
//		VoAddressBase<Grid> voAddressBase = voAddressBaseScenesDeptGrids.getVoAddressBase();
//		// 保存地址库
//		AddressBase addressBase = voAddressBase.getAddressBase();
//		addressBase.setAtype("cle_grid");
//		addressBaseService.saveOrUpdate(addressBase);
//		
//		// 保存网格
//		Grid grid = voAddressBase.getEntity();
//		grid.setAbId(addressBase.getUuid());
//		gridService.saveOrUpdate(grid);
//		
//		// 删除部门网格关联
//		QueryWrapper<DeptGrid> deptGridWrapper = new QueryWrapper<>();
//		deptGridWrapper.eq("grid_id", grid.getUuid());
//		deptGridService.remove(deptGridWrapper);
//		// 保存部门网格关联
//		List<DeptGrid> deptGrids = voAddressBaseScenesDeptGrids.getDeptGrids();
//		if(0 < deptGrids.size()) {
//			for (int i = 0, size =deptGrids.size(); i < size; i++) {
//				deptGrids.get(i).setGridId(grid.getUuid());
//			}
//			deptGridService.saveOrUpdateBatch(deptGrids);
//		}
//		
//		// 网格文件关联
//		List<FileBus> fileBus = voAddressBase.getFileBus();
//		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
//		queryWrapper.eq("bus_id", grid.getUuid());
//		// 清空文件关联
//		fileBusService.remove(queryWrapper);
//		if(0 < fileBus.size()) {
//			for (int i = 0, size = fileBus.size(); i < size; i++) {
//				fileBus.get(i)
//					.setBusId(grid.getUuid())
//					.setSort(i + 1)
//					.setModule("grid_album");
//			}
//			// 保存关联
//			fileBusService.saveOrUpdateBatch(fileBus);
//		}
//		
//		// 更新场景的网格id
//		List<Scene> scenes = voAddressBaseScenesDeptGrids.getScenes();
//		if(0 < scenes.size()) {
//			for (int i = 0, size = scenes.size(); i < size; i++) {
//				if(!"".equals(scenes.get(i).getGridId())) {
//					scenes.get(i).setGridId(grid.getUuid());
//				}
//			}
//			sceneService.updateBatchById(scenes);
//		}
//		
//		return ResultFactory.success(grid.getUuid());
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public Result delGrid(Grid grid) {
//		// 删除网格
//		grid.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
//		gridService.updateById(grid);
//		
//		// 删除场景网格关联
//		Scene scene = new Scene();
//		scene.setGridId("");
//		UpdateWrapper<Scene> sceneWrapper = new UpdateWrapper<Scene>();
//		sceneWrapper.eq("grid_id", grid.getUuid());
//		sceneService.update(scene, sceneWrapper);
//		
//		// 删除部门网格关联
//		DeptGrid deptGrid = new DeptGrid();
//		deptGrid.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
//		UpdateWrapper<DeptGrid> deptGridWrapper = new UpdateWrapper<DeptGrid>();
//		deptGridWrapper.eq("grid_id", grid.getUuid());
//		deptGridService.update(deptGrid, deptGridWrapper);
//		
//		return ResultFactory.success();
//	}
//
//	@Override
//	public List<LinkedHashMap<String, Object>> areaData(Map<String, String> param) {
//		return gridMapper.areaData(param);
//	}
//
//}
