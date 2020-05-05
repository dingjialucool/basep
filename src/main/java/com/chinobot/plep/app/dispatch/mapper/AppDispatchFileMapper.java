package com.chinobot.plep.app.dispatch.mapper;

import com.chinobot.plep.app.dispatch.entity.AppDispatchFile;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * app调度预警文件 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-08-13
 */
public interface AppDispatchFileMapper extends IBaseMapper<AppDispatchFile> {

	AppDispatchFile getDispatchFiles(@Param("p") Map<String, Object> param);
	
}
