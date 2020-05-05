package com.chinobot.plep.home.point.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.HttpUtils;
import com.chinobot.common.utils.SFTPHelper;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.common.utils.ZipUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.service.IFlyAreaService;
import com.chinobot.plep.home.point.entity.FixedFlyPoint;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.RoutePoint;
import com.chinobot.plep.home.point.entity.ScenePoint;
import com.chinobot.plep.home.point.entity.dto.FileBusDto;
import com.chinobot.plep.home.point.entity.vo.FlyPointFileVo;
import com.chinobot.plep.home.point.entity.vo.FlyPointVo;
import com.chinobot.plep.home.point.entity.vo.FixedPointVo;
import com.chinobot.plep.home.point.entity.vo.LnglatVo;
import com.chinobot.plep.home.point.entity.vo.VoPldr;
import com.chinobot.plep.home.point.entity.vo.VoPoint;
import com.chinobot.plep.home.point.entity.vo.VoRoute;
import com.chinobot.plep.home.point.mapper.FixedFlyPointMapper;
import com.chinobot.plep.home.point.mapper.FixedPointMapper;
import com.chinobot.plep.home.point.service.IFixedFlyPointService;
import com.chinobot.plep.home.point.service.IFixedPointService;
import com.chinobot.plep.home.point.service.IRoutePointService;
import com.chinobot.plep.home.point.service.IScenePointService;
import com.chinobot.plep.home.route.entity.CheckPoint;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.service.ICheckPointService;
import com.chinobot.plep.home.route.service.IRouteService;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

/**
 * <p>
 * 定点场景关系表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-08-22
 */
@Service
public class FixedPointServiceImpl extends BaseService<FixedPointMapper, FixedPoint> implements IFixedPointService {
	@Autowired
	private FixedPointMapper fixedPointMapper;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IScenePointService scenePointService;
	@Autowired
	private ICheckPointService checkPointService;
	@Autowired
	private IRouteService routeService;
	@Autowired
	private IRoutePointService routePointService;
	@Autowired
	private IFlyAreaService flyAreaService;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Value("${config.pointTempPath}")
	private String tempPath;
	
	@Value("${config.labelMePath}")
	private String labelMePath;

	@Value("${config.labelMeIp}")
	private String labelMeIp;

	@Value("${config.labelMeUser}")
	private String labelMeUser;

	@Value("${config.labelMePwd}")
	private String labelMePwd;
	
	@Value("${config.tempPath}")
	private String tempPaths;
	
	@Autowired
	private FixedFlyPointMapper fixedFlyPointMapper;
	@Autowired
	private IFixedFlyPointService fixedFlyPointService;
	@Override
	public List<FixedPointVo> getAllPoint(Map<String, String> param) {
		return fixedPointMapper.getAllPoint(param);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void edit(VoPoint vo) {
		FixedPoint point = vo.getPoint();
		List<ScenePoint> scenes = vo.getScenes();
		//部门
		point.setDeptId(ThreadLocalUtil.getResources().getDept().getUuid());
		//冗余航点数据
		List<FixedFlyPoint> flypoints = vo.getFlypoints();
		point.setCenter(flypoints.get(0).getCenter());
		this.saveOrUpdate(point);
		//删除航点
		FixedFlyPoint inValidFlyPoint = new FixedFlyPoint();
		inValidFlyPoint.setDataStatus("0");
		fixedFlyPointService.update(inValidFlyPoint, CommonUtils.getEqUpdateWrapper("data_status", "fixed_id", "1", point.getUuid()));
		//新增航点
		for (int i = 0; i < flypoints.size(); i++) {
			FixedFlyPoint flyPoint = flypoints.get(i);
			flyPoint.setUuid(null);
			flyPoint.setSort(i+1);
			flyPoint.setFixedId(point.getUuid());
			flyPoint.setCreateBy(null);
			flyPoint.setCreateTime(null);
			flyPoint.setName("航点"+ (i+1));
			fixedFlyPointService.save(flyPoint);
		}
		//删除场景点关联
		QueryWrapper<ScenePoint> spWrapper = new QueryWrapper<ScenePoint>();
		spWrapper.eq("point_id", point.getUuid());
		scenePointService.remove(spWrapper);
		//保存场景关联
		for(ScenePoint scene : scenes) {
			scene.setPointId(point.getUuid());
			scenePointService.saveOrUpdate(scene);
		}
		// 网格文件关联
		List<FileBus> fileBus = vo.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", point.getUuid());
		queryWrapper.eq("module", "point_album");
		// 清空文件关联
		fileBusService.remove(queryWrapper);
		if (0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i).setBusId(point.getUuid()).setSort(i + 1).setModule("point_album");
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void del(FixedPoint sp) {
		sp.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		this.updateById(sp);
		//删除场景点关联
		QueryWrapper<ScenePoint> spWrapper = new QueryWrapper<ScenePoint>();
		spWrapper.eq("point_id", sp.getUuid());
		scenePointService.remove(spWrapper);
		//删除航点
		FixedFlyPoint inValidFlyPoint = new FixedFlyPoint();
		inValidFlyPoint.setDataStatus("0");
		fixedFlyPointService.update(inValidFlyPoint, CommonUtils.getEqUpdateWrapper("data_status", "fixed_id", "1", sp.getUuid()));
		//判断是否属于航线
		List<RoutePoint> rps = routePointService.list(CommonUtils.getEqQueryWrapper("data_status","point_id","1", sp.getUuid()));
		if(!rps.isEmpty()) {
			RoutePoint rp = rps.get(0);
			//属于 航线，重新计算航线，更新航线
			//TODO 太过复杂费时间，暂时不更新航线时间，但航线路径已改变，不影响飞行Route route = routeService.getById(rp.getRouteId());
			//删除routePoint
			rp.setDataStatus("0");
			routePointService.updateById(rp);
		}
		
		
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public String saveRoute(VoRoute vo) {
		CheckPoint checkPoint = vo.getCheckPoint();
		Route route = vo.getRoute();
		List<RoutePoint> line = vo.getLine();
		String deptId = ThreadLocalUtil.getResources().getDept().getUuid();
		if(checkPoint != null && route != null && line != null && StringUtils.isBlank(route.getCheckPointId())) {
			//新增起飞点新增路线
			checkPoint.setDeptId(deptId);
			checkPointService.save(checkPoint);
			route.setCheckPointId(checkPoint.getUuid());
			route.setDeptId(deptId);
			routeService.save(route);
			saveLine(line,route.getUuid());
			return route.getUuid()+","+checkPoint.getUuid();
		}
		if(checkPoint != null && route != null && line != null && StringUtils.isNotBlank(route.getCheckPointId())) {
			//原有起飞点新增路线且修改起飞点信息
			checkPointService.updateById(checkPoint);
			route.setDeptId(deptId);
			routeService.save(route);
			saveLine(line,route.getUuid());
			return route.getUuid()+","+route.getCheckPointId();
		}
		if(checkPoint == null && route != null && line != null) {
			//原有起飞点新增路线
			route.setDeptId(deptId);
			routeService.save(route);
			saveLine(line,route.getUuid());
			return route.getUuid()+","+route.getCheckPointId();
		}
		if(checkPoint == null && route != null && line == null) {
			//修改路线名称
			routeService.updateById(route);
		}
		if(checkPoint != null && route != null && line == null) {
			//修改路线及起飞点名称
			routeService.updateById(route);
			checkPointService.updateById(checkPoint);
		}
		if(checkPoint != null && route == null && line == null) {
			//修改起飞点名称地址
			checkPointService.updateById(checkPoint);
		}
		return null;
	}
	
	void saveLine(List<RoutePoint> line, String routeId) {
		for(RoutePoint rb : line) {
			rb.setRouteId(routeId);
			routePointService.save(rb);
		}
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delRoute(Route route) {
		route = routeService.getById(route.getUuid());
		route.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		routeService.updateById(route);
		//删除路线建筑关联
		QueryWrapper<RoutePoint> queryWrapper = new QueryWrapper<RoutePoint>();
		queryWrapper.eq("route_id", route.getUuid());
		routePointService.remove(queryWrapper);
		//判断是否需要删除起飞点
		QueryWrapper<Route> routerapper = new QueryWrapper<Route>();
		routerapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		routerapper.eq("check_point_id", route.getCheckPointId());
		int count = routeService.count(routerapper);
		QueryWrapper<FlyArea> areaWrapper = new QueryWrapper<FlyArea>();
		areaWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		areaWrapper.eq("point_id", route.getCheckPointId());
		int areacount = flyAreaService.count(areaWrapper);
		if(count == 0 && areacount == 0) {
			CheckPoint p = new CheckPoint();
			p.setUuid(route.getCheckPointId());
			p.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
			checkPointService.updateById(p);
		}
		
	}

	@Override
	public List<Map> getAllRouteLine(Map<String, String> param) {
		return fixedPointMapper.getAllRouteLine(param);
	}

	@Override
	public List<Map> getRouteLine(String uuid) {
		return fixedPointMapper.getRouteLine(uuid);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Map doPldr(VoPldr vo) throws Exception {
		Map result = new HashMap();
		List<String> errorMsg = new ArrayList<String>();//取不到经纬度的定点名称
		List<String> closeMsg = new ArrayList<String>();//航点过近的定点名称
		boolean allSuccess = true;
		String assignId = vo.getAssignId();
		List<ScenePoint> scenes = vo.getScenes();
		String deptId = ThreadLocalUtil.getResources().getDeptId();
		for(String fid : vo.getFileIds()) {
		UploadFile uploadFile = uploadFileService.getById(fid);
		InputStream inputStream = FileUtil.byte2Input(fastDFSClient.downloadFile(uploadFile.getPath()));
		//String temPath = "D:\\red_ant_file\\";//TODO 临时文件夹，用于存放解压后的文件
		ZipUtil.deleteDir(tempPath);//删除临时文件夹下的所有文件
		boolean bo = ZipUtil.decompressZip(inputStream, tempPath);
		System.out.println(bo);
		if(bo) {
			//List list = ZipUtil.traverseFolder(temPath);//获取文件夹下的所有文件
			File tmpFile = new File(tempPath);
			FileUtil.createDir(tempPath);
			for (File file2 : tmpFile.listFiles()) {
				String sqName = file2.getName();//社区名称，暂时用不到
				System.out.println("社区："+sqName);
				File[] codes = file2.listFiles();//事件编号集合
				for(File code : codes) {
					String codeName = code.getName();//事件编号，为跟踪点名称
					System.out.println("事件："+codeName);
					File[] imgs = code.listFiles();
					//图片排序
					Arrays.sort(imgs,  new Comparator<File>() {
						@Override
						public int compare(File o1, File o2) {
							return o1.getName().compareTo(o2.getName());
						}
					});
					FixedPoint point = null;
					List<UploadFile> uploadFileList = new ArrayList<UploadFile>();
					int sort = 1;
					if(checkNoClose(imgs)) {//是否没有航点过近
						for(File f : imgs) {
							//判断是否是图片
							if (f.getName().contains(".jpg") || f.getName().contains(".png") || f.getName().contains(".JPG")) {
								InputStream fileInputStream = new FileInputStream(f);
								List<Object> imageTags = printImageTags(fileInputStream);
								fileInputStream.close();
								if(imageTags.size() > 2) {
									//获取到了图片的GPS
									UploadFile uploadImgFile = uploadFileService.save(f);
									uploadFileList.add(uploadImgFile);
									if(point == null) {//定点信息保存
										point = new FixedPoint();
										point.setAddress(HttpUtils.lnglatToAddress(imageTags.get(0).toString(), imageTags.get(1).toString()));
										point.setAssignId(assignId);
										point.setCenter(imageTags.get(0) + "," + imageTags.get(1));
										point.setDeptId(deptId);
										point.setName(codeName);
										this.save(point);
										for(ScenePoint sp : scenes) {
											sp.setPointId(point.getUuid());
											scenePointService.save(sp);
											sp.setUuid(null);
										}
									}
									//航点信息保存
									FixedFlyPoint ffp = new FixedFlyPoint();
									ffp.setCenter(imageTags.get(0) + "," + imageTags.get(1));
									ffp.setHeight((Float)imageTags.get(2));
									if(0 == ffp.getHeight()) {
										ffp.setHeight(90F);
									}
									
									ffp.setName("航点" + sort);
									ffp.setSort(sort);
									ffp.setFileId(uploadImgFile.getUuid());
									ffp.setFixedId(point.getUuid());
									ffp.setCameraAngle(-90F);
									sort++;
									fixedFlyPointService.save(ffp);
								}
								
							}
						}
						if(point == null) {
							//失败上传
							allSuccess = false;
							errorMsg.add(codeName);
						}else {
							addFileBus(uploadFileList, point.getUuid(), "point_album");//上传相册
						}
					}else {
						//航点过近
						allSuccess = false;
						closeMsg.add(codeName);
					}
					
				}
			}
			
		}
		inputStream.close();
		ZipUtil.deleteDir(tempPath);//删除临时文件夹下的所有文件
		}
		
		if(allSuccess) {
			result.put("code", 200);
		}else {
			result.put("code", 500);
			result.put("errorMsg",errorMsg);
			result.put("closeMsg",closeMsg);
		}
		return result;
	}
	
	private boolean checkNoClose(File[] imgs) throws Exception {
		List<Object> pre = null;
		for(File f : imgs) {
			//判断是否是图片
			if (f.getName().contains(".jpg") || f.getName().contains(".png") || f.getName().contains(".JPG")) {
				InputStream fileInputStream = new FileInputStream(f);
				List<Object> imageTags = printImageTags(fileInputStream);
				fileInputStream.close();
				if(imageTags.size() > 2) {
					if(pre != null) {
						if(pre.get(0) == imageTags.get(0) && pre.get(1) == imageTags.get(1)) {
							return false;//相邻航点相同经纬度
						}
						String distanceStr = GPSUtil.getDistance((Double)imageTags.get(1), (Double)imageTags.get(0), (Double)pre.get(1), (Double)pre.get(0));
						Integer distance = Integer.valueOf(distanceStr);
						if(distance < 3) {
							return false;//航点过近
						}
					}
					pre = imageTags;
				}
			}
		}
		return true;
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
	@Override
	public List<Double> printImageTags(File file) throws Exception {
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
	 * 读取照片里面的信息,加了高度
	 */
	public List<Object> printImageTags(InputStream inputStream) throws Exception {
		Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
		Float height = null;
		List<Object> lists = new ArrayList();
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
				}else if (tagName.equals("GPS Altitude") && StringUtils.isNotBlank(desc)) {
					desc = desc.replace(" metres", "");
					height = Float.parseFloat(desc);
				}
			}
		}
		if (!lists.isEmpty()) {
			Collections.reverse(lists);
			double[] gd = GPSUtil.gps84_To_Gcj02((Double)lists.get(1), (Double)lists.get(0));
			lists.set(0, gd[1]);
			lists.set(1, gd[0]);
		}
		if (height != null) {
			lists.add(height);
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
	public IPage<Map> getPointPage(Page page, Map<String, Object> param) {
		String str = (String) param.get("domain");
		if(str !=null && str!="") {
			if(str.indexOf(',')>0) {
				String[] split = str.split(",");
				param.put("domain", split);
			}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("domain", split);
			}
		}
		
		String str2 = (String) param.get("isHaveBasic");
		if(str2 !=null && str2!="") {
			if(str2.indexOf(',')>0) {
				String[] split = str2.split(",");
				param.put("isHaveBasic", split);
			}else {
				String[] split = new String[1];
				split[0] = str2;
				param.put("isHaveBasic", split);
			}
		}
		return fixedPointMapper.getPointPage(page, param);
	}

	@Override
	public List<Map> getFlyLinePointImg(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return fixedPointMapper.getFlyLinePointImg(param);
	}

	@Override
	public List<Map> getRangePoint(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return fixedPointMapper.getRangePoint(param);
	}

	@Override
	public Map getFixPointCount(Map<String, Object> param) {
		
		return fixedPointMapper.getFixPointCount(param);
	}

	@Override
	public Map getCountOfLine(Map<String, Object> param) {

		return fixedPointMapper.getCountOfLine(param);
	}

	@Override
	public Map getFixPointCounts(Map<String, Object> param) {
		
		return fixedPointMapper.getFixPointCounts(param);
	}

	@Override
	public List<Map> getPointInfo(Map<String,Object> pointMap) {
		
		return fixedPointMapper.getPointInfo(pointMap);
	}

	@Override
	public Map getFlyImgList(Page page, Map<String, Object> param) {
		//查询该定点下的所有航点
		String uuid = (String) param.get("uuid");
		List<String> flyPointList = fixedFlyPointMapper.getAllFlyPointByFixPointId(uuid);
		//定点下所有航点的飞行数据
		List<FlyPointFileVo> listMaps = new ArrayList<FlyPointFileVo>();
		long current = 1;
		long pages = 1;
		long size = 5;
		long total = 5;
		//查询每个航点下的所有飞行数据
		for (int i = 0; i < flyPointList.size(); i++) {
			param.put("busId", flyPointList.get(i));
			param.put("module", "fly_img");
			IPage<FlyPointVo> flyPointFileList =	fixedPointMapper.getFlyImgList(page, param);
			current = flyPointFileList.getCurrent();
			pages = flyPointFileList.getPages();
			size = flyPointFileList.getSize();
			total = flyPointFileList.getTotal();
			FlyPointFileVo vo = new FlyPointFileVo();
			vo.setSort(i + 1);
			vo.setFlyPointFileList(flyPointFileList.getRecords());
			vo.setTotal(flyPointFileList.getTotal());
			listMaps.add(vo);
		}
		
		Map mapVo = new HashMap();
		mapVo.put("current", current);
		mapVo.put("pages", pages);
		mapVo.put("size", size);
		mapVo.put("total", total);
		mapVo.put("arr", listMaps);
		return mapVo;
	}

	@Override
	public List<Map> getFlyImgToZip(Map<String, Object> param) {
		
		String uuid = (String) param.get("uuid");
		param.put("busId", uuid);
		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
		param.put("module", "fly_img");
		return fixedPointMapper.getFlyImgToZip(param);
	}

	@Override
	public List<Map> getFlyImgListNoPage(Map<String, Object> param) {
		
		return fixedPointMapper.getFlyImgListNoPage(param);
	}

	@Override
	public List<Map> getAllPointNoPage(Map<String, Object> param) {
		
		return fixedPointMapper.getAllPointNoPage(param);
	}

	@Override
	public boolean addPic(FileBusDto dto) {
		
		FileBus fileBus = new FileBus();
		BeanUtils.copyProperties(dto,fileBus);
		boolean bo = fileBusService.save(fileBus);
		
		
//		UploadFile uploadFile = uploadFileService.getById(dto.getFileId()); // 查询上传的文件
//		String fileName = uploadFile.getUuid()+".jpg";
//		String destPath = tempPaths + File.separator + CommonUtils.getUUID();
//		File dir = FileUtil.createDir(destPath);// 创建临时文件夹
//		String srcPath = destPath + File.separator + fileName;
//		File file = FileUtil.createFile(srcPath); // 创建临时文件
//		boolean download = fastDFSClient.downloadFile(uploadFile.getPath(), file);
//		if (download) { // 下载并解压
//			FileUtil.unzip(srcPath, destPath);
//		}
//
//		File[] imgs = dir.listFiles();
//		if(imgs.length>0) {
//			// 复制图片到labelme目录
//			new LabelMeThread(imgs[0].getAbsolutePath(), uploadFile.getCurrentName(), dto.getBusId(), dto.getFileId() + ".jpg").start();
//		}
		
		return bo;
	}
	
class LabelMeThread extends Thread{
		
		private String sourcePath;
		private String sourceName;
		private String pointId;
		private String targetName;
		
		public LabelMeThread(String sourcePath, String sourceName, String pointId, String targetName) {
			this.sourcePath = sourcePath;
			this.sourceName = sourceName;
			this.pointId = pointId;
			this.targetName = targetName;
		}
		
		@Override
		public void run() {
			try {
				SFTPHelper sftpHelper = new SFTPHelper(labelMeUser, labelMePwd, labelMeIp);
				if(sftpHelper.connection()) {
					String imgDir = labelMePath + File.separator + pointId + File.separator;
					sftpHelper.execCommand(new String[] { "mkdir " + imgDir, "mv " + labelMePath + File.separator + sourceName + " " + imgDir + targetName});
					sftpHelper.put(sourcePath, imgDir);
//					
//					String imgDir = labelMePath + "/" + pointId + "/";
//					sftpHelper.execCommand(new String[] { "mkdir " + imgDir, "mv " + labelMePath + "/" + sourceName + " " + imgDir + targetName});
//					sftpHelper.put(sourcePath, imgDir);
					sftpHelper.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void saveToLabel(FileBusDto dto) {
		
		UploadFile uploadFile = uploadFileService.getById(dto.getFileId()); // 查询上传的文件
		String fileName = uploadFile.getUuid()+".jpg";
		String destPath = tempPaths + File.separator + CommonUtils.getUUID();
		File dir = FileUtil.createDir(destPath);// 创建临时文件夹
		String srcPath = destPath + File.separator + fileName;
		File file = FileUtil.createFile(srcPath); // 创建临时文件
		boolean download = fastDFSClient.downloadFile(uploadFile.getPath(), file);
		if (download) { // 下载并解压
			FileUtil.unzip(srcPath, destPath);
		}

		File[] imgs = dir.listFiles();
		if(imgs.length>0) {
			// 复制图片到labelme目录
			new LabelMeThread(imgs[0].getAbsolutePath(), uploadFile.getCurrentName(), dto.getBusId(), dto.getFileId() + ".jpg").start();
		}
		
	}

	@Override
	public IPage<Map> getAllPoint(Page page, Map<String, String> param) {
		IPage<Map> ipage = fixedPointMapper.getAllPointBak(page, param);
		for(Map map : ipage.getRecords()) {
			Map imgParam = new HashMap();
			imgParam.put("busId", map.get("uuid"));
			imgParam.put("module", "point_album");
			List<Map> list = fileBusService.getFileIdByBusId(imgParam);
			if(list.size()>0) {
				map.put("fileId", list.get(0).get("fileId"));
			}
		}
		return ipage;
	}

	@Override
	public LnglatVo getGpsByImgId(String id) throws Exception {
		UploadFile uploadFile = uploadFileService.getById(id);
		InputStream inputStream = FileUtil.byte2Input(fastDFSClient.downloadFile(uploadFile.getPath()));
		List<Object> list = printImageTags(inputStream);
		inputStream.close();
		if(list.size() == 3) {
			LnglatVo vo = new LnglatVo();
			vo.setLng((Double) list.get(0));
			vo.setLat((Double) list.get(1));
			vo.setHeight((Float) list.get(2));
			return vo;
		}
		return null;
	}
}
