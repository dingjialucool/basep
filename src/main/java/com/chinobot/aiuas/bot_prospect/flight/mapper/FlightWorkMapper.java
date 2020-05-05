package com.chinobot.aiuas.bot_prospect.flight.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EchartDateVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.FlightWorkInfoVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.MapCoordinateInfoVo;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.UrgentWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.entity.dto.UavFlightWorkParamDto;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightWorkPageVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.StatisticsVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavFlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavObjectVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavPersonMinuteVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 航班作业 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-02-24
 */
public interface FlightWorkMapper extends IBaseMapper<FlightWork> {

    /**
     * 航班作业 分页列表
     * @Author: shizt
     * @Date: 2020/2/25 10:53
     */
    IPage<FlightWorkPageVo> getList(Page page, @Param("p") Map<String, Object> param);

    /**
     * 航班作业 列表
     * @Author: shizt
     * @Date: 2020/2/25 10:53
     */
    List<FlightWorkPageVo> getList(@Param("p") Map<String, Object> param);

    /**
     * 无人机已安排时间
     * @Author: shizt
     * @Date: 2020/2/25 15:17
     */
    List<UavPersonMinuteVo> getUavMinuteList(@Param("p") Map<String, Object> param);

    /**
     * 飞手已安排时间
     * @Author: shizt
     * @Date: 2020/2/25 15:17
     */
    List<UavPersonMinuteVo> getPersonMinuteList(@Param("p") Map<String, Object> param);

    /**
     * 航班作业统计
     * @Author: shizt
     * @Date: 2020/2/25 15:23
     */
    StatisticsVo getStatistics(@Param("p") Map<String, Object> param);
    
    /**
     * 获取该航班的最大批次
     * @param flightUuid
     * @return
     */
    Integer getMaxBatch(@Param("strategyId") String strategyId);
    
    /**
     * 统计策略在某天生成的任务数
     * @param param
     * @return
     */
    Long countBystrategyAndDate(@Param("p") Map<String, Object> param);

    /**
     * 航班任务统计
     * @Author: shizt
     * @Date: 2020/2/25 15:23
     */
    Map<String, String> getFlightTaskStatistics(@Param("p") Map<String, Object> param);

    /**
     * 获取航班作业时间
     * @Author: shizt
     * @Date: 2020/3/6 16:36
     */
    List<FlightWorkVo> getWorkTime(@Param("p") Map<String, Object> param);

    /**
     * 获取航班作业中的策略、航班
     * @Author: shizt
     * @Date: 2020/3/9 10:36
     */
    List<Map> getStrategyFlightByWork(@Param("p") Map<String, Object> param);

    /**
     * 根据航班作业id获取任务、对象列表
     * @Author: shizt
     * @Date: 2020/3/9 15:53
     */
    List<Map> getTaskInfoListByFlightWorkId(@Param("flightWorkId") String flightWorkId);

    /**
     * 根据航班作业id获取策略下所有任务、对象列表
     * @Author: shizt
     * @Date: 2020/3/9 15:53
     */
    List<Map> getTaskInfoListByStrategy(@Param("flightWorkId") String flightWorkId);

    /**
     * 根据航班作业id获取人员无人机起飞时间
     * @Author: shizt
     * @Date: 2020/3/9 17:05
     */
    Map getPersonUavDateByFlightWorkId(@Param("flightWorkId") String flightWorkId);

    /**
     * 获取无人机航班任务
     * @param dto
     * @return
     */
	List<UavFlightWorkVo> getUavWorks(@Param("dto")UavFlightWorkParamDto dto);

    /**
     * 根据航班作业id获取作业结果
     * @Author: shizt
     * @Date: 2020/3/9 19:43
     */
    Map getWorkResultByFlightWorkId(@Param("flightWorkId") String flightWorkId);

    /**
     * 根据航班作业id获取作业结果
     * @Author: shizt
     * @Date: 2020/3/9 20:16
     */
    List<Map> getWarningListByFlightWorkId(@Param("flightWorkId") String flightWorkId);
    
    /**
     * 获取航班的采查对象及任务
     * @param flightUuid
     * @return
     */
    List<UavObjectVo> getObjAndTaskByFight(@Param("flightUuid") String flightUuid);
    
    Map getFightNameAndEname(@Param("workId") String workId, @Param("uavId") String uavId);
    
    Map getUrgentNameAndEname(@Param("workId") String workId, @Param("uavId") String uavId);
    /**
     * 获取航班信息
     * @param flightUuid
     * @return
     */
    IPage<FlightWorkInfoVo> getMonitorCarNumber(Page page,@Param("p") Map<String, Object> param);
    
   /**
    * 获取航班信息
    * @param flightUuid
    * @return
    */
   List<FlightWorkInfoVo> getMonitorCarNumber(@Param("p") Map<String, Object> param);
   
   /**
    * 获取航班信息
    * @param flightUuid
    * @return
    */
   List<EchartDateVo> getEchartData(@Param("p") Map<String, Object> param);
   
   /**
    * 获取紧急调度任务推送
    * @param uuid
    * @return
    */
   UrgentWorkVo getUrgentWorkVo(@Param("uuid")String uuid);
   
   /**
    * 获取采查结果
    * @param workId
    * @return
    */
   List<CollectResultVo> getCollectData(@Param("workId") String workId);
   /**
    * 获取对象预警地图数据
    * @param param
    * @return
    */
   List<MapCoordinateInfoVo> getMapAddressInfoData(@Param("p") Map<String,Object> param);

    /**
     * 常规作业 航线
     * @Author: shizt
     * @Date: 2020/3/24 16:57
     */
    Map getRouteByFlightWork(@Param("flightWorkId")String flightWorkId);

    /**
     * 每日作业数量
     * @Author: shizt
     * @Date: 2020/4/9 14:22
     */
    List<Map> getWorkCountByMonth(@Param("workStatus")String[] workStatus, @Param("month")String month);
}
