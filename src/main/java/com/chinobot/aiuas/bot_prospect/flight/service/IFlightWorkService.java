package com.chinobot.aiuas.bot_prospect.flight.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.entity.dto.UavFlightWorkParamDto;
import com.chinobot.aiuas.bot_prospect.flight.entity.dto.WorkStateDto;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightWorkPageVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.FlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.UavFlightWorkVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.WorkFilesVo;
import com.chinobot.framework.web.service.IBaseService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 航班作业 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-02-24
 */
public interface IFlightWorkService extends IBaseService<FlightWork> {

	/**
	 * 航班作业 分页列表
	 * @Author: shizt
	 * @Date: 2020/2/25 10:57
	 */
	IPage<FlightWorkPageVo> getPage(Page page, Map<String, Object> param);

	/**
	 * 航班作业数据 策略列表/无人机人员状态/统计/作业分页
	 * @Author: shizt
	 * @Date: 2020/2/25 14:21
	 */
	Map<String, Object> getFlightWorkData(Page page, Map<String, Object> param, String... refreshModule);
	
	/**
	 * 航班调度数据
	 * @Author: shizt
	 * @Date: 2020/3/6 11:44
	 */
	Map<String, Object> getFlightTaskData(Map<String, Object> param, String... refreshModule);

	/**
	 * 航班作业 列表
	 * @Author: shizt
	 * @Date: 2020/2/25 10:57
	 */
	List<FlightWorkPageVo> getList(Map<String, Object> param);

	/**
	 * 编辑航班作业时间
	 * @Author: shizt
	 * @Date: 2020/3/6 16:35
	 */
	String edit(FlightWorkVo flightWorkVo);

	/**
	 * 航班作业详情
	 * @Author: shizt
	 * @Date: 2020/3/9 15:38
	 */
	Map<String, Object> getWorkInfo(Map<String, Object> param, String... refreshModule);
	
	/**
	 * 获取无人机航班任务
	 * @param dto
	 * @return
	 */
	List<UavFlightWorkVo> getUavWorks(UavFlightWorkParamDto dto);

	/**
	 * 保存作业文件
	 * @throws Exception 
	 * @Author: shizt
	 * @Date: 2020/3/10 9:55
	 */
	void doneWork(WorkFilesVo workFilesVo) throws Exception;

	void doSaveFiles(WorkFilesVo workFilesVo) throws Exception;

	/**
	 * 改变作业状态
	 * @param dto
	 * @return
	 */
	String changeWorkState(WorkStateDto dto);
	
	/**
	 * 往无人机推送紧急调度任务
	 * @param urgentId
	 */
	void transmitUrgent(String urgentId);

	/**
	 * 常规作业 航线
	 * @Author: shizt
	 * @Date: 2020/3/24 16:57
	 */
	Map getRouteByFlightWork(String flightWorkId);

	/**
	 * 每日作业数量
	 * @Author: shizt
	 * @Date: 2020/4/9 14:22
	 */
	List<Map> getWorkCountByMonth(String pageType, String month);
}
