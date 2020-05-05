package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.SceneTask;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 巡查内容 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-04-10
 */
public interface SceneTaskMapper extends IBaseMapper<SceneTask> {
	
	/**
	 * 巡查内容 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	IPage<Map> getTask(Page page, @Param("p")Map<String, String> param);
	
	/**
	 * 巡查内容 列表
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	List<Map> getTask(@Param("p")Map<String, String> param);
	
	/**
	 * 添加默认巡查内容
	 * @param sceneType
	 * @param sceneId
	 * @return
	 * @author shizt  
	 * @date 2019年4月11日
	 * @company chinobot
	 */
	int addTempletTask(@Param("sceneType")String sceneType, @Param("sceneId")String sceneId);

	
}
