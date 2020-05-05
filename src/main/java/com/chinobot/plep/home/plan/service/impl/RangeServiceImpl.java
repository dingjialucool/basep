package com.chinobot.plep.home.plan.service.impl;

import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.building.mapper.BuildingMapper;
import com.chinobot.plep.home.building.mapper.VillageMapper;
import com.chinobot.plep.home.event.entity.EarlyWarning;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.mapper.EarlyWarningMapper;
import com.chinobot.plep.home.event.mapper.EventMainMapper;
import com.chinobot.plep.home.plan.entity.CameraConfig;
import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.FlyConfig;
import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.OutputParamter;
import com.chinobot.plep.home.plan.entity.Range;
import com.chinobot.plep.home.plan.entity.RangeDept;
import com.chinobot.plep.home.plan.entity.SceneRange;
import com.chinobot.plep.home.plan.entity.vo.VoAddressBaseRangeGrids;
import com.chinobot.plep.home.plan.entity.vo.VoAreaPathParm;
import com.chinobot.plep.home.plan.entity.vo.VoPathPlan;
import com.chinobot.plep.home.plan.mapper.FlyAreaMapper;
import com.chinobot.plep.home.plan.mapper.RangeMapper;
import com.chinobot.plep.home.plan.service.ICameraConfigService;
import com.chinobot.plep.home.plan.service.IFlyAreaService;
import com.chinobot.plep.home.plan.service.IFlyConfigService;
import com.chinobot.plep.home.plan.service.IFlyPathService;
import com.chinobot.plep.home.plan.service.IOutputParamterService;
import com.chinobot.plep.home.plan.service.IRangeDeptService;
import com.chinobot.plep.home.plan.service.IRangeService;
import com.chinobot.plep.home.plan.service.ISceneRangeService;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.entity.DeptGrid;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.mapper.DeptGridMapper;
import com.chinobot.cityle.base.service.IAddressBaseService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.PointUtil;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 巡查范围表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-06-16
 */
@Service
public class RangeServiceImpl extends BaseService<RangeMapper, Range> implements IRangeService {
	@Autowired
	private RangeMapper rangeMapper;
	@Autowired
	private IAddressBaseService addressBaseService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IUploadFileService fileUploadService;
	@Autowired
	private IRangeDeptService rangeDeptService;
	@Autowired
	private EarlyWarningMapper warningMapper;
	@Autowired
	private EventMainMapper eventMapper;
	@Autowired
	private BuildingMapper buildingMapper;
	@Autowired
	private DeptGridMapper deptGridMapper;
	@Autowired
	private VillageMapper VillageMapper;
	@Autowired
	private IFlyAreaService flyAreaService;
	@Autowired
	private IFlyPathService flyPathService;
	@Autowired
	private IOutputParamterService outparmService;
	@Autowired
	private IFlyConfigService flyConfigService;
	@Autowired 
	private ICameraConfigService cameraConfigService; 
	@Autowired
	private ISceneRangeService sceneRangeService;
	@Autowired
	private IRangeService rangeService;
	
	@Override
	public IPage<VoAddressBase<Map>> getRangeAddrPage(Page page, Map<String, Object> param) {
		return rangeMapper.getRangeAddr(page, param);
	}

	@Override
	public Result editRange(VoAddressBaseRangeGrids<Range> vo) throws Exception {
		VoAddressBase<Range> voAddressBase = vo.getVoAddressBase();
		// 保存地址库
		AddressBase addressBase = voAddressBase.getAddressBase();
		addressBase.setAtype("p_range_dept");
		addressBaseService.saveOrUpdate(addressBase);
		
		// 保存范围
		Range range = voAddressBase.getEntity();
		range.setAbId(addressBase.getUuid());
		boolean newAdd = false;
		if(StringUtils.isBlank(range.getUuid())) {
			newAdd = true;
		}
		this.saveOrUpdate(range);
		// 删除范围部门关联
		QueryWrapper<RangeDept> delWrapper = new QueryWrapper<RangeDept>();
		delWrapper.eq("range_id", range.getUuid());
		rangeDeptService.remove(delWrapper);
		// 保存范围部门关联
		List<RangeDept> deptGrids = vo.getDeptGrids();
		if (0 < deptGrids.size()) {
			for (int i = 0, size = deptGrids.size(); i < size; i++) {
				deptGrids.get(i).setRangeId(range.getUuid());
			}
			rangeDeptService.saveOrUpdateBatch(deptGrids);
		}
		//删除场景范围关联
		QueryWrapper<SceneRange> sceneRangeWrapper = new QueryWrapper<SceneRange>();
		sceneRangeWrapper.eq("range_id", range.getUuid());
		sceneRangeService.remove(sceneRangeWrapper);
		//保存场景范围关联
		List<SceneRange> scenes = vo.getScenes();
		for(SceneRange scene : scenes) {
			scene.setRangeId(range.getUuid());
			sceneRangeService.save(scene);
		}
		// 网格文件关联
		List<FileBus> fileBus = voAddressBase.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", range.getUuid());
		// 清空文件关联
		fileBusService.remove(queryWrapper);
		if (0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i).setBusId(range.getUuid()).setSort(i + 1).setModule("range_album");
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
		if(newAdd) {
			return ResultFactory.success(range.getUuid());
		}
		return ResultFactory.success();
	}

	@Override
	public Result delRange(Range range) {
		
		String rangeId = range.getUuid();
		QueryWrapper<FlyArea> queryWrapper = new QueryWrapper<FlyArea>();
		queryWrapper.eq("rang_id", rangeId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<FlyArea> list = flyAreaService.list(queryWrapper);
		//删除微区域下的航线
		for (FlyArea flyArea : list) {
			QueryWrapper<FlyPath> flyPathQuery = new QueryWrapper<FlyPath>();
			flyPathQuery.eq("area_id", flyArea.getUuid());
			List<FlyPath> flyPathList = flyPathService.list(flyPathQuery);
			for (FlyPath flyPath : flyPathList) {
				flyPath.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
				flyPathService.saveOrUpdate(flyPath);
			}
			//删除所有微区域
			flyArea.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			flyAreaService.saveOrUpdate(flyArea);
		}
		//删除区域
		range.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		rangeService.saveOrUpdate(range);
		
		return ResultFactory.success();
	}

	@Override
	public VoAddressBase<Map> getRangeAddrById(String uuid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("uuid", uuid);
		return rangeMapper.getRangeAddr(param);
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void test() throws Exception {
		File fileAll = new File("D:\\workProject\\city\\workDoc\\DJI_0314");
		for (File file : fileAll.listFiles()) {
			doTestSq(file);
		}
	}

	void doTestSq(File sq) throws Exception {
		//String adress = "广东省深圳市宝安区燕罗街道" + sq.getName();
		String adress = "广东省深圳市南山区" + sq.getName();
		File[] sublist = sq.listFiles();
		List<Map<String, String>> allPolyLine = VillageMapper.getAllPolyLine();
		for (int i = 0; i < sublist.length; i++) {
			String eventCode = sublist[i].getName();// 事件编号
			File[] sublists = sublist[i].listFiles();// 图片集合
			ArrayList<File> imgs = new ArrayList<File>();
			if(sublists == null) {
				System.out.println("######"+eventCode);
			}
			if(sublists != null) {
				for (File f : sublists) {
					if (f.getName().contains(".jpg") || f.getName().contains(".png") || f.getName().contains(".JPG")) {
						imgs.add(f);
					}
				}
			}
			// 上传所有图片
			List<UploadFile> uploadFileList = fileUploadService.save(imgs.toArray(new File[0]));

			List<Double> lnglat = null;
			if(sublists != null) {
			outer: for (int j = 0; j < sublists.length; j++) {
				try {
					if (sublists[j].getName().contains(".jpg") || sublists[j].getName().contains(".png") || sublists[j].getName().contains(".JPG")) {
						lnglat = printImageTags(sublists[j]);
					} else {
						continue;
					}
				} catch (Exception e) {
					System.err.println(eventCode + "," + sublists[j].getName());
					e.printStackTrace();
				}
				if (lnglat.size() > 1) { // 拿到坐标了，结束循环 //插入预警
					EarlyWarning ew = new EarlyWarning();
					ew.setLongitude(lnglat.get(0));
					ew.setLatitude(lnglat.get(1));
					ew.setAddress(adress);
					warningMapper.insert(ew); // 插入预警图片关联
					addFileBus(uploadFileList, ew.getUuid(), "warning_img"); // 插入地址库
					AddressBase addressBase = new AddressBase();
					addressBase.setAddress(adress);
					addressBase.setAtype("p_building");
					addressBase.setProvince("440000000000");
					addressBase.setCity("440300000000");
					addressBase.setDistrict("440306000000");
					addressBase.setCenter(lnglat.get(0) + "," + lnglat.get(1));
					addressBaseService.saveOrUpdate(addressBase); // 插入建筑
					Building building = new Building();
					building.setAbsId(addressBase.getUuid());
					//building.setName("燕罗违建" + eventCode);
					building.setName("深大测试违建" + eventCode);
					buildingMapper.insert(building);
					// 插入建筑图片关联
					addFileBus(uploadFileList, building.getUuid(), "building_album");
					// 插入建筑部门关联表
					DeptGrid deptGrid = new DeptGrid();
					deptGrid.setGridId(building.getUuid());
					deptGrid.setDeptId("e9df603441af43bf9370443cf2205a52");
					deptGridMapper.insert(deptGrid); // 插入事件
					EventMain eventMain = new EventMain();
					eventMain.setCode(eventCode);
					eventMain.setBuildingId(building.getUuid());
					eventMain.setSource("1");
					eventMain.setWarningId(ew.getUuid());
					eventMain.setStatus("1");
					eventMain.setLinkNow("1");
					eventMain.setDeptId("e9df603441af43bf9370443cf2205a52");// 宝安区
					java.awt.geom.Point2D.Double point = PointUtil.toPoint(lnglat.get(0), lnglat.get(1));
					//绑定小区
					for(Map<String, String> map : allPolyLine) {
						String vid = map.get("uuid");
						String lineString = map.get("polyline");
						List<java.awt.geom.Point2D.Double> pointList = PointUtil.toPointList(lineString);
						if(PointUtil.IsPtInPoly(point, pointList)) {
							eventMain.setVillageId(vid);
							break;
						}
					}
					eventMapper.insert(eventMain);
					break outer;
				}
			}
		}

		}
	}

	public void addFileBus(List<UploadFile> uploadFileList, String busId, String module) {
		for (int i = 0; i < uploadFileList.size(); i++) {
			FileBus fileBus = new FileBus();
			fileBus.setFileId(uploadFileList.get(i).getUuid());
			fileBus.setBusId(busId);
			fileBus.setModule(module);
			fileBus.setSort(i + 1);
			fileBusService.save(fileBus);
		}
	}

	/**
	 * 读取照片里面的信息
	 */
	public List<Double> printImageTags(File file) throws ImageProcessingException, Exception {
		Metadata metadata = ImageMetadataReader.readMetadata(file);
		String result = "";
		List<Double> lists = new ArrayList();
		for (Directory directory : metadata.getDirectories()) {
			for (Tag tag : directory.getTags()) {
				String tagName = tag.getTagName(); // 标签名
				String desc = tag.getDescription();
				// 标签信息
				/*
				 * if (tagName.equals("Image Height")) { System.out.println("图片高度: "+desc); }
				 * else if (tagName.equals("Image Width")) { System.out.println("图片宽度: "+desc);
				 * } else if (tagName.equals("Date/Time Original")) {
				 * System.out.println("拍摄时间: "+desc); }else
				 */if (tagName.equals("GPS Longitude")) {
					// System.err.println("纬度 : "+desc);
					// System.err.println("纬度(度分秒格式) : "+pointToLatlong(desc));
					lists.add(pointToLatlong(desc));
				} else if (tagName.equals("GPS Latitude")) {
					// System.err.println("经度: "+desc);
					// System.err.println("经度(度分秒格式): "+pointToLatlong(desc));
				lists.add(pointToLatlong(desc));
				}
			}
		}
		if (!lists.isEmpty()) {
			Collections.reverse(lists);
			double[] gd = GPSUtil.gps84_To_Gcj02(lists.get(1), lists.get(0));
			lists.set(0, gd[1]);
			lists.set(1, gd[0]);
		}

		return lists;
	
	}

	/**
	 * 经纬度格式 转换为 度分秒格式 ,如果需要的话可以调用该方法进行转换
	 * 
	 * @param point 坐标点
	 * @return
	 */
	public Double pointToLatlong(String point) {
		Double du = Double.parseDouble(point.substring(0, point.indexOf("°")).trim());
		Double fen = Double.parseDouble(point.substring(point.indexOf("°") + 1, point.indexOf("'")).trim());
		Double miao = Double.parseDouble(point.substring(point.indexOf("'") + 1, point.indexOf("\"")).trim());
		Double duStr = du + fen / 60 + miao / 60 / 60;
		// return String.format("%.6f", duStr);
		return duStr;
	}

	@Override
	public boolean saveArea(VoAreaPathParm vo) {
		FlyArea flyArea = vo.getFlyArea();
		//保存区域
		boolean bo = flyAreaService.save(flyArea);
		
		List<FlyPath> flyPathList = vo.getFlyPathList();
		List<OutputParamter> outputParamterList = vo.getOutputParamterList();
		for(int i= 0;i<flyPathList.size();i++) {
			//保存飞行路径
			FlyPath flyPath = flyPathList.get(i);
			flyPath.setAreaId(flyArea.getUuid());
			flyPathService.save(flyPath);
			//保存输出参数
			OutputParamter outputParamter = outputParamterList.get(i);
			outputParamter.setPathId(flyPath.getUuid());
			outparmService.save(outputParamter);
		}
		
		return bo;
	}

	@Override
	public boolean saveConfig(VoPathPlan vo) {
		
		boolean bo = flyConfigService.save(vo.getFlyConfig());
		bo = cameraConfigService.save(vo.getCameraConfig());
		return bo;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean deleteAll(Map<String, Object> param) {
		
		String rangeId = (String) param.get("rangId");
		
		
		
		//查出范围下所有区域
		QueryWrapper<FlyArea> flyAreaQueryWrapper = new QueryWrapper<FlyArea>();
		flyAreaQueryWrapper.eq("rang_id", rangeId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<FlyArea> flyAreaList = flyAreaService.list(flyAreaQueryWrapper);
		
		//删除范围下所有的区域
		UpdateWrapper<FlyArea> flyAreaWrapper = new UpdateWrapper<FlyArea>();
		flyAreaWrapper.eq("rang_id", rangeId);
		flyAreaWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		boolean bo = flyAreaService.update(flyAreaWrapper);
		
		
		for (FlyArea flyArea : flyAreaList) {
			//查出每个区域所相对应的路径
			QueryWrapper<FlyPath> flyPathQueryWrapper = new QueryWrapper<FlyPath>();
			flyPathQueryWrapper.eq("area_id", flyArea.getUuid()).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
			List<FlyPath> flyPathList = flyPathService.list(flyPathQueryWrapper);
			
			//删除路径所相对应的输出参数
			for (FlyPath flyPath : flyPathList) {
				UpdateWrapper<OutputParamter> outPutWrapper = new UpdateWrapper<OutputParamter>();
				outPutWrapper.eq("path_id", flyPath.getUuid());
				outPutWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
				outparmService.update(outPutWrapper);
			}
			
			//删除区域所相对应的路径
			UpdateWrapper<FlyPath> flyPathWrapper = new UpdateWrapper<FlyPath>();
			flyPathWrapper.eq("area_id", flyArea.getUuid());
			flyPathWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
			flyPathService.update(flyPathWrapper);
		}
		
		
		//删除飞行参数 
		UpdateWrapper<FlyConfig> flyConfigWrapper = new UpdateWrapper<FlyConfig>();
		flyConfigWrapper.eq("area_id", rangeId);
		flyConfigWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		flyConfigService.update(flyConfigWrapper);
		//删除相机参数
		UpdateWrapper<CameraConfig> cameraConfigWrapper = new UpdateWrapper<CameraConfig>();
		cameraConfigWrapper.eq("area_id", rangeId);
		cameraConfigWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		cameraConfigService.update(cameraConfigWrapper);
		
		return bo;
	}
	
}
