package com.chinobot.aiuas.bot_collect.warning.service;

import java.time.LocalDate;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.EventFlow;
import com.chinobot.aiuas.bot_collect.warning.entity.Feedback;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.DoneDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.HistoryClueVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 预警信息 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-13
 */
public interface IWarningService extends IBaseService<Warning> {

	/**
	 * 预警信息 列表
	 * 
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	IPage<WarningListVo> getWarningList(Page page, Map<String, Object> param);

	/**
	 * 根据主键获取预警信息详情
	 * 
	 * @Param: [uuid]
	 * @Return: com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningVo
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 10:59
	 */
	WarningVo getWarningDetail(String uuid);

	/**
	    * 分拨实况 列表
	 * 
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	IPage<EventInfoListVo> getDistributionLiveList(Page page, Map<String, Object> param);

	
	/**
	    * 事件跟踪 列表
	 * 
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceListVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	IPage<EventTraceListVo> geteventTraceList(Page page, Map<String, Object> param);
	
	
	/**
	    * 事件反馈 列表
	 * 
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackListVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	IPage<EventFeedbackListVo> getEventFeedbackList(Page page, Map<String, Object> param);
	
	
	/**
	    * 事件分拨详情查询
	 * 
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	DistributionLiveVo getDistributionLiveDetail(String uuid,String warnId);
	
	/**
	    * 事件跟踪详情查询
	 * 
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceLiveVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	EventTraceLiveVo geteventTraceDetail(String uuid,String warnId);
	
	/**
	    * 事件跟踪详情查询
	 * 
	 * @Param: [param]
	 * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackVo>
	 * @Author: yuanwanggui
	 * @Date: 2020/1/16 14:26
	 */
	EventFeedbackVo getEventFeedbackDetail(String uuid);
	
	
	/**
     * 根据uuid批量修改预警事件流转记录表的业务状态
     * @Param: [param]
     * @Return: int
     * @Author: yuanwanggui
     * @Date: 2020/1/20 09:50
     */
   int updateBIBS(Map param);
   
   /**
    * 新增修改预警事件表的业务状态
    * @Param: [param]
    * @Return: boolean
    * @Author: yuanwanggui
    * @Date: 2020/1/20 09:50
    */
   boolean updateBFBS(EventFlow eventFlow);
   
   /**
    * 根据uuid批量修改预警事件信息表的业务状态
    * @Param: [param]
    * @Return: int
    * @Author: yuanwanggui
    * @Date: 2020/1/20 09:50
    */
   int updateWI(@Param("businessStatus") String businessStatus,@Param("uuid") String uuid);
   
   /**
    * 根据uuid获取单个预警事件信息
    * @Param: [param]
    * @Return: int
    * @Author: yuanwanggui
    * @Date: 2020/1/20 09:50
    */
   Map<String,Object> getBotEventInfo(@Param("uuid") String uuid);
   
   /**
    * 新增白名单
    * @Param: [param]
    * @Return: 
    * @Author: yuanwanggui
    * @Date: 2020/1/20 02:04
    */
   Result addWhiteList(String uuid,LocalDate invalidTime,String hostBy);
   

	/**
	 * 分拨实况撤销
	 * @param hostBy
	 * @param uuid
	 * @return
	 */
	Result updateEvent(String hostBy, String uuid);
	
	/**
	 * 改变预警事件表中状态为白名单状态 且 需要改变 预警流转记录中的状态
	 * @param uuid
	 * @param hostBy
	 * @param businessStatus
	 */
	int updateStatus(String uuid,String hostBy,String fileId,String module,String businessStatus,String idea,String beforeStatus);

	/**
	 * 历史线索详情
	 * @param uuid
	 * @return
	 */
	HistoryClueVo getHistoryClueDetail(String uuid,String warnId);

	/**
	 * 治理新增
	 * @param dto
	 * @return
	 */
	boolean eventToDone(DoneDTO dto);
	
}
