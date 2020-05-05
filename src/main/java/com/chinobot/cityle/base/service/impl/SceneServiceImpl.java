//package com.chinobot.cityle.base.service.impl;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Random;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.baomidou.mybatisplus.core.conditions.Wrapper;
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.chinobot.cityle.base.entity.AddressBase;
//import com.chinobot.cityle.base.entity.Regions;
//import com.chinobot.cityle.base.entity.Scene;
//import com.chinobot.cityle.base.entity.vo.VoAddressBase;
//import com.chinobot.cityle.base.mapper.SceneMapper;
//import com.chinobot.cityle.base.mapper.SceneTaskMapper;
//import com.chinobot.cityle.base.service.IAddressBaseService;
//import com.chinobot.cityle.base.service.ISceneService;
//import com.chinobot.common.constant.GlobalConstant;
//import com.chinobot.common.domain.Result;
//import com.chinobot.common.file.entity.FileBus;
//import com.chinobot.common.file.service.IFileBusService;
//import com.chinobot.common.utils.CommonUtils;
//import com.chinobot.common.utils.ResultFactory;
//import com.chinobot.framework.web.service.impl.BaseService;
//
///**
// * <p>
// * 场景 服务实现类
// * </p>
// *
// * @author shizt
// * @since 2019-03-16
// */
//@Service
//public class SceneServiceImpl extends BaseService<SceneMapper, Scene> implements ISceneService {
//
//	@Autowired
//	private SceneMapper sceneMapper;
//	@Autowired
//	private ISceneService sceneService;
//	@Autowired
//	private IAddressBaseService addressBaseService;
//	@Autowired
//	private IFileBusService fileBusService;
//	@Autowired
//	private SceneTaskMapper sceneTaskMapper;
//	
//	@Override
//	public IPage<Scene> getScenePage(Page page, Map<String, String> param) {
//
////        if(CommonUtils.isNotEmpty(param.get("imps"))) {
////            System.out.println(param.get("imps"));
////            return sceneMapper.getSenceImpList(page,param);
////        }
//
//
//        QueryWrapper<Scene> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//        if(CommonUtils.isNotEmpty(param.get("sname"))) {
//        	queryWrapper.like("sname", param.get("sname"));
//        }
//        if(CommonUtils.isNotEmpty(param.get("busType"))) {
//         	String str =  param.get("busType");
//         	if(str.indexOf(',')>0) {
//         		String[] split = str.split(",");
//         		queryWrapper.in("bus_type", split);
//         	}else {
// 				queryWrapper.eq("bus_type", str);
// 			}
//        }
//        if(CommonUtils.isNotEmpty(param.get("stype"))) {
//          	String str =  param.get("stype");
//          	if(str.indexOf(',')>0) {
//          		String[] split = str.split(",");
//          		queryWrapper.in("stype", split);
//          	}else {
//  				queryWrapper.eq("stype", str);
//  			}
//        }
//        if(CommonUtils.isNotEmpty(param.get("address"))) {
//            queryWrapper.like("address", param.get("address"));
//        }
//        if(CommonUtils.isNotEmpty(param.get("imps"))) {
//            queryWrapper.like("imp", param.get("imps"));
//        }
//        queryWrapper.orderByDesc("create_time");
//
//        return sceneMapper.selectPage(page, queryWrapper);
//	}
//
//	@Override
//	public IPage<VoAddressBase<Map>> getSceneAddrPage(Page page, Map<String, String> param) {
//		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
//		
//		return sceneMapper.getSceneAddr(page, param);
//	}
//	
//	@Override
//	public VoAddressBase<Map> getSceneAddrByid(String uuid) {
//		Map<String, String> param = new HashMap<String, String>();
//		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
//		param.put("uuid", uuid);
//		
//		return sceneMapper.getSceneAddr(param);
//	}
//
//	@Override
//	public List<Map> getEditGridScene(String gridId) {
//		
//		return sceneMapper.getEditGridScene(gridId);
//	}
//
//	@Transactional(rollbackFor = Exception.class)
//	@Override
//	public Result editScene(VoAddressBase<Scene> voAddressBase) {
//		// 地址库
//		AddressBase addressBase = voAddressBase.getAddressBase();
//		addressBase.setAtype("cle_scene");
//		addressBaseService.saveOrUpdate(addressBase);
//
//		// 场景
//		Scene scene = voAddressBase.getEntity();
//		boolean noUuid = CommonUtils.isEmpty(scene.getUuid());
//		scene.setAbId(addressBase.getUuid());
//		sceneService.saveOrUpdate(scene);
//		
//		// 场景文件关联
//		List<FileBus> fileBus = voAddressBase.getFileBus();
//		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
//		queryWrapper.eq("bus_id", scene.getUuid());
//		// 清空文件关联
//		fileBusService.remove(queryWrapper);
//		if(0 < fileBus.size()) {
//			for (int i = 0, size = fileBus.size(); i < size; i++) {
//				fileBus.get(i)
//					.setBusId(scene.getUuid())
//					.setSort(i + 1)
//					.setModule("scene_album");
//			}
//			// 保存关联
//			fileBusService.saveOrUpdateBatch(fileBus);
//		}
//		
//		// 新增时根据场景类型添加巡查内容
//		if(noUuid && CommonUtils.isNotEmpty(scene.getStype())) {
//			sceneTaskMapper.addTempletTask(scene.getStype(), scene.getUuid());
//		}
//		
//		return ResultFactory.success(scene.getUuid());
//	}
//
//
//	/**
//	 * 首页综合执法数据
//	 */
//	@Override
//	public Map getZongHe(Map<String, Object> param) {
//		
//		return sceneMapper.getZongHe(param);
//	}
//
//
//
//	@Override
//	public List<Map> getReportSceneList(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//		return sceneMapper.getReportSceneList(params);
//	}
//
//	@Override
//	public Map getReportViolationCount(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//		Map<String, Object> series = new HashMap<String, Object>();
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("data", sceneMapper.getReportViolationCount(params));
//		series.put("series", data);
//		return series;
//	}
//
//	@Override
//	public Map getReportFiveMonthViolation(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//		List<String> monthList = new ArrayList<String>();
//		for (int i = 4; i >= 0; i--) {
//			Date date = CommonUtils.calDate(Calendar.MONTH, -i);
//			monthList.add(CommonUtils.getDatetoString(date, "yyyy-MM"));
//		}
//		Map<String, Object> result = new HashMap<String, Object>();
//		
//		Map<String, Object> xAxis = new HashMap<String, Object>();
//		xAxis.put("data", monthList);
//		result.put("xAxis", xAxis);
//
//		Map<String, Object> series = new HashMap<String, Object>();
//		List<Integer> seriesData = new ArrayList<Integer>();
//		List<Map> list = sceneMapper.getReportFiveMonthViolation(params);
//		for (String month : monthList) {
//			int value = 0;
//			for (Map map : list) {
//				if(map.get("month").equals(month)) {
//					value = Integer.parseInt(String.valueOf(map.get("value")));
//					break;
//				}
//			}
//			seriesData.add(value);
//		}
//		series.put("data", seriesData);
//		result.put("series", series);
//		return result;
//	}
//
//	@Override
//	public Map getReportViolationType(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//		Map<String, Object> result = new HashMap<String, Object>();
//		List<Map> list = sceneMapper.getReportViolationType(params);
//		Map<String, Object> xAxis = new HashMap<String, Object>();
//		Map<String, Object> series = new HashMap<String, Object>();
//		List<String> xAxisData = new ArrayList<String>();
//		List<Integer> seriesData = new ArrayList<Integer>();
//		for (Map map : list) {
//			xAxisData.add(String.valueOf(map.get("type")));
//			seriesData.add(Integer.parseInt(String.valueOf(map.get("count"))));
//		}
//		xAxis.put("data", xAxisData);
//		series.put("data", seriesData);
//		result.put("xAxis", xAxis);
//		result.put("series", series);
//		return result;
//	}
//
//	@Override
//	public Map getReportViolationTypeRadar(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//		Map<String, Object> result = new HashMap<String, Object>();
//		List<Map> list = sceneMapper.getReportViolationType(params);
//		Map<String, Object> radar = new HashMap<String, Object>();
//		Map<String, Object> series = new HashMap<String, Object>();
//		List<Map<String, Object>> indicator = new ArrayList<Map<String,Object>>();
//		List<Integer> seriesData = new ArrayList<Integer>();
//		for (Map map : list) {
//			int count = Integer.parseInt(String.valueOf(map.get("count")));
//			Map<String, Object> imap = new HashMap<String, Object>();
//			imap.put("name", String.valueOf(map.get("type")));
//			imap.put("max", Math.ceil(count * 1.5));
//			indicator.add(imap);
//			seriesData.add(count);
//		}
//		series.put("data", seriesData);
//		radar.put("indicator", indicator);
//		result.put("radar", radar);
//		result.put("series", series);
//		return result;
//	}
//
//	@Override
//	public Map getReportIglbuiltCollect(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//
//		Map<String, Object> result = new HashMap<String, Object>();
//		Map<String, Object> xAxis = new HashMap<String, Object>();
//		List<String> xAxisData = new ArrayList<String>();
//		xAxisData.add("1季度");
//		xAxisData.add("2季度");
//		xAxisData.add("3季度");
//		xAxisData.add("4季度");
//		xAxis.put("data", xAxisData);
//		
//		Map<String, Object> yAxis = new HashMap<String, Object>();
//		List<Map<String, Object>> yAxisData = new ArrayList<Map<String, Object>>();
//		Map yMap = new HashMap<String, Object>();
//		yMap.put("name", "违章建筑");
//		yMap.put("max", 1000);
//		yMap.put("interval", 200);
//		yAxisData.add(0, yMap);
//		yMap = new HashMap<String, Object>();
//		yMap.put("name", "违章面积");
//		yMap.put("max", 15000);
//		yMap.put("interval", 3000);
//		yAxisData.add(1, yMap);
//		yAxis.put("data", yAxisData);
//		
//		Map<String, Object> series = new HashMap<String, Object>();
//		List<Map<String, Object>> seriesData = new ArrayList<Map<String, Object>>();
//		List<Map> list = sceneMapper.getReportIglbuiltCollect(params);
//		List<Integer> countList = new ArrayList<Integer>();
//		List<Float> areaList = new ArrayList<Float>();
//		for (Map map : list) {
//			int count = Integer.parseInt(String.valueOf(map.get("count")));
//			countList.add(count);
//			areaList.add((float) (count * 50.0));
//		}
//		Map sMap = new HashMap<String, Object>();
//		sMap.put("name", "违章建筑");
//		sMap.put("data", countList);
//		seriesData.add(0, sMap);
//		sMap = new HashMap<String, Object>();
//		sMap.put("name", "违章面积");
//		sMap.put("data", areaList);
//		seriesData.add(1, sMap);
//		series.put("data", seriesData);
//		
//		result.put("xAxis", xAxis);
//		result.put("yAxis", yAxis);
//		result.put("series", series);
//		return result;
//	}
//
//	@Override
//	public Map getReportLawefcQuarter(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//		Map<String, Object> result = new HashMap<String, Object>();
//		Map<String, Object> dataset = new HashMap<String, Object>();
//		List<Object> source = new ArrayList<Object>();
//		List<Map> sq = sceneMapper.getReportSceneQuarter(params);
//		List<Map> wq = sceneMapper.getReportWarningQuarter(params);
//		List<Map> gq = sceneMapper.getReportGridQuarter(params);
//		for (int i = 0; i < 4; i++) {
//			List<Object> data = new ArrayList<Object>();
//			data.add((i+1)+"季度");
//			data.add(sq.get(i).get("count"));
//			data.add(wq.get(i).get("count"));
//			data.add(gq.get(i).get("count"));
//			source.add(data);
//		}
//		dataset.put("source", source);
//		result.put("dataset", dataset);
//		return result;
//	}
//
//	@Override
//	public Map getSceneInfo(Map<String, Object> params) {
//		// TODO Auto-generated method stub
//		Map<String, Object> result = new HashMap<String, Object>();
//		QueryWrapper<Scene> queryWrapper = new QueryWrapper<Scene>();
//		queryWrapper.eq("uuid", params.get("uuid"));
//		Scene scene = super.getOne(queryWrapper);
//		result.put("name", scene.getSname());
//		result.put("address", scene.getAddress());
//		result.put("floor", scene.getFloor());
//		Random random = new Random();
//		int area = random.nextInt(2000) % (1000 + 1) + 1000;
//		result.put("area", area);
//		return result;
//	}
//}
