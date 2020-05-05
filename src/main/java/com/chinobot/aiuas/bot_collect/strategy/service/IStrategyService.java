package com.chinobot.aiuas.bot_collect.strategy.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.strategy.entity.Strategy;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.AddFlightDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ChangeStatusDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.DelStategyDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListInfoDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListTaskDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.PageStrategyDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategySpecialDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.*;
import com.chinobot.common.entity.vo.TreeOptionVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 采查策略 服务类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface IStrategyService extends IBaseService<Strategy> {

	/**
	 * 策略分页查询
	 * @param page
	 * @param dto
	 * @return
	 */
	IPage<PageStrategyVO> pageStrategy(Page page, PageStrategyDTO dto);
	
	/**
	 * 获取所有的领域和场景
	 * @return
	 */
	List<DomainWithSceneVO> listAllDomainAndScene();
	
	/**
	 * 获取场景下的任务
	 * @param dto
	 * @return
	 */
	List<TaskVO> listTask(ListTaskDTO dto);
	
	/**
	 * 获取领域下的对象
	 * @param dto
	 * @return
	 */
	List<InfoVO> listInfo(ListInfoDTO dto);
	
	/**
	 * 保存策略
	 * @param vo
	 * @return
	 */
	String saveStrategy(StrategyDetailVO vo);
	
	/**
	 * 获取策略详细
	 * @param strategyId
	 * @return
	 */
	StrategyDetailVO getStrategyDetail(String strategyId);
	
	/**
	 * 改变业务状态
	 * @param dto
	 */
	void changeStrategyStatus(ChangeStatusDTO dto);
	
	/**
	 * 删除策略
	 * @param dto
	 */
	void delStrategy(DelStategyDTO dto);

	/**
	 * 策略列表
	 * @Author: shizt
	 * @Date: 2020/2/21 15:53
	 */
	List<StrategyListVO> getStrategyList(String strategyName);

	/**
	 * 根据策略主键获取 航班/机场/对象/障碍物
	 * @Author: shizt
	 * @Date: 2020/2/21 17:34
	 */
	Map<String, Object> getStrategyInitData(String strategyId, String... refreshModule);

	/**
	 * 根据策略id和对象id获取任务场景树
	 * @Author: shizt
	 * @Date: 2020/2/24 14:55
	 */
	List<TreeOptionVo> getTaskTree(String strategyId, String infoId);

	StrategySpecialVO getSpecialStrategyDetail(String strategyId);

	String saveSpecialStrategy(StrategySpecialDto dto);

	List<FlightDeatilVO> listFlight(String strategyId, String uuid);

	String addFight(AddFlightDto dto);

}
