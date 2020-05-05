package com.chinobot.aiuas.bot_prospect.flight.service;

import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightReferenceVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavWorkVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

import java.io.IOException;

/**
 * <p>
 * 航班 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-02-21
 */
public interface IFlightService extends IBaseService<Flight> {

    /**
     * 编辑 航班
     * @Author: shizt
     * @Date: 2020/3/2 10:27
     */
    Result edit(FlightReferenceVo flightReferenceVo) throws IOException;

    /**
     * 获取无人机作业信息
     * @param uavId
     * @return
     */
	UavWorkVo getUavWorkInfo(String uavId);
}
