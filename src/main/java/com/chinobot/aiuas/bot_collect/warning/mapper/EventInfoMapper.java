package com.chinobot.aiuas.bot_collect.warning.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.EventInfo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoTypeListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.FeedBackHistoryVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 预警事件 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
public interface EventInfoMapper extends IBaseMapper<EventInfo> {

	/**
	 * 飞行员下拉选择
	 * @param param
	 * @return
	 */
	List<Map> getFlyPerson(@Param("p") Map param);

	/**
	 * 无人机下拉选择
	 * @param param
	 * @return
	 */
	List<Map> getUav(@Param("p") Map param);

	/**
	 * 获取反馈历史记录
	 * @param uuid
	 * @return
	 */
	List<FeedBackHistoryVo> getFeedBackRecord(@Param("uuid")String uuid);

	/**
	 * 线索分拨列表查询
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<EventInfoListVo> getClueList(Page page, @Param("p") Map<String,Object> param);

	/**
	 *  查询及获取历史事件List列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<EventInfoTypeListVo> getHistoryEventAndClueList(Page page,@Param("p") Map<String, Object> param);

	/**
	 * 查询及获取历史线索List列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<EventInfoTypeListVo> getHistoryClueList(Page page,@Param("p") Map<String, Object> param);
}
