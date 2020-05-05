package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Parts;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 部件 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-04-04
 */
public interface PartsMapper extends IBaseMapper<Parts> {
	
	/**
	 * 部件 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	IPage getParts(Page page, @Param("p") Map<String, Object> param);
	
	/**
	 * 根据条件获取部件列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月3日
	 * @company chinobot
	 */
	List<Map> getParts(@Param("p") Map<String, String> param);
}
