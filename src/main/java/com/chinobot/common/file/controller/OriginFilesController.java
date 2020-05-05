package com.chinobot.common.file.controller;


import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.OriginFiles;
import com.chinobot.common.file.service.IOriginFilesService;
import com.chinobot.common.file.util.FileClient;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;

/**
 * <p>
 * 原始文件 前端控制器
 * </p>
 *
 * @author djl
 * @since 2019-06-05
 */
@Api(tags= {"原始文件接口-N(未使用)"})
@RestController
@RequestMapping("/api/origin-files")
public class OriginFilesController extends BaseController {

	@Autowired
	private IOriginFilesService oriService;
	@Autowired
	private FileClient fileClient;
	
	/**
	 * 上传文件
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/origin")
	public Result upload(@RequestParam("file") MultipartFile file,String catalogId) throws IOException {
		if (file.isEmpty()) {
			return ResultFactory.error ();
		}
		List<OriginFiles> originFile = oriService.save(file,catalogId);
		return ResultFactory.success (originFile);
	}
	
	/**
	 *  获取文件
	 * @param page
	 * @param param
	 * @return
	 */
	@GetMapping("/getOrigin")
	public Result getOrigin(Page page, @RequestParam Map<String, Object> param) {
		
		return ResultFactory.success(oriService.getOriginPage(page, param));
	}
	
	/**
	 *  刪除文件
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/file/delete")
	public Result deleteFile(@RequestBody OriginFiles originFiles) {
		
		originFiles.setDataStatus("0");
		boolean bo = oriService.updateById(originFiles);
		if (!bo) {
			return ResultFactory.fail(null);
		}
		return ResultFactory.success();
		
	}
	
	
	/**
	 * io读取图片
	 * @param fileId
	 * @param resp
	 * @return
	 * @throws IOException
	 * @author shizt  
	 * @date 2019年3月19日
	 * @company chinobot
	 */
	@RequestMapping("/ioimage")
	public String IOReadImage(String fileId, HttpServletResponse resp) throws IOException {
		OriginFiles originFiles = oriService.getById(fileId);

		String filePath = CommonUtils.objNotEmpty(originFiles) ? originFiles.getPath() : null;
		if(null != filePath) {
			ServletOutputStream out = null;
			try {
				out = resp.getOutputStream();
				out.write (fileClient.downloadFile(filePath));
				out.flush();
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				if(out!=null) {
					out.close();
				}
			}
		}

		return null;
	}
}
