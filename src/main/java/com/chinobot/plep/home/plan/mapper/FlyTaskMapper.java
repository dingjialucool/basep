package com.chinobot.plep.home.plan.mapper;

import com.chinobot.plep.home.plan.entity.FlyTask;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 飞行任务表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-06-19
 */
public interface FlyTaskMapper extends IBaseMapper<FlyTask> {
	
	IPage<Map> getTaskPage(Page page, @Param("p") Map<String, String> param);
	
	Map getTaskPage(@Param("p") Map<String, String> param);

	Map getTaskByTime(@Param("p") Map<String, Object> param);
	
	IPage<Map> getUavList(Page page, @Param("p") Map<String, String> param);//TODO 后期加入权限验证
	
}
