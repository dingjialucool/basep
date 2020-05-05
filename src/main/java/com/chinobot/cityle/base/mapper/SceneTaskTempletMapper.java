package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.SceneTaskTemplet;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 巡查内容模板 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-04-09
 */
public interface SceneTaskTempletMapper extends IBaseMapper<SceneTaskTemplet> {
	
	/**
	 * 巡查内容模板 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	IPage<Map> getTaskTempletByPage(Page page, @Param("p")Map<String, Object> param);
	
	/**
	 * 巡查内容模板 列表
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	List<Map> getTaskTemplet(@Param("p")Map<String, String> param);
}
