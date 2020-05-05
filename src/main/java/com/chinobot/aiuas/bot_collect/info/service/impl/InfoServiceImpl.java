package com.chinobot.aiuas.bot_collect.info.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chinobot.aiuas.bot_collect.base.entity.Geography;
import com.chinobot.aiuas.bot_collect.base.mapper.GeographyMapper;
import com.chinobot.aiuas.bot_collect.base.service.IGeographyService;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.aiuas.bot_collect.info.entity.Info;
import com.chinobot.aiuas.bot_collect.info.entity.InfoTag;
import com.chinobot.aiuas.bot_collect.info.entity.Tag;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectAndGeoDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectAndGeoWithStringDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectWithAddressDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.GeographyDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.InfoCheckDataFileDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.InfoCheckDataListDTO;
import com.chinobot.aiuas.bot_collect.info.entity.vo.CollectPolygon;
import com.chinobot.aiuas.bot_collect.info.entity.vo.GovAreaVo;
import com.chinobot.aiuas.bot_collect.info.mapper.CollectDataMapper;
import com.chinobot.aiuas.bot_collect.info.mapper.InfoMapper;
import com.chinobot.aiuas.bot_collect.info.service.IInfoService;
import com.chinobot.aiuas.bot_collect.info.service.IInfoTagService;
import com.chinobot.aiuas.bot_collect.info.service.ITagService;
import com.chinobot.aiuas.bot_collect.info.util.Tree;
import com.chinobot.aiuas.bot_collect.info.util.TreeUtil;
import com.chinobot.aiuas.bot_collect.info.util.kml.KmlPolygon;
import com.chinobot.aiuas.bot_collect.info.util.kml.KmlProperty;
import com.chinobot.aiuas.bot_collect.info.util.kml.PackageKmlUtil;
import com.chinobot.aiuas.bot_collect.info.util.kml.ParsingKmlUtil;
import com.chinobot.aiuas.bot_collect.task.entity.Domain;
import com.chinobot.aiuas.bot_collect.task.service.IDomainService;
import com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.AmapUtils;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.GPSUtil;
import com.chinobot.common.utils.GaodeUtils;
import com.chinobot.common.utils.GaodeUtils.Poi;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.area.constant.AreaConstant;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dmg.pmml.Array;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 采查对象 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class InfoServiceImpl extends BaseService<InfoMapper, Info> implements IInfoService {

	@Autowired
	private InfoMapper infoMapper;
	@Autowired
	private IInfoService infoService;
	@Autowired
	private IGeographyService geographyService;
	@Autowired
	private ITagService tagService;
	@Autowired
	private IInfoTagService infoTagService;
	@Autowired
	private GeographyMapper geographyMapper;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private CollectDataMapper collectDataMapper;
	@Autowired
	private IDomainService domainService;
	@Value("${config.tempPath}")
	private String tempPath;
	/**
	 * 地理信息业务类型
	 */
	private static final String BUSI_TYPE = "bot_collect_info";
	/**
	 * 采查对象文件业务类型
	 */
	private static final String COLLECT_BUS_TYPE = "collect_img";

	@Override
	public Tree<CollectObjectDTO> getTreeObject() {

		Map<String, Object> param = new HashMap<String, Object>();
		List<CollectObjectDTO> ObjectList = infoMapper.getTreeObject(param);

		List<Tree<CollectObjectDTO>> trees = new ArrayList<>();
		buildTrees(trees, ObjectList);
		Tree<CollectObjectDTO> root = new Tree<CollectObjectDTO>();

		List<Tree<CollectObjectDTO>> topNodes = TreeUtil.build(trees);
		root.setId("0");
		root.setParentUUid("");
		root.setTitle("采查对象");
		root.setType("3");
		root.setHasParent(false);
		root.setHasChildren(true);
		root.setChildren(topNodes);

		return root;
	}

	private void buildTrees(List<Tree<CollectObjectDTO>> trees, List<CollectObjectDTO> ObjectList) {
		ObjectList.forEach(object -> {
			Tree<CollectObjectDTO> tree = new Tree<>();
			tree.setId(object.getId());
			tree.setParentUUid(object.getParentUUid());
			tree.setTitle(object.getTitle());
			tree.setType(object.getType());
			tree.setLnglats(object.getLnglats());
			trees.add(tree);
		});
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addCollectObject(CollectObjectAndGeoDTO dto) {

		// 采查对象基本信息更新
		CollectObjectWithAddressDTO coAddress = dto.getCoAddress();
		Info info = new Info();
		BeanUtils.copyProperties(coAddress, info);

		infoService.saveOrUpdate(info);

		/**
		 * 这里的parentUuid不一定就是采查对象的父类，还有可能是该采查对象的领域主键---
		 * 造成这个原因就是获取树形采查对象的时候，将顶级采查对象的采查对象的父类当成是领域了，本应父类是空，不存在的 这个操作只需要子新增的时候进行，修改不需要
		 */
		if (StringUtils.isEmpty(coAddress.getUuid()) || "".equals(coAddress.getUuid())) {
			String parentUuid = info.getParentUuid();
			Info one = infoService.getById(parentUuid);
			if (CommonUtils.isObjEmpty(one)) {
				Domain domain = domainService.getById(parentUuid);
				info.setCollectCode(domain.getDomainCode() + "-" + info.getUuid());
				info.setParentUuid(null);
				info.setDomainUuid(parentUuid);
			} else {
				String collectCode = one.getCollectCode() + "-" + info.getUuid();
				info.setCollectCode(collectCode);
				info.setDomainUuid(one.getDomainUuid());
				info.setParentUuid(parentUuid);
			}
		}

		infoService.updateById(info);

		// 地理信息更新
		GeographyDTO geographyDto = dto.getGeography();
		Geography geography = packGeography(geographyDto, info.getUuid());
		geographyService.saveOrUpdate(geography);

		// 采查对象标签
		List<String> tagList = dto.getTagList();
		// 如果是修改采查对象，则先将以前的对象标签关联删除
		if (StringUtils.isNotEmpty(coAddress.getUuid())) {
			List<InfoTag> list = infoTagService
					.list(new LambdaQueryWrapper<InfoTag>().eq(InfoTag::getCollectUuid, coAddress.getUuid()));
			for (InfoTag infoTag : list) {
				infoTag.setIsDeleted(true);
				infoTagService.updateById(infoTag);
			}
		}
		// 新建采查对象标签关联
		if (tagList != null && tagList.size() > 0) {
			for (String tagName : tagList) {
				Tag tag = tagService.getOne(new LambdaQueryWrapper<Tag>().eq(Tag::getTagName, tagName));
				if (tag == null) {
					tag = new Tag();
					tag.setTagName(tagName);
					tagService.save(tag);
				}
				InfoTag infoTag = new InfoTag();
				infoTag.setCollectUuid(info.getUuid());
				infoTag.setTagId(tag.getUuid());
				infoTagService.save(infoTag);
			}
		}

		// 如果是修改，先清空采查文件关联
		if (StringUtils.isNotEmpty(coAddress.getUuid())) {
			List<FileBus> list = fileBusService
					.list(new LambdaQueryWrapper<FileBus>().eq(FileBus::getBusId, coAddress.getUuid()));
			for (FileBus fileBus : list) {
				fileBus.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
				fileBusService.updateById(fileBus);
			}
		}

		// 文件业务关联
		List<String> idList = dto.getIdList();
		if (idList != null && idList.size() > 0) {
			for (int i = 0; i < idList.size(); i++) {
				FileBus fileBus = new FileBus();
				fileBus.setBusId(info.getUuid());
				fileBus.setFileId(idList.get(i));
				fileBus.setModule(COLLECT_BUS_TYPE);
				fileBus.setSort(i + 1);
				fileBusService.save(fileBus);
			}
		}

	}

	/**
	 * 地理信息封装
	 * 
	 * @param geographyDto
	 * @return
	 */
	private Geography packGeography(GeographyDTO geographyDto, String busiId) {
		Geography geography = new Geography();
		if (StringUtils.isNotEmpty(geographyDto.getGeographyId())) {
			geography.setUuid(geographyDto.getGeographyId());
		} else {
			// 获取该业务类型最大的序号
			Long maxSort = geographyMapper.getMaxSort(BUSI_TYPE);
			Integer sort = (int) (maxSort + 1);
			geography.setSort(sort);
		}
		geography.setBusiId(busiId);
		geography.setBusiType(BUSI_TYPE);
		geography.setGeoType("polygon");
		geography.setLnglats(geographyDto.getLnglats());
		geography.setLat(geographyDto.getLat());
		geography.setLng(geographyDto.getLng());
		geography.setArea(geographyDto.getArea());
		return geography;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeCollectObject(String infoId) {

		// TODO有些条件下不允许删除
		// 删除采查对象
		Info info = new Info();
		info.setUuid(infoId);
		info.setIsDeleted(true);
		infoService.updateById(info);

		// 删除关联的地理信息
		geographyService.update(new LambdaUpdateWrapper<Geography>().eq(Geography::getBusiId, infoId)
				.eq(Geography::getIsDeleted, PartConstant.DATA_NO_DELETE)
				.set(Geography::getIsDeleted, PartConstant.DATA_YES_DELETE));

		// 删除关联的采查标签
		infoTagService.update(new LambdaUpdateWrapper<InfoTag>().eq(InfoTag::getCollectUuid, infoId)
				.eq(InfoTag::getIsDeleted, PartConstant.DATA_NO_DELETE)
				.set(InfoTag::getIsDeleted, PartConstant.DATA_YES_DELETE));

	}

	@Override
	public List<CollectObjectAndGeoDTO> kmlUpload(MultipartFile multipartFile) {

		File toFile = null;
		InputStream ins = null;
		try {
			ins = multipartFile.getInputStream();
			toFile = new File(multipartFile.getOriginalFilename());
			inputStreamToFile(ins, toFile);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ins.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// 解析kml文件
		KmlProperty kmlProperty;
		ParsingKmlUtil parsingKmlUtil = new ParsingKmlUtil();
		kmlProperty = parsingKmlUtil.parseKmlForJAK(toFile);
		assert kmlProperty != null;

		List<CollectObjectAndGeoDTO> list = new ArrayList<CollectObjectAndGeoDTO>();
		if (kmlProperty != null && kmlProperty.getKmlPolygons().size() > 0) {
			for (KmlPolygon k : kmlProperty.getKmlPolygons()) {
				CollectObjectAndGeoDTO dto = new CollectObjectAndGeoDTO();

				CollectObjectWithAddressDTO coAddress = new CollectObjectWithAddressDTO();
				coAddress.setOName(k.getName());
				

				List<Coordinate> points = k.getPoints();
				//算出中心坐标点
				Coordinate coordinate = GetCenterPointFromListOfCoordinates(points);
				double[] gps84_To_Gcj02 = GPSUtil.gps84_To_Gcj02(coordinate.getLongitude(),coordinate.getLatitude());
				double latitude = gps84_To_Gcj02[0];
				double longitude = gps84_To_Gcj02[1];
				//中心坐标点转化为地址
				String location = longitude + "," + latitude;
		    	String addressJson = AmapUtils.getAddressByGeocode(location);
		    	JSONObject addressObject = JSON.parseObject(addressJson);
		    	
				// 将集合点坐标变成字符串
				String lnglats = getLnglats(points);
				//坐标系转换
				String fomatLnglat = transformLnglat(lnglats);
				//高德多边形计算面积
				double area = getArea(fomatLnglat);
				GeographyDTO geography = new GeographyDTO();
				geography.setLnglats(fomatLnglat);
				geography.setLat(latitude);
				geography.setLng(longitude);
				geography.setArea(area);
				coAddress.setOAddress(addressObject.getJSONObject("regeocode").getString("formatted_address"));
				dto.setGeography(geography);
				dto.setCoAddress(coAddress);
				list.add(dto);

			}
		}

		return list;

	}
	
	/**
	 * 计算多边形的面积
	 * @return
	 */
	public double getArea(String lnglats) {
		
		String[] rings = lnglats.split(";");
		if(rings.length < 1) {
			return 0;
		}
		List<Poi> ring = new ArrayList<>();
		for (String str : rings) {
			String[] split = str.split(",");
			double[] gps84_To_Gcj02 = GPSUtil.gps84_To_Gcj02(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
			GaodeUtils.Poi po = new  Poi(gps84_To_Gcj02[1], gps84_To_Gcj02[0]);
			ring.add(po);
		}
		BigDecimal area = GaodeUtils.getArea(ring);
		
		return Double.parseDouble(String.format("%.2f",area));
	}
	

	/**
	 * 根据多边形算出中心坐标点
	 * @param coordinateList
	 * @return
	 */
	public Coordinate GetCenterPointFromListOfCoordinates(List<Coordinate> coordinateList)
	{
	    int total = coordinateList.size();
	    double X = 0, Y = 0, Z = 0;
	    for (Coordinate g : coordinateList)
	    {
	        double lat, lon, x, y, z;
	        lat = g.getLatitude() * Math.PI / 180;
	        lon = g.getLongitude() * Math.PI / 180;
	        x = Math.cos(lat) * Math.cos(lon);
	        y = Math.cos(lat) * Math.sin(lon);
	        z = Math.sin(lat);
	        X += x;
	        Y += y;
	        Z += z;
	    }
	    X = X / total;
	    Y = Y / total;
	    Z = Z / total;
	    double Lon = Math.atan2(Y, X);
	    double Hyp = Math.sqrt(X * X + Y * Y);
	    double Lat = Math.atan2(Z, Hyp);
	    return new Coordinate(Lat * 180 / Math.PI, Lon * 180 / Math.PI);
	}
	
	
	/**
	 * 坐标转换gc84 转火星坐标
	 * @param lnglats
	 * @param lnglatSystem
	 * @return
	 */
	private String transformLnglat(String lnglats) {
		String[] rings = lnglats.split(";");
		StringBuilder resultRings = new StringBuilder();
		for (String str : rings) {
			String[] split = str.split(",");
			double[] gps84_To_Gcj02 = GPSUtil.gps84_To_Gcj02(Double.parseDouble(split[1]), Double.parseDouble(split[0]));
			resultRings.append(gps84_To_Gcj02[1] + "," + gps84_To_Gcj02[0] +";");
		}
		String ls = resultRings.toString();
		ls = ls.substring(0, ls.length()-1);
		return ls;
	}
	
	
	private void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getLnglats(List<Coordinate> points) {

		StringBuilder lnglats = new StringBuilder();
		int size = points.size();
		for (int i = 0; i < size; i++) {

			double longitude = points.get(i).getLongitude();
			DecimalFormat df = new DecimalFormat("#.000000");
			String lon = df.format(longitude);
			double latitude = points.get(i).getLatitude();
			String lat = df.format(latitude);
			lnglats.append(lon + "," + lat + ";");

			// 当首尾不相等的时候，加一条数据使它相等
			if (i == size - 1
					&& (points.get(0).getLongitude() != longitude || points.get(0).getLatitude() != latitude)) {
				lnglats.append(
						df.format(points.get(0).getLongitude()) + "," + df.format(points.get(0).getLatitude()) + ";");
			}
		}

		String str = lnglats.toString();
		if (CommonUtils.isNotEmpty(str)) {
			// 去掉最后一个";"
			return str.substring(0, str.length() - 1);
		}
		return null;
	}

	@Override
	public List<CollectPolygon> checkedCollectObject(String id) {

		Info info = infoService.getById(id);

		String code = null;
		// 这一步操作是为了判断是领域主键还是采查对象主键
		if (info != null) {
			code = info.getCollectCode();
		} else if ("0".equals(id)) {
			code = "";
		} else {
			Domain domain = domainService.getById(id);
			code = domain.getDomainCode();
		}
		Map param = new HashMap();
		param.put("id", code);
		return infoMapper.checkedCollectObject(param);
	}

	@Override
	public CollectObjectAndGeoDTO getCollectObject(String infoId) {

		CollectObjectAndGeoDTO dto = new CollectObjectAndGeoDTO();
		CollectObjectAndGeoWithStringDTO collectObject = infoMapper.getCollectObject(infoId);
		dto.setCoAddress(collectObject.getCoAddress());
		dto.setGeography(collectObject.getGeography());
		List<String> flieIdList = stringToList(collectObject.getFileIds());
		dto.setIdList(flieIdList);
		List<String> tagNameList = stringToList(collectObject.getTagNames());
		dto.setTagList(tagNameList);
		dto.setParentLnglats(collectObject.getParentLnglats());

		return dto;
	}

	/**
	 * 字符串变list集合
	 * 
	 * @param strs
	 * @return
	 */
	private List<String> stringToList(String strs) {

		List<String> list = new ArrayList<String>();
		if (strs == null || strs.length() <= 0) {
			return list;
		}

		if (strs.indexOf(",") > 0) {
			String[] split = strs.split(",");
			for (int i = 0; i < split.length; i++) {
				list.add(split[i]);
			}
		} else {
			list.add(strs);
		}

		return list;
	}

	@Override
	public String exportKml(String id, HttpServletResponse resp) {

		File mkFile = new File(tempPath);
		if (!mkFile.exists()) {// 如果文件夹不存在
			mkFile.mkdir();// 创建文件夹
		}

		// 获取标题
		String name = getTitle(id);
		String path = tempPath + File.separator + name + ".kml";
		List<CollectPolygon> collectPolygonList = infoService.checkedCollectObject(id);
		try {
			PackageKmlUtil.packKml(collectPolygonList, name, path);
			File file = new File(path);
			byte[] bytes = PackageKmlUtil.File2byte(file);
			ServletOutputStream out = null;
			try {
				resp.reset();// 清空输出流
				resp.setContentType("application/vnd.android.package-archive;");
				resp.setHeader("Content-Disposition",
						"attachment;filename=" + new String(name.getBytes("gb2312"), "ISO8859-1") + ".kml");
				resp.setContentLength(bytes.length);

				out = resp.getOutputStream();
				out.write(bytes);
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
				}
//                FileUtil.delFile(new File(tempPath));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getTitle(String id) {

		String name = "";
		Domain domain = domainService.getById(id);
		if (domain != null) {
			name = domain.getDName();
		} else {
			Info info = infoService.getById(id);
			if (info != null) {
				name = info.getOName();
			} else {
				name = "采查对象";
			}
		}

		return name;
	}

	@Override
	public List<GovAreaVo> getCode() {

		return infoMapper.getCode();
	}

	@Override
	public List<Info> getCollect(String infoId) {

		return null;
	}

	@Override
	public List<CollectObjectDTO> searchCollect(String infoName) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("infoName", infoName);
		List<CollectObjectDTO> ObjectList = infoMapper.getTreeObject(param);
		return ObjectList;
	}

	@Override
	public List<Map<String, String>> getTags() {

		return infoMapper.getTags();
	}

	/**
	 * 
	 */
	@Override
	public Map<String, Object> searchInfoCheckData(String infoId) {
		Map<String, Object> infoCheckData = new HashMap<String, Object>();

		// 查询采查对象信息，采查对象名称，地址
		Info infoData = this.getById(infoId);
		infoCheckData.put("infoData", infoData);

		// 查询采查对象的图片视频
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("infoId", infoId);

		List<InfoCheckDataListDTO> checkDataListDTOs = collectDataMapper.searchInfoCheckDataDTO(paraMap);
		for (InfoCheckDataListDTO infoCheckDataListDTO : checkDataListDTOs) {
			List<InfoCheckDataFileDTO> checkDataFileDTOs = infoCheckDataListDTO.getFileList();
			if (checkDataFileDTOs.size() > 0) {
				for (InfoCheckDataFileDTO infoCheckDataFileDTO : checkDataFileDTOs) {
					if (infoCheckDataFileDTO.getFile_type().equals(GlobalConstant.FILE_TYPE_IMAGE)) {
						infoCheckDataListDTO.setImgList(infoCheckDataFileDTO.getFileIdList());
					} else if(infoCheckDataFileDTO.getFile_type().equals(GlobalConstant.FILE_TYPE_VEDIO)){
						infoCheckDataListDTO.setVedioList(infoCheckDataFileDTO.getFileIdList());
					}
				}
			}

		}
		infoCheckData.put("infoCheckInfo", checkDataListDTOs);
		//查询采查图片数量
		paraMap.put("type", GlobalConstant.FILE_TYPE_IMAGE);
		List<Map> imgeList = collectDataMapper.searchInfoCheckData(paraMap);
		infoCheckData.put("imageCount", imgeList.size());

		// 查询采查视频数量
		paraMap.put("type", GlobalConstant.FILE_TYPE_VEDIO);
		List<Map> videoList = collectDataMapper.searchInfoCheckData(paraMap);
		infoCheckData.put("vedioCount", videoList.size());

		return infoCheckData;
	}

}
