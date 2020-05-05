package com.chinobot.aiuas.bot_collect.warning.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.EventInfo;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.DoneDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoTypeListVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 预警事件 服务类
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
public interface IEventInfoService extends IBaseService<EventInfo> {

	/**
	 * 批量分派
	 * @param idList
	 * @param personId
	 * @return
	 */
	Result quantityDis(List<String> idList, String personId);

	/**
	 * 飞行员下拉选择
	 * @param param
	 * @return
	 */
	List<Map> getFlyPerson(Map param);

	/**
	 * 无人机下拉选择
	 * @param param
	 * @return
	 */
	List<Map> getUav(Map param);

	/**
	 * 批量撤销
	 * @param idList
	 * @param personId
	 * @return
	 */
	boolean batchRevocation(List<String> idList, String personId);

	/**
	 * 事件核查
	 * @param hostBy
	 * @param uuid
	 * @return
	 */
	Result eventCheck(String hostBy, String uuid,String fileId,String idea,String businessStatus);

	/**
	 * 事件办结
	 * @param hostBy
	 * @param uuid
	 * @param idea
	 * @return
	 */
	Result eventDone(String hostBy, String uuid,String fileId, String idea,String businessStatus);

	/**
	    * 线索分拨 列表
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	IPage<EventInfoListVo> getClueList(Page page, Map<String, Object> param);

	/**
	 * 查询及获取历史事件List列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<EventInfoTypeListVo> getHistoryEventAndClueList(Page page, Map<String, Object> param);

	/**
	 * 查询及获取历史线索List列表
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<EventInfoTypeListVo> getHistoryClueList(Page page, Map<String, Object> param);

	/**
	 * 预警废除
	 * @param warnId
	 */
	void removeWarningMessage(String warnId);

}
