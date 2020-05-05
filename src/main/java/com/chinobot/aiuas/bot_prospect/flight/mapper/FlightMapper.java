package com.chinobot.aiuas.bot_prospect.flight.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.StrategyFlightVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavWorkDetailVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.monitor.entity.UavOnline;

/**
 * <p>
 * 航班 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-02-21
 */
public interface FlightMapper extends IBaseMapper<Flight> {

    /**
     * 根据策略主键获取航班列表
     * @param strategyId
     * @return
     */
    List<StrategyFlightVo> getListByStrategyId(@Param("strategyId") String strategyId);

    Map getFlightInfoByFlightWorkId(@Param("flightWorkId") String flightWorkId);
    
    /**
     * 获取无人机作业详情
     * @param p
     * @return
     */
    UavWorkDetailVo getUavWorkDetail(@Param("p") UavOnline p);
}
