package com.chinobot.plep.home.algorithm.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.mapper.FileBusMapper;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.SFTPHelper;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.algorithm.controller.FlyDataController;
import com.chinobot.plep.home.algorithm.entity.Algorithm;
import com.chinobot.plep.home.algorithm.mapper.AlgorithmMapper;
import com.chinobot.plep.home.algorithm.service.IAlgorithmService;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.service.IFixedFlyPointService;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.mapper.DispatchDetailPathMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 算法表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-08-25
 */
@Slf4j
@Service
public class AlgorithmServiceImpl extends BaseService<AlgorithmMapper, Algorithm> implements IAlgorithmService {

	@Autowired
	private IFileBusService fileBusService;
	
	@Autowired
	private AlgorithmMapper algorithmMapper;

	@Autowired
	private FileBusMapper fileBusMapper;

	@Autowired
	private DispatchDetailPathMapper dispatchDetailPathMapper;
	
	@Autowired
	private IFixedFlyPointService fixedFlyPointService;

	@Autowired
	private FileClient fileClient;

	@Autowired
	private FastDFSClient fastDFSClient;
	
	@Value("${config.zipPath}")
	private String zipTemp;
//
//	@Value("${config.downloadUrl}")
//	private String downloadUrl;
//
//	@Value("${config.algorithmUrl}")
//	private String algorithmUrl;
//
//	@Value("${config.algorithmIP}")
//	private String algorithmIP;
//
//	@Value("${config.algorithmUser}")
//	private String algorithmUser;
//
//	@Value("${config.algorithmPwd}")
//	private String algorithmPwd;
//
//	@Value("${config.algorithmRoot}")
//	private String algorithmRoot;
//	
//	@Value("${config.labelMeXmlPath}")
//	private String labelMeXmlPath;
//	
//	@Value("${config.labelMeIp}")
//	private String labelMeIp;
//	
//	@Value("${config.labelMeUser}")
//	private String labelMeUser;
//	
//	@Value("${config.labelMePwd}")
//	private String labelMePwd;
//	
	@Override
	public IPage<Map> getAlgorithmList(Page page, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return algorithmMapper.getAlgorithmList(page, param);
	}

	@Override
	public void saveAlgorithm(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String uuid = param.get("uuid").toString();
		String domainType = param.get("domainType").toString();
		String sceneType = param.get("sceneType").toString();
		String operatingPlatform = param.get("operatingPlatform").toString();
		String fileId = param.get("fileId").toString();
		Algorithm at = new Algorithm();
		at.setName(param.get("name").toString());
		at.setAlgorithmVersion(param.get("ver").toString());
		if (param.containsKey("uuid") && !StringUtils.isEmpty(uuid)) { // 更新的
			at.setSceneId(param.get("sceneId").toString());
			if(StringUtils.isEmpty(fileId)) {//说明算法没更新，用上一个版本的算法
				param.put("busId", uuid);
				List<Map> list = fileBusMapper.getFileIdByBusId(param);
				if(list.size() > 0) {
					fileId = list.get(0).get("fileId").toString();
				}
			}
		} else { // 新添加的算法
			Map scene = algorithmMapper.getSceneByAlgorithm(param);
			at.setSceneId(scene.get("uuid").toString());
		}
		at.setDomainType(domainType);
		at.setSceneType(sceneType);
		at.setPlatformType(operatingPlatform);
		algorithmMapper.insert(at);

		String busId = at.getUuid();
		FileBus bus = new FileBus();
		bus.setBusId(busId);
		bus.setFileId(fileId);
		fileBusMapper.insert(bus);
		
		param.put("uuid", busId);
		algorithmMapper.stopAlgorithm(param);
	}

	@Override
	public void commitAlgorithm(Map<String, Object> param) {
		// TODO Auto-generated method stub
//		Algorithm algorithm = (Algorithm) algorithmMapper.selectById(String.valueOf(param.get("busId")));
//		if (algorithm == null) {
//			return;
//		}
//		List<Map> list = fileBusMapper.getFileIdByBusId(param);
//		if (list.size() > 0) {
//			Map map = list.get(0);
//			String path = String.valueOf(map.get("path"));
//			System.out.println("start download............................");
//			byte[] fileByte = fileClient.downloadFile(path);
//			new SftpThread(algorithm, fileByte, algorithmIP, algorithmUser, algorithmPwd, algorithmRoot).start();
//		}
	}

	@Override
	public List<Algorithm> getPathAlgorithmByTask(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return algorithmMapper.getPathAlgorithmByTask(param);
	}

	@Override
	public List<Algorithm> getRouterAlgorithmByTask(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return algorithmMapper.getRouterAlgorithmByTask(param);
	}
	
	/**
	 * 楼顶加建的算法
	 */
	@Override
	public void execAlgorithm(String flyModule, String taskId, Map<String, File> imgMap) {
//		try {
//			// TODO Auto-generated method stub
//			DispatchDetailPath ddp = (DispatchDetailPath) dispatchDetailPathMapper.selectById(taskId);
//			String pathId = ddp.getPathId();
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("busId", pathId);
//			String tmpPath = zipTemp + File.separator + CommonUtils.getUUID();
//			FileUtil.createDir(tmpPath);	//创建临时目录
//			List<File> srcFiles = new ArrayList<File>();
//			for(String fileId : imgMap.keySet()) {	//把图片重命名
//				File targetFile = new File(tmpPath + File.separator + fileId + "_N.jpg");
//				FileUtil.copyFile(imgMap.get(fileId), targetFile);
//				srcFiles.add(targetFile);
//			}
//			
//			//打包zip
//			File zipFile = new File(tmpPath + ".zip");
//			FileUtil.toZip(srcFiles, new FileOutputStream(zipFile));
//			
//			//上传到文件服务器
//			UploadFile uf = fileClient.uploadFile(zipFile);
//			
//			//根据任务id查找对应的算法
//			param.clear();
//			param.put("uuid", taskId);
//			//可能会有多个场景
//			List<Algorithm> algorithmList = getPathAlgorithmByTask(param);
//			for (Algorithm algorithm : algorithmList) {
//				SFTPHelper sftpHelper = new SFTPHelper(algorithmUser, algorithmPwd, algorithmIP);
//				if (sftpHelper.connection()) {
//					//执行算法脚本
//					String shellName = algorithm.getAlgorithmVersion() + "_" + algorithm.getDomainType() + "_" + algorithm.getSceneType() + "_" + algorithm.getPlatformType();
//					sftpHelper.execCommand(new String[] { "cd " + algorithmRoot + shellName + File.separator, "source ./" + shellName + ".sh " + downloadUrl + uf.getUuid() + " " + algorithmUrl + "/push_warning_addition " + pathId + "#" + taskId});
//					sftpHelper.close();
//				}
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
	}
	
	/**
	 * 存量变化的算法
	 */
	@Override
	public void execAlgorithm(String flyModule, Map<String, File> imgMap, Map<String, String> pointMap, String taskId) {
		// TODO Auto-generated method stub
//		try {
//			Map<String, Object> param = new HashMap<String, Object>();
//			for (String key : imgMap.keySet()) {
//				log.debug ("#huangw#进入循环key= {}", key);
//				String flyPointId = pointMap.get(key);
//				String pointId = fixedFlyPointService.getById(flyPointId).getFixedId();
//				String tmpPath = zipTemp + File.separator + CommonUtils.getUUID();
//				FileUtil.createDir(tmpPath);	//创建临时目录
//				
//				List<File> zipFile = new ArrayList<File>();
//				param.clear();
//				param.put("busId", flyPointId);
//				param.put("module", "basic_img");
//				List<Map> list = fileBusService.getFileIdByBusId(param);
//				log.debug ("#huangw#基准图数量= {}", list.size());
//				if(list.size() > 0) {
//					//下载基准图
//					String path = list.get(0).get("path").toString();
//					File bFile = FileUtil.createFile(tmpPath + File.separator + key + "_B.jpg");
//					fastDFSClient.downloadFile(path, bFile);
//					zipFile.add(bFile);
//					//下载xml文件
//					String xmlPath = labelMeXmlPath + File.separator + pointId + File.separator + list.get(0).get("fileId").toString() + ".xml";
//					String xmlTmpPath = tmpPath + File.separator + key + "_B.xml";
//					SFTPHelper sftpHelper = new SFTPHelper(labelMeUser, labelMePwd, labelMeIp);
//					if (sftpHelper.connection()) {
//						sftpHelper.get(xmlPath, xmlTmpPath);
//						File xmlFile = new File(xmlTmpPath);
//						zipFile.add(xmlFile);
//						sftpHelper.close();
//					}
//				}else {
//					continue ;
//				}
//				
//				//保存对比图
//				File targetFile = new File(tmpPath + File.separator + key + "_N.jpg");
//				FileUtil.copyFile(imgMap.get(key), targetFile);
//				zipFile.add(targetFile);
//				log.debug ("#huangw#即将开始压缩= {}", zipFile.toString());
//				//压缩上传到文件服务器
//				File ysFile = new File(tmpPath + ".zip");
//				FileUtil.toZip(zipFile, new FileOutputStream(ysFile));
//				
//				//上传到文件服务器
//				UploadFile uf = fileClient.uploadFile(ysFile);
//				log.debug ("#huangw#压缩文件id= {}", uf.getUuid());
//				//根据任务id查找对应的算法
//				param.clear();
//				param.put("uuid", pointId);
//				//可能会有多个场景
//				List<Algorithm> algorithmList = getRouterAlgorithmByPoint(param);
//				log.debug ("#huangw#找到的算法= {}", algorithmList.toString());
//				for (Algorithm algorithm : algorithmList) {
//					SFTPHelper sftpHelper = new SFTPHelper(algorithmUser, algorithmPwd, algorithmIP);
//					if (sftpHelper.connection()) {
//						//执行算法脚本
//						log.debug ("#huangw#要开始执行算法了，参数1= {},参数2= {},参数3= {}", downloadUrl + uf.getUuid(), algorithmUrl + "/push_warning_stock", pointId);
//						String shellName = algorithm.getAlgorithmVersion() + "_" + algorithm.getDomainType() + "_" + algorithm.getSceneType() + "_" + algorithm.getPlatformType();
//						sftpHelper.execCommand(new String[] { "cd " + algorithmRoot + shellName + File.separator, "source ./" + shellName + ".sh " + downloadUrl + uf.getUuid() + " " + algorithmUrl + "/push_warning_stock " + flyPointId + "#" + taskId});
//						sftpHelper.close();
//					}
//				}
//			}	
//		} catch (Exception e) {
//			// TODO: handle exception
//			log.debug ("#huangw#调用算法出错= {}", e.getMessage());
//			e.printStackTrace();
//		}
	}

	@Override
	public List<FixedPoint> getPointsByFlyId(String routeId) {
		return algorithmMapper.getPointsByFlyId(routeId);
	}

	@Override
	public List<Algorithm> getRouterAlgorithmByPoint(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return algorithmMapper.getRouterAlgorithmByPoint(param);
	}

}
