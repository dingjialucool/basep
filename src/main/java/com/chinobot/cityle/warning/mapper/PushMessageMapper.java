package com.chinobot.cityle.warning.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.warning.entity.PushMessage;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 推送信息表 Mapper 接口
 * </p>
 *
 * @author dingjl
 * @since 2019-03-16
 */
public interface PushMessageMapper extends IBaseMapper<PushMessage> {

	
	/**
	 * 	推送信息已被确认
	 * @param param
	 * @return
	 */
	IPage<Map> getYesPushMessage(Page page, @Param("a") Map<String, Object> param);

	/**
	 *  推送信息未推送
	 * @param page
	 * @param param
	 * @param pushStatus
	 * @param confireStatus
	 * @return
	 */
	IPage<Map> getNoPushMessage(Page page, @Param("a") Map<String, Object> param);

	/**
	 * 	 获取实时预警信息
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<Map> getRealTimeMessge(Page page, @Param("a") Map<String, Object> param);

	/**
	 * 	获取历史预警信息
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<Map> getHistoryTimeMessge(Page page, @Param("a") Map<String, Object> param);

	/**
	 * 	获取文件ID与类型
	 * @param param
	 * @return
	 */
	List<LinkedHashMap<String, String>> getFileId(@Param("a") Map<String, Object> param);
}
