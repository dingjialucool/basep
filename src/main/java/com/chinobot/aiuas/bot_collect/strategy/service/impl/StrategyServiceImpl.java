package com.chinobot.aiuas.bot_collect.strategy.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.airport.entity.BotProspectAirport;
import com.chinobot.aiuas.bot_collect.airport.service.IBotProspectAirportService;
import com.chinobot.aiuas.bot_collect.info.entity.FlightTask;
import com.chinobot.aiuas.bot_collect.info.entity.dto.InfoTreeDto;
import com.chinobot.aiuas.bot_collect.info.mapper.InfoMapper;
import com.chinobot.aiuas.bot_collect.info.service.IFlightTaskService;
import com.chinobot.aiuas.bot_collect.strategy.entity.Strategy;
import com.chinobot.aiuas.bot_collect.strategy.entity.StrategyTask;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.AddFlightDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ChangeStatusDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.DelStategyDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListInfoDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.ListTaskDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.PageStrategyDTO;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.SpecialStrategyNodeDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.dto.StrategySpecialDto;
import com.chinobot.aiuas.bot_collect.strategy.entity.vo.*;
import com.chinobot.aiuas.bot_collect.strategy.mapper.StrategyMapper;
import com.chinobot.aiuas.bot_collect.strategy.service.IStrategyService;
import com.chinobot.aiuas.bot_collect.strategy.service.IStrategyTaskService;
import com.chinobot.aiuas.bot_collect.task.entity.dto.TaskSceneDto;
import com.chinobot.aiuas.bot_collect.task.mapper.TaskMapper;
import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightMapper;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightService;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.aiuas.bot_prospect.obstacle.mapper.ObstacleMapper;
import com.chinobot.aiuas.bot_resource.uav.entity.UavType;
import com.chinobot.aiuas.bot_resource.uav.service.IUavTypeService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.entity.vo.TreeOptionVo;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 采查策略 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Service
public class StrategyServiceImpl extends BaseService<StrategyMapper, Strategy> implements IStrategyService {

	@Autowired
	private StrategyMapper strategyMapper;
	
	@Autowired
	private IStrategyTaskService strategyTaskService;
	@Autowired
	private InfoMapper infoMapper;
	@Autowired
	private FlightMapper flightMapper;
	@Autowired
	private ObstacleMapper obstacleMapper;
	@Autowired
	private TaskMapper taskMapper;
	@Autowired
	private IBotProspectAirportService iBotProspectAirportService;
	@Autowired
	private IUavTypeService iUavTypeService;
	@Autowired
	private IFlightService flightService;
	@Autowired
	private IFlightWorkService workService;
	@Autowired
	private IFlightTaskService flightTaskServcie;
	
	
	
	@Override
	public IPage<PageStrategyVO> pageStrategy(Page page, PageStrategyDTO dto) {
		
		return strategyMapper.pageStrategy(page, dto);
	}

	@Override
	public List<DomainWithSceneVO> listAllDomainAndScene() {
		
		return strategyMapper.listAllDomainAndScene();
	}

	@Override
	public List<TaskVO> listTask(ListTaskDTO dto) {
		
		return strategyMapper.listTask(dto);
	}

	@Override
	public List<InfoVO> listInfo(ListInfoDTO dto) {
		
		return strategyMapper.listInfo(dto);
	}

	@Transactional
	@Override
	public String saveStrategy(StrategyDetailVO vo) {
		Strategy strategy = new Strategy();
		strategy.setUuid(vo.getStrategyId());
		strategy.setStrategyName(vo.getStrategyName());
		strategy.setStrategyDesc(vo.getStrategyDesc());
		strategy.setBusinessStatus(vo.getBusinessStatus());
		strategy.setStrategyType(vo.getStrategyType());
		strategy.setStrategyValue(vo.getStrategyValue());
		strategy.setWorkCount(vo.getWorkCount());
		if(vo.getStrategyPlan() != null && vo.getStrategyPlan().length > 0){
			strategy.setStrategyPlan(StringUtils.join(vo.getStrategyPlan(), ","));
		}
		this.saveOrUpdate(strategy);
		//先把之前的任务置为无效
		StrategyTask entity = new StrategyTask();
		entity.setIsDeleted(true);
		strategyTaskService.update(entity , CommonUtils.getEqUpdateWrapper("strategy_uuid", "is_deleted", strategy.getUuid(), 0));
		for(SceneWithIdsVO scene : vo.getScenes()) {
			if(scene.getInfoIds() != null && scene.getInfoIds().size()>0 && scene.getTaskIds() != null && scene.getTaskIds().size()>0) {
				for(String taskId : scene.getTaskIds()) {
					for(String infoId : scene.getInfoIds()) {
						StrategyTask strategyTask = new StrategyTask();
						strategyTask.setCollectUuid(infoId);
						strategyTask.setStrategyUuid(strategy.getUuid());
						strategyTask.setTaskUuid(taskId);
						strategyTaskService.save(strategyTask);
					}
				}
			}
		}
		return strategy.getUuid();
	}

	@Override
	public StrategyDetailVO getStrategyDetail(String strategyId) {
		StrategyDetailVO vo = strategyMapper.getStrategyDetail(strategyId);
		if(StringUtils.isNotBlank(vo.getStrategyPlanStr())) {
			vo.setStrategyPlan(vo.getStrategyPlanStr().split(","));
		}
		return vo;
	}

	@Transactional
	@Override
	public void changeStrategyStatus(ChangeStatusDTO dto) {
		Strategy strategy = new Strategy();
		strategy.setUuid(dto.getStrategyId());
		strategy.setBusinessStatus(dto.getBusinessStatus());
		this.updateById(strategy);
	}

	@Transactional
	@Override
	public void delStrategy(DelStategyDTO dto) {
		Strategy strategy = new Strategy();
		strategy.setUuid(dto.getStrategyId());
		strategy.setIsDeleted(true);
		this.updateById(strategy);
		
	}

	@Override
	public List<StrategyListVO> getStrategyList(String strategyName) {
		return strategyMapper.getStrategyList(strategyName);
	}

	@Override
	public Map<String, Object> getStrategyInitData(String strategyId, String... refreshModule) {
		Map<String, Object> result = new HashMap<>();
		Set typeSet = null;
		if(CommonUtils.objNotEmpty(refreshModule)){
			typeSet = new HashSet(Arrays.asList(refreshModule));
		}
		// 机场
		if(typeSet == null || typeSet.contains("airport")){
//			result.put("airportList", iBotProspectAirportService.selectAirport());
			result.put("airportList", iBotProspectAirportService.lambdaQuery()
					.select(BotProspectAirport::getUuid, BotProspectAirport::getAirportName)
					.eq(BotProspectAirport::getIsDeleted, GlobalConstant.IS_DELETED_NO)
					.list());
		}
		// 航班
		if(typeSet == null || typeSet.contains("flight")){
			result.put("flightList", flightMapper.getListByStrategyId(strategyId));
		}
		// 对象
		if(typeSet == null || typeSet.contains("info")){
			result.put("infoTree", getInfoTree(strategyId));
		}
		// 障碍物
		if(typeSet == null || typeSet.contains("obstacle")){
			result.put("obstacleList", obstacleMapper.getList());
		}
		// 机型
		if(typeSet == null || typeSet.contains("uavType")){
			result.put("uavTypeList", iUavTypeService.lambdaQuery()
					.eq(UavType::getIsDeleted, GlobalConstant.IS_DELETED_NO)
					.select(UavType::getUuid, UavType::getModuleName)
					.list());
		}

		return result;
	}

	@Override
	public List<TreeOptionVo> getTaskTree(String strategyId, String infoId) {
		List<TreeOptionVo> result = new ArrayList<>();
		List<TaskSceneDto> taskSceneList = taskMapper.getTaskSceneByStrategyIdAndInfoId(strategyId, infoId);
		String sceneId = null;
		String taskId = null;
		Map<String, Object> extendParam = null;
		TreeOptionVo vo = null;
		List<String> flightIds = null;
		for (TaskSceneDto t: taskSceneList) {
			// 任务
			if(!t.getTaskId().equals(taskId)){
				if(taskId != null){
					extendParam.put("flightIds", flightIds);
					vo.setExtendParam(extendParam);
					result.add(vo);
				}
				taskId = t.getTaskId();
				flightIds = new ArrayList<>();
				extendParam = new HashMap<>();

				vo = new TreeOptionVo();
				vo.setKeyName(t.getTaskId());
				vo.setTitleName(t.getTaskName());
				vo.setParentName(t.getSceneId());
				extendParam.put("selected", t.getSelected());
			}
			flightIds.add(t.getFlightId());

			// 场景
			if(!t.getSceneId().equals(sceneId)){
				sceneId = t.getSceneId();

				TreeOptionVo parentVo = new TreeOptionVo();
				parentVo.setKeyName(t.getSceneId());
				parentVo.setTitleName(t.getSceneName());
				parentVo.setParentName("1");
				result.add(parentVo);
			}
		}
		if(taskId != null){
			extendParam.put("flightIds", flightIds);
			vo.setExtendParam(extendParam);
			result.add(vo);
		}

		return result;
	}

	/**
	 * 获取对象树
	 * @Author: shizt
	 * @Date: 2020/2/21 17:55
	 */
	private List<TreeOptionVo> getInfoTree(String strategyId){
		List<TreeOptionVo> result = new ArrayList();
		List<InfoTreeDto> infoDomainList = infoMapper.getInfoDomainList(strategyId);
		// 组装树
		String domainId = null;
		for (InfoTreeDto dto: infoDomainList) {
			// 对象
			TreeOptionVo vo = new TreeOptionVo();
			vo.setKeyName(dto.getInfoId());
			vo.setTitleName(dto.getInfoName());
			vo.setParentName(dto.getDomainId());
			Map<String, Object> extendParam = new HashMap<>();
			extendParam.put("lnglats", dto.getInfoLnglats());
			extendParam.put("lng", dto.getInfoLng());
			extendParam.put("lat", dto.getInfoLat());
			vo.setExtendParam(extendParam);
			result.add(vo);
			// 邻域
			if(!dto.getDomainId().equals(domainId)){
				domainId = dto.getDomainId();

				TreeOptionVo parentVo = new TreeOptionVo();
				parentVo.setKeyName(dto.getDomainId());
				parentVo.setTitleName(dto.getDomainName());
				parentVo.setParentName("1");
				result.add(parentVo);
			}
		}
		return result;
	}

	@Override
	public StrategySpecialVO getSpecialStrategyDetail(String strategyId) {
		StrategySpecialVO vo = strategyMapper.getSpecialStrategyDetail(strategyId);
//		Map<String,Object> param = new HashMap<String, Object>();
//		for(SpecialStrategyNodeVo node : vo.getNodes()) {
//			param.put("flightId", node.getFlight().getFlightId());
//			node.setFlight(strategyMapper.listFlight(param).get(0));
//		}
		return vo;
	}

	@Transactional
	@Override
	public String saveSpecialStrategy(StrategySpecialDto dto) {
		//保存策略
		Strategy strategy = new Strategy();
		strategy.setUuid(dto.getStrategyId());
		strategy.setStrategyName(dto.getStrategyName());
		strategy.setStrategyDesc(dto.getStrategyDesc());
		strategy.setBusinessStatus(dto.getBusinessStatus());
		strategy.setStrategyType(dto.getStrategyType());
		if("add".equals(dto.getMode())) {
			this.save(strategy);
		}
		if("update".equals(dto.getMode())) {
			this.updateById(strategy);
		}
//		this.saveOrUpdate(strategy);
		//删除计划
		 List<String> delUuids = dto.getDelUuids();
		 FlightWork work = new FlightWork();
		 work.setIsDeleted(true);
		 if(delUuids != null) {
			 for(String ids : delUuids) {
				 for(String id : ids.split(",")) {
					 work.setUuid(id);
					 workService.updateById(work);
				 }
			 }
		 }
		//保存新加的计划（作业）
		List<SpecialStrategyNodeDto> addNodes = dto.getAddNodes();
		if(addNodes != null) {
			for(SpecialStrategyNodeDto node : addNodes) {
				FlightWork flightWork = new FlightWork();
				flightWork.setFlightUuid(node.getFlightId());
				flightWork.setFlightDate(node.getFlyDate());
				flightWork.setWorkStatus("1");
				for(int i=0; i< node.getCount(); i++) {
					flightWork.setUuid(null);
					workService.save(flightWork);
				}
			}
		}
		return strategy.getUuid();
	}

	@Override
	public List<FlightDeatilVO> listFlight(String strategyId, String uuid) {
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("strategyId", strategyId);
		param.put("flightId", uuid);
		return strategyMapper.listFlight(param);
	}

	@Transactional
	@Override
	public String addFight(AddFlightDto dto) {
		//保存航班
		Flight flight = new Flight();
		flight.setFligntName(dto.getFlightName());
		flight.setStrategyId(dto.getStrategyId());
		flightService.save(flight);
		
		for(SceneWithIdsVO scene : dto.getScenes()) {
			if(scene.getInfoIds() != null && scene.getInfoIds().size()>0 && scene.getTaskIds() != null && scene.getTaskIds().size()>0) {
				for(String taskId : scene.getTaskIds()) {
					for(String infoId : scene.getInfoIds()) {
						//保存策略、对象 、任务关系
						List<StrategyTask> list = strategyTaskService.list(CommonUtils.getEqQueryWrapper("is_deleted", "strategy_uuid", "collect_uuid", "task_uuid",0, dto.getStrategyId(), infoId, taskId));
						if(list.size() == 0) {
							StrategyTask strategyTask = new StrategyTask();
							strategyTask.setCollectUuid(infoId);
							strategyTask.setStrategyUuid(dto.getStrategyId());
							strategyTask.setTaskUuid(taskId);
							strategyTaskService.save(strategyTask);
						}
						//保存航班、对象、任务关系
						List<FlightTask> list2 = flightTaskServcie.list(CommonUtils.getEqQueryWrapper("is_deleted", "flight_uuid", "collect_uuid", "task_uuid",0, flight.getUuid(), infoId, taskId));
						if(list2.size() == 0) {
							FlightTask flightTask = new FlightTask();
							flightTask.setCollectUuid(infoId);
							flightTask.setFlightUuid(flight.getUuid());
							flightTask.setTaskUuid(taskId);
							flightTaskServcie.save(flightTask);
						}
					}
				}
			}
		}
		
		
		return flight.getUuid();
	}

}
