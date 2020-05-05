package com.chinobot.common.file.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.file.entity.OriginFiles;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 原始文件 服务类
 * </p>
 *
 * @author djl
 * @since 2019-06-05
 */
public interface IOriginFilesService extends IBaseService<OriginFiles> {

	List<OriginFiles> save(MultipartFile file,String catalogId) throws IOException;

	IPage<Map> getOriginPage(Page page, Map<String, Object> param);
	
	/**
	 * 获取需标记的图片
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年6月5日
	 * @company chinobot
	 */
	Map<String, String> getLabelPicture(Map<String, Object> param);

	/**
	 *   根据task_id和label_task_id获取标记任务需要的图片信息
	 * @param param
	 * @return
	 */
	List<OriginFiles> getOriginFilesByTask(Map<String, Object> param);

}
