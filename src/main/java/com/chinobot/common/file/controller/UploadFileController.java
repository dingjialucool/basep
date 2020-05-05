package com.chinobot.common.file.controller;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.entity.vo.FileBusListVo;
import com.chinobot.common.file.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.UploadFile;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.file.service.IUploadFileService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 文件 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2019-03-19
 */
@Api(tags= {"文件类接口"})
@RestController
@RequestMapping("/api/file")
@Slf4j
public class UploadFileController extends BaseController {
	
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private FileClient fileClient;
	@Autowired
	private FastDFSClient fastDFSClient;

	@Value("${config.fastdfsDownloadUrl}")
	private String fastdfsDownloadUrl;

	/**
	 * io读取图片
	 * @param fileId
	 * @param resp
	 * @return
	 * @throws IOException
	 * @author shizt  
	 * @date 2019年3月19日
	 * @company chinobot
	 * @thumbnail 是否缩略图
	 * @pictureSize 控制图片清晰度(传参示例："400,300")
	 */
	@ApiOperation(value = "io读取图片", notes = "参数- 文件id，是否所率图thumbnail")
	@GetMapping("/ioimage")
//	@RequestMapping("/ioimage")
	public void IOReadImage(String fileId, @RequestParam(required=false) boolean thumbnail,@RequestParam(required=false) String pictureSize, HttpServletResponse resp) throws IOException {
		UploadFile uploadFile = uploadFileService.getById(fileId);
		String filePath = CommonUtils.objNotEmpty(uploadFile) ? uploadFile.getPath() : null;
		if(null != filePath) {
			if(thumbnail){
				ServletOutputStream out = null;
				try {
					out = resp.getOutputStream();
					out.write (fileClient.downloadImage(filePath, thumbnail,pictureSize));
					out.flush();
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					if(out!=null) {
						out.close();
					}
				}
			}else{
				resp.sendRedirect(fastdfsDownloadUrl + filePath);
			}
		}

//		return null;
	}
	/**
	 * 下载文件
	 * @Param: [fileId, resp]
	 * @Return: java.lang.String
	 * @Author: shizt
	 * @Date: 2019/11/5 14:24
	 */
	@ApiOperation(value = "下载文件", notes = "下载文件")
	@GetMapping("/downloadFile")
	public void downloadFile(String fileId, HttpServletResponse resp) throws IOException {
		UploadFile uploadFile = uploadFileService.getById(fileId);
		String filePath = CommonUtils.objNotEmpty(uploadFile) ? uploadFile.getPath() : null;


		if(null != filePath) {
			resp.sendRedirect(fastdfsDownloadUrl + filePath);
//			ServletOutputStream out = null;
//			try {
//				byte[] bytes = fileClient.downloadFile(filePath);
////				String fileMD5 = MD5Util.getFileMD5(bytes);
////				System.err.println("===============123" + fileMD5);
//
//				resp.reset();// 清空输出流
//				resp.setContentType("application/vnd.android.package-archive;");
//				resp.setHeader("Content-Disposition", "attachment;filename=" + uploadFile.getOriginName());
//				resp.setContentLength(bytes.length);
//
//				out = resp.getOutputStream();
//				out.write(bytes);
//				out.flush();
//			}catch (Exception e){
//				e.printStackTrace();
//			}finally {
//				if(out!=null) {
//					out.close();
//				}
//			}
		}
//		return null;
	}

	@ApiOperation(value = "文件上传", notes = "参数- 文件对象file")
	@PostMapping("/upload")
	public Result upload(@RequestParam("file") MultipartFile file) throws IOException {
		if (file.isEmpty()) {
			return ResultFactory.error ();
		}
		List<UploadFile> uploadFile = uploadFileService.save(file);
		return ResultFactory.success (uploadFile);
	}
	
	
	/**
	 * 根据id/模块获取文件列表
	 * @param uuid
	 * @return
	 * @author shizt  
	 * @date 2019年5月21日
	 * @company chinobot
	 */
	@ApiOperation(value = "根据id/模块获取文件列表", notes = "参数- Map param")
	@GetMapping("")
//	@RequestMapping("")
	public Result getFiles(@RequestParam Map<String, Object> param){
		param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
		
		return ResultFactory.success(fileBusService.getFileIdByBusId(param));
	}

	/**
	 * @Author: shizt
	 * @Date: 2020/4/9 17:28
	 */
	@ApiOperation(value = "保存业务文件关联")
	@PostMapping("/saveFileBusList")
	public Result saveFileBusList(@RequestBody FileBusListVo fileBusListVo){
		String busId = fileBusListVo.getBusId();
		String[] fileIds = fileBusListVo.getFileIds();
		String module = fileBusListVo.getModule();

		List<FileBus> fileBuses = new ArrayList<>();
		for (String id: fileIds) {
			FileBus fileBus = new FileBus();
			fileBus.setFileId(id);
			fileBuses.add(fileBus);
		}

		return ResultFactory.success(fileBusService.saveFileBusList(fileBuses, busId, module));
	}
}
