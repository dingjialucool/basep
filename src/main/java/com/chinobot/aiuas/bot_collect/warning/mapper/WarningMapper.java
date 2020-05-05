package com.chinobot.aiuas.bot_collect.warning.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.EventFlow;
import com.chinobot.aiuas.bot_collect.warning.entity.Feedback;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.FileMessageDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.FeedBackHistoryVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.HostMessageVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.HistoryClueVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SuperviseVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 预警信息 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-13
 */
public interface WarningMapper extends IBaseMapper<Warning> {
	
	
	/**
     * 获取预警信息list列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/14 11:52
     */
    IPage<WarningListVo> getWarningList(Page page, @Param("p") Map<String,Object> param);
    
    
    /**
     * 获取预警信息详细信息
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/14 11:52
     */
    WarningVo getWarningDetail(@Param("uuid") String uuid);
    
    
    /**
     * 获取分拨实况list列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/15 11:52
     */
    IPage<EventInfoListVo> getDistributionLiveList(Page page, @Param("p") Map<String,Object> param);
    
    /**
     * 获取事件跟踪list列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceListVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/15 11:52
     */
    IPage<EventTraceListVo> geteventTraceList(Page page, @Param("p") Map<String,Object> param);
    
    /**
     * 获取事件反馈list列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackListVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/14 11:52
     */
    IPage<EventFeedbackListVo> getEventFeedbackList(Page page, @Param("p") Map<String,Object> param);
    
    /**
     * 获取事件分拨详情
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/15 11:52
     */
    DistributionLiveVo getDistributionLiveDetail(@Param("uuid") String uuid);
    
    /**
     * 获取事件跟踪详情
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceLiveVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/15 11:52
     */
    EventTraceLiveVo geteventTraceDetail(@Param("uuid") String uuid);
    
    /**
     * 获取事件反馈详情
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackVo>
     * @Author: yuanwanggui
     * @Date: 2020/1/16 11:10
     */
    EventFeedbackVo getEventFeedbackDetail(@Param("uuid") String uuid);
    
    
    /**
     * 根据uuid批量修改预警事件流转记录表的业务状态
     * @Param: [param]
     * @Return: int
     * @Author: yuanwanggui
     * @Date: 2020/1/20 09:50
     */

   int updateBIBS(@Param("p") Map param);
   
   /**
    * 根据uuid批量修改预警事件表的业务状态
    * @Param: [param]
    * @Return: int
    * @Author: yuanwanggui
    * @Date: 2020/1/20 09:50
    */
   int updateBFBS(@Param("p") Map param);
   
   /**
    * 根据uuid批量修改预警信息表的业务状态
    * @Param: [param]
    * @Return: int
    * @Author: yuanwanggui
    * @Date: 2020/1/20 09:50
    */
   int updateWI(@Param("businessStatus") String businessStatus,@Param("uuid") String uuid);
   
   /**
    * 根据uuid批量修改预警事件信息表的业务状态
    * @Param: [param]
    * @Return: int
    * @Author: yuanwanggui
    * @Date: 2020/1/20 09:50
    */
   Map<String,Object> getBotEventInfo(@Param("uuid") String uuid);
   
   
   /**
	 * 添加反馈信息
	 * @param student 反馈信息实例
	 * @return 成功操作的记录数目
	 */
	int addFeedback(@Param("feedback") Feedback feedback);

	/**
	 * 预警信息图片或视频集合
	 * @param param
	 * @return
	 */
	List<String> getWarnByBusId(@Param("p")Map<String, String> param);

	/**
	 * 反馈信息集合
	 * @param uuid
	 * @return
	 */
	List<HostMessageVo> getFeedBackList(String uuid);

	/**
	 * 督办信息集合
	 * @param uuid
	 * @return
	 */
	List<SuperviseVo> getSuperviseList(String uuid);

	/**
	 * 获取预警流转记录中的发送人发送时间
	 * @param uuid
	 * @param toBeConfirmed
	 */
	EventFlow getSendAndSendTime(@Param("uuid")String uuid, @Param("status")String status);


	/**
	 * 根据预警事件id获取预警消息
	 * @param uuid
	 * @return
	 */
	Warning getWarnByEventId(@Param("uuid")String uuid);
	
	/**
	 * 根据业务id和module获取文件信息
	 * @param uuid
	 * @param status
	 * @return
	 */
	List<FileMessageDTO> getFileMessage(@Param("p")Map<String, String> param);

	/**
	 * 获取历史事件详情
	 * @param uuid
	 * @return
	 */
	HistoryClueVo getHistoryClueDetail(@Param("uuid") String uuid);

	/**
	 * 根据预警消息id获取数量统计
	 */
	List<CollectResultVo> getResultByWarnId(@Param("workId") String workId,@Param("objectId")String objectId);
}
