package com.chinobot.common.file.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.file.entity.OriginFiles;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 原始文件 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-06-05
 */
public interface OriginFilesMapper extends IBaseMapper<OriginFiles> {

	IPage<Map> getOriginPage(Page page,@Param("p")Map<String, Object> param);
	
	/**
	 * 获取需标记的图片
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年6月5日
	 * @company chinobot
	 */
	Map<String, String> getLabelPicture(@Param("p")Map<String, Object> param);

	
	List<OriginFiles> getOriginFilesByTask(Map<String, Object> param);	
}
