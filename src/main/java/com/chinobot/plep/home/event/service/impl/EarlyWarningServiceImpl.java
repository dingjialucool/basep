package com.chinobot.plep.home.event.service.impl;

import com.chinobot.plep.home.building.mapper.VillageMapper;
import com.chinobot.plep.home.event.entity.EarlyWarning;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.mapper.EarlyWarningMapper;
import com.chinobot.plep.home.event.mapper.EventMainMapper;
import com.chinobot.plep.home.event.service.IEarlyWarningService;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.drew.imaging.ImageMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.HttpUtils;
import com.chinobot.common.utils.PointUtil;
import com.chinobot.framework.web.service.impl.BaseService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 风险预警表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-06-26
 */
@Service
public class EarlyWarningServiceImpl extends BaseService<EarlyWarningMapper, EarlyWarning> implements IEarlyWarningService {
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private FastDFSClient fastDFSClient;
	@Autowired
	private EarlyWarningMapper warningMapper;
	@Autowired
	private VillageMapper VillageMapper;
	@Autowired
	private IEventMainService eventMainService;
	@Autowired
	private EventMainMapper eventMainMapper;
	@Override
	public List<Map> uploadEarlyWarning(List<FileBus> fileBuss) throws Exception {
		List<Map> list = new ArrayList<Map>();
		for(int i=0; i<fileBuss.size();i++) {
			FileBus fileBus = fileBuss.get(i);
			UploadFile uploadFile = uploadFileService.getById(fileBus.getFileId());
			fileBus.setSort(1);
			fileBus.setModule("warning_img");
			String filePath = CommonUtils.objNotEmpty(uploadFile) ? uploadFile.getPath() : null;
			if(null != filePath) {
				InputStream inputStream = FileUtil.byte2Input(fastDFSClient.downloadFile(filePath));
				List<Double> lnglat = printImageTags(inputStream);
				if(lnglat.size()>1) {
					//预警信息
					EarlyWarning ew = new EarlyWarning();
					ew.setLongitude(lnglat.get(0));
					ew.setLatitude(lnglat.get(1));
					ew.setAddress(HttpUtils.lnglatToAddress(lnglat.get(0).toString(), lnglat.get(1).toString()));
					warningMapper.insert(ew);
					//关联预警图片
					fileBus.setBusId(ew.getUuid());
					fileBusService.save(fileBus);
					//事件
					//TODO 事件过滤
					EventMain event = new EventMain();
					event.setCode(UUID.randomUUID().toString().replaceAll("-", ""));
					event.setWarningId(ew.getUuid());
					event.setSource("1");
					event.setStatus("1");
					event.setLinkNow("1");
					event.setVillageId(getBindVillageId(lnglat.get(0), lnglat.get(1)));
					event.setDeptId(getBindDeptId(lnglat.get(0), lnglat.get(1)));
					eventMainService.save(event);
				}
			}
			if(fileBus.getUuid() == null) {
				Map map = new HashMap();
				map.put("name", uploadFile.getOriginName());
				map.put("url", "/aiuas/api/file/ioimage?fileId="+uploadFile.getUuid());
				list.add(map);
			}
		}
		return list;
	}

	
	/**
	 * 读取照片里面的信息
	 */
	@Override
	public List<Double> printImageTags(InputStream inputStream) throws Exception {
		Metadata metadata = ImageMetadataReader.readMetadata(inputStream);
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
	/**
	 * 获取经纬度绑定的小区ID
	 * @param lng
	 * @param lat
	 * @return
	 */
	@Override
	public String getBindVillageId(Double lng, Double lat) {
		java.awt.geom.Point2D.Double point = PointUtil.toPoint(lng, lat);
		List<Map<String, String>> allPolyLine = VillageMapper.getAllPolyLine();
		//绑定小区
		for(Map<String, String> map : allPolyLine) {
			String vid = map.get("uuid");
			String lineString = map.get("polyline");
			List<java.awt.geom.Point2D.Double> pointList = PointUtil.toPointList(lineString);
			if(PointUtil.IsPtInPoly(point, pointList)) {
				return vid;
			}
		}
		return null;
	}
	@Override
	public String getBindDeptId(Double lng, Double lat) throws Exception {
		java.awt.geom.Point2D.Double point = PointUtil.toPoint(lng, lat);
		String[] paths = {"map/龙华区.txt", "map/福田区.txt", "map/宝安区.txt","map/大鹏新区.txt", "map/光明区.txt", "map/龙岗区.txt", "map/罗湖区.txt", "map/南山区.txt", "map/坪山区.txt", "map/盐田区.txt"};
		for(String path : paths) {
			//List<Double> pointList = PointUtil.fileToPointList(path);
			List<List<java.awt.geom.Point2D.Double>> listList = PointUtil.fileToPointListList(path);
			for(List<java.awt.geom.Point2D.Double> list : listList) {
				if(PointUtil.IsPtInPoly(point, list)) {
					if(path.contains("龙华")) {
						return "3f62d70d2042fd7c6aab1293ee006c23";	
					}
					if(path.contains("福田")) {
						return "23b4327e0b82c3d2acbbd39f77450bf8";	
					}
					if(path.contains("宝安")) {
						return "e9df603441af43bf9370443cf2205a52";	
					}
					if(path.contains("大鹏新区")) {
						return "d7996d4e36e847cfb44b0f33047d63b3";	
					}
					if(path.contains("光明")) {
						return "f20fa8455fd3479fb0b188a0edeea176";	
					}
					if(path.contains("龙岗")) {
						return "160c411d50a4356f541bd886ac2f5251";	
					}
					if(path.contains("罗湖")) {
						return "19b3c82adf0cf4b73f39240d1f836c3d";	
					}
					if(path.contains("南山")) {
						return "a0ef451fa9ad129cc4b3d169a42a4c32";	
					}
					if(path.contains("坪山")) {
						return "7b10ce9abb034105abc8a1335d3c3e1d";	
					}
					if(path.contains("盐田")) {
						return "e9b82f7a161a647d238113d3ac63ac13";	
					}
				} 
			}
			
		}
		return null;
	}


	@Override
	public IPage<Map> getEvents(Page page, Map<String, Object> param) {
		if(param.get("bus_type")!=null && param.get("bus_type")!="" ) {
        	param = toParam(param,"bus_type");
        }
        if(param.get("scene")!=null && param.get("scene")!="" ) {
        	param = toParam(param,"scene");
        }
        if(param.get("domain")!=null && param.get("domain")!="" ) {
        	param = toParam(param,"domain");
        }
        IPage<Map> event = warningMapper.getEvent(page,param);
        List<Map> records = event.getRecords();
        for (Map map : records) {
			String emUuid = (String) map.get("uuid");
			String pointId = (String) map.get("point_id");
			String warningId = (String) map.get("warningId");
//			List<Map> warnImgList = eventMainMapper.getImgListByBusId(warningId, "warning_img");
//			List<Map> eventImgList = eventMainMapper.getImgListByBusId(emUuid, "event_img");
			List<Map> warnBasicList = eventMainMapper.getImgListByBusId(warningId, "basic_img");
			List<Map> evenBasicList = eventMainMapper.getImgListByBusId(pointId, "basic_img");
			
//			map.put("imgList", warnImgList.size()>0?warnImgList:eventImgList);//获取全部预警图片
			map.put("imgList", eventMainMapper.getImgListByBusId(emUuid, "event_img"));
			map.put("resetImg", eventMainMapper.getImgListByBusId(emUuid, "reset_img"));//获取全部确认图片
			map.put("basicImg", warnBasicList.size()>0?warnBasicList:evenBasicList);//获取全部基准图片
			map.put("vedioList", eventMainMapper.getImgListByBusId(emUuid, "fly_video"));//获取楼顶加建全部视频
        }
		return event.setRecords(records);
	}

	@Override
	public List<Map> getEvents2(Map<String, Object> param) {
		List<Map> event = warningMapper.getEvent2(param);

		return event;
	}

	private Map toParam(Map param, String type) {
		
    	String str = (String) param.get(type);
    	if(str.indexOf(',')>0) {
    		String[] split = str.split(",");
    		param.put(type, split);
    	}else {
			String[] split = new String[1];
			split[0] = str;
			param.put(type, split);
		}
		return param;
	}


	@Override
	public Map getDispath(Map<String, Object> param) {
		Map map = warningMapper.getDispath(param);
		if(map == null) {
			return null;
		}
		String emUuid = (String) map.get("uuid");
		String pointId = (String) map.get("point_id");
		String warningId = (String) map.get("warningId");
		List<Map> warnBasicList = eventMainMapper.getImgListByBusId(warningId, "basic_img");
		List<Map> evenBasicList = eventMainMapper.getImgListByBusId(pointId, "basic_img");
		
		map.put("imgList", eventMainMapper.getImgListByBusId(emUuid, "event_img"));//获取全部预警图片
		map.put("resetImg", eventMainMapper.getImgListByBusId(emUuid, "reset_img"));//获取全部确认图片
		map.put("basicImg", warnBasicList.size()>0?warnBasicList:evenBasicList);//获取全部基准图片
		map.put("vedioList", eventMainMapper.getImgListByBusId(emUuid, "fly_video"));//获取楼顶加建全部视频
		return map;
	}
}
