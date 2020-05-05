package com.chinobot.plep.home.algorithm.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FastDFSClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.FileUtil;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.SFTPHelper;
import com.chinobot.common.utils.ZipUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.framework.web.controller.KafkaProducerController;
import com.chinobot.plep.app.dispatch.entity.AppDispatchFile;
import com.chinobot.plep.app.dispatch.service.IAppDispatchFileService;
import com.chinobot.plep.home.algorithm.service.IAlgorithmService;
import com.chinobot.plep.home.point.entity.FixedFlyPoint;
import com.chinobot.plep.home.point.service.IFixedFlyPointService;
import com.chinobot.plep.home.point.service.IFixedPointService;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.service.IDispatchDetailPathService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailRouteService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 飞行数据 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-08-25
 */
@Api(tags= {"飞行数据接口"})
@Slf4j
@RestController
@RequestMapping("/api/flyData")
public class FlyDataController extends BaseController {

	@Autowired
	private IFileBusService fileBusService;

	@Autowired
	private IFixedPointService fixedPointService;

	@Autowired
	private IUploadFileService uploadFileService;

	@Autowired
	private IDispatchDetailService dispatchDetailService;

	@Autowired
	private IAppDispatchFileService appDispatchFileService;

	@Autowired
	private IDispatchDetailPathService dispatchDetailPathService;
	
	@Autowired
	private IDispatchDetailRouteService dispatchDetailRouteService;
	
	@Autowired
	private FastDFSClient fastDFSClient;
	
	@Autowired
	private IFixedFlyPointService fixedFlyPointService;

	@Autowired
	private IAlgorithmService algorithmService;
	
	@Value("${config.tempPath}")
	private String tempPath;

	@Value("${config.labelMePath}")
	private String labelMePath;

	@Value("${config.labelMeIp}")
	private String labelMeIp;

	@Value("${config.labelMeUser}")
	private String labelMeUser;

	@Value("${config.labelMePwd}")
	private String labelMePwd;


	@ApiOperation(value = "保存上传数据", notes = "参数-Map param")
	@PostMapping("/save")
//	@RequestMapping("/save")
	public Result saveFlyData(@RequestBody Map<String, Object> param) throws Exception {

		String busId = param.get("uuid").toString();
		String fileId = param.get("fileId").toString();
		String flyModule = param.get("flyModule").toString();
		//检查照片数量是否跟航点数量保持一致
		if (flyModule.equals("1")) {
			String rs = checkNum(busId, fileId);
			if(!"yes".equals(rs)) {
				return ResultFactory.error(rs);
			}
		}
		// 保存上传的文件信息
		FileBus fb = new FileBus();
		fb.setModule("fly_data");
		fb.setBusId(busId);
		fb.setFileId(fileId);
		fileBusService.save(fb);
		
		//设置状态为完成
		if (flyModule.equals("1")) {
			DispatchDetailRoute dispatchDetailRoute = dispatchDetailRouteService.getById(busId);
			dispatchDetailRoute.setStatus("3");
			dispatchDetailRouteService.updateById(dispatchDetailRoute);
		}else if (flyModule.equals("2") || flyModule.equals("3")) {
			DispatchDetailPath dispatchDetailPath = dispatchDetailPathService.getById(busId);
			dispatchDetailPath.setStatus("3");
			dispatchDetailPathService.updateById(dispatchDetailPath);
		}
		
		
		// 定点跟踪的航线要检查定点有没有设置基准图
		List<String> basicList = new ArrayList<String>();
		if (flyModule.equals("1")) {
			List<Map> list = fixedPointService.getFlyLinePointImg(param);
			for (Map map : list) {
				int isBasic = Integer.parseInt(map.get("isBasic").toString());
				if (isBasic == 1) {	//有基准图的跳过
					continue;
				}
				String pointUuid = map.get("uuid").toString();
				if(!StringUtils.isEmpty(pointUuid)) {	//统计没有基准图的定点个数
					basicList.add(pointUuid);
				}
			}
		}
		
		//对图片进行处理
		new FileThread(busId, fileId, flyModule).start();
		return ResultFactory.success(basicList);
	}

	private String checkNum(String busId, String fileId) throws FileNotFoundException {
		//查询航点集合
		List<FixedFlyPoint> flyPoints = fixedFlyPointService.listFlyPointsByTaskId(busId);
		UploadFile uploadFile = uploadFileService.getById(fileId); // 查询上传的文件
		String destPath = tempPath + File.separator + CommonUtils.getUUID();
		File dir = FileUtil.createDir(destPath);// 创建临时文件夹
		String srcPath = tempPath + File.separator + CommonUtils.getUUID();
		File file = FileUtil.createFile(srcPath); // 创建临时文件
		boolean download = fastDFSClient.downloadFile(uploadFile.getPath(), file);
		if (download) { // 下载并解压
			//ZipUtil.decompressZip(new FileInputStream(file), destPath);
			FileUtil.unzip(srcPath, destPath);
		}
		File[] imgs = dir.listFiles();
		if(flyPoints.size() != imgs.length) {
			return "航点数为：" + flyPoints.size() + "，但照片数为：" + imgs.length;
		}
		return "yes";
	}

	// 保存图片和定点或者航线的关系
	class FileThread extends Thread {

		private String taskId;
		private String fileId;
		private String flyModule;

		public FileThread(String taskId, String fileId, String flyModule) {
			// TODO Auto-generated constructor stub
			this.taskId = taskId;
			this.fileId = fileId;
			this.flyModule = flyModule;
		}

		@Override
		public void run() {

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("busId", taskId);
			UploadFile uploadFile = uploadFileService.getById(fileId); // 查询上传的文件
			String fileName = uploadFile.getOriginName();
			try {
				String destPath = tempPath + File.separator + CommonUtils.getUUID();
				File dir = FileUtil.createDir(destPath);// 创建临时文件夹
				String srcPath = tempPath + File.separator + fileName;
				File file = FileUtil.createFile(srcPath); // 创建临时文件
				boolean download = fastDFSClient.downloadFile(uploadFile.getPath(), file);
				if (download) { // 下载并解压
					FileUtil.unzip(srcPath, destPath);
				}

				File[] imgs = dir.listFiles();
				Map<String, File> fileMap = new HashMap<String, File>();	//图片对应文件缓存
				Map<String, String> pointMap = new HashMap<String, String>();	//图片对应定点缓存
				//查询航点集合
				List<FixedFlyPoint> flyPoints = fixedFlyPointService.listFlyPointsByTaskId(taskId);
				if(flyModule.equals("1") && flyPoints.size() != imgs.length) {
					return ;
				}
				//图片排序
				Arrays.sort(imgs,  new Comparator<File>() {
					@Override
					public int compare(File o1, File o2) {
						return o1.getName().compareTo(o2.getName());
					}
				});
				for (int i = 0; i<imgs.length; i++) { 
					File img = imgs[i];
					if (!img.getName().endsWith(".jpg") && !img.getName().endsWith(".JPG")) {
						continue;
					}

					//保存所有上传图片的信息，调算法的时候用
					List<UploadFile> uflist = uploadFileService.save(new File[] { img });
					UploadFile uf = uflist.get(0);

					//放到缓存里面调用算法的时候再用
					fileMap.put(uf.getUuid(), img);
					
					//区域扫描的图片直接绑定到调度任务主键
					if(flyModule.equals("2") || flyModule.equals("3")) {
						FileBus fbs = new FileBus();
						fbs.setBusId(taskId);
						fbs.setFileId(uf.getUuid());
						fbs.setModule("fly_img");
						fileBusService.save(fbs);
					}else {
						String imgName = img.getName();
						String pointId = flyPoints.get(i).getFixedId();
						
						//绑定定点的飞行图片
						FileBus fbs = new FileBus();
						fbs.setBusId(flyPoints.get(i).getUuid());
						fbs.setFileId(uf.getUuid());
						fbs.setModule("fly_img");
						fileBusService.save(fbs);
						
						//图片对应定点缓存
						pointMap.put(uf.getUuid(), flyPoints.get(i).getUuid());

						// 复制图片到labelme目录
						new LabelMeThread(img.getAbsolutePath(), imgName, pointId, uf.getUuid() + ".jpg").start();
					}
				}
				
				//调用预警算法
				if(flyModule.equals("2") || flyModule.equals("3")) {
					algorithmService.execAlgorithm(flyModule, taskId, fileMap);
				}else {
					log.debug ("#huangw#flyModule= {}, fileMap = {},  pointMap = {}", flyModule, fileMap.toString(), pointMap.toString());
					algorithmService.execAlgorithm(flyModule, fileMap, pointMap, taskId);
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
	
	class LabelMeThread extends Thread{
		
		private String sourcePath;
		private String sourceName;
		private String pointId;
		private String targetName;
		
		public LabelMeThread(String sourcePath, String sourceName, String pointId, String targetName) {
			// TODO Auto-generated constructor stub
			this.sourcePath = sourcePath;
			this.sourceName = sourceName;
			this.pointId = pointId;
			this.targetName = targetName;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				SFTPHelper sftpHelper = new SFTPHelper(labelMeUser, labelMePwd, labelMeIp);
				if(sftpHelper.connection()) {
					sftpHelper.put(sourcePath, labelMePath + File.separator);
					String imgDir = labelMePath + File.separator + pointId + File.separator;
					sftpHelper.execCommand(new String[] { "mkdir " + imgDir, "mv " + labelMePath + File.separator + sourceName + " " + imgDir + targetName});
//					sftpHelper.put(sourcePath, labelMePath + "/");
//					String imgDir = labelMePath + "/" + pointId + "/";
//					sftpHelper.execCommand(new String[] { "mkdir " + imgDir, "mv " + labelMePath + "/" + sourceName + " " + imgDir + targetName});
					sftpHelper.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	}
}
