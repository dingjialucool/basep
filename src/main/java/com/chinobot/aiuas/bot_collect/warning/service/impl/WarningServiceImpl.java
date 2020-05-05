package com.chinobot.aiuas.bot_collect.warning.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.constant.TaskConstant;
import com.chinobot.aiuas.bot_collect.task.entity.dto.UnitDto;
import com.chinobot.aiuas.bot_collect.task.entity.vo.UnitVo;
import com.chinobot.aiuas.bot_collect.task.service.IUnitService;
import com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant;
import com.chinobot.aiuas.bot_collect.warning.entity.EventFlow;
import com.chinobot.aiuas.bot_collect.warning.entity.Feedback;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.DoneDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.FileMessageDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventFeedbackVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceLiveVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.HostMessageVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.HistoryClueVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.SuperviseVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.WarningVo;
import com.chinobot.aiuas.bot_collect.warning.mapper.WarningMapper;
import com.chinobot.aiuas.bot_collect.warning.service.IEventFlowService;
import com.chinobot.aiuas.bot_collect.warning.service.IFeedbackService;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningService;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultItemVo;
import com.chinobot.aiuas.bot_prospect.flight.entity.vo.CollectResultVo;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.dataset.entity.WhiteList;
import com.chinobot.plep.home.dataset.service.IWhiteListService;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 预警信息 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-13
 */
@Service
public class WarningServiceImpl extends BaseService<WarningMapper, Warning> implements IWarningService {

	@Autowired
	private WarningMapper warningMapper;
	@Autowired
	private IWarningService iWarningService;
	@Autowired
	private IWhiteListService whiteListService;
	@Autowired
	private IEventFlowService eventFlowService;
	@Autowired
    private IUnitService iUnitService;
	@Autowired
	private IFileBusService fileBusService;
	
	
	@Override
	public IPage<WarningListVo> getWarningList(Page page,Map<String, Object> param) {
		if(param.get("businessStatus")!=null && param.get("businessStatus")!="" ) {
        	param = toParam(param,"businessStatus");
        }
		return warningMapper.getWarningList(page,param);
	}

	private Map toParam(Map param, String type) {
		
    	String str = (String) param.get(type);
    	if(str.indexOf(',')>0) {
    		String[] split = str.split(",");
    		param.put(type, split);
    	}else {
			String[] split = new String[1];
			split[0] = str;
			param.put(type, split);
		}
		return param;
	}

	
	@Override
	public WarningVo getWarningDetail(String uuid) {
		WarningVo warningDetail = warningMapper.getWarningDetail(uuid);
		
		//获取预警信息视频或图片
		Map<String,String> param = new HashMap<>();
		param.put("busId", uuid);
		//检查图
		param.put("module",GlobalConstant.WARN_IMG);
		List<String> warnImgList = warningMapper.getWarnByBusId(param);
		if(warnImgList.size()>0) {
			warningDetail.setWarnImg(warnImgList.get(0));
		}
		//基准图
		param.put("module",GlobalConstant.BASE_IMG_WARN);
		List<String> warnBaseImgList = warningMapper.getWarnByBusId(param);
		if(warnBaseImgList.size()>0) {
			warningDetail.setWarnBasicImg(warnBaseImgList.get(0));
		}
		//视频
		param.put("module",GlobalConstant.WARN_VED);
		List<String> warnVidList = warningMapper.getWarnByBusId(param);
		if(warnVidList.size()>0) {
			warningDetail.setWarnVio(warnVidList.get(0));
		}
		
		 //根据任务获取防范单位、治理单位、救援单位
		 Map unitParam = new HashMap();
	     unitParam.put("taskUuid", warningDetail.getTaskId());
	     List<UnitDto> units = iUnitService.getUtilList(unitParam);
	     for (UnitDto u: units) {
	         if(u.getUnitType().equals(TaskConstant.UNIT_TYPE_MANAGE)){
	        	 warningDetail.setFfDept(u.getOrganization());
	         }else if(u.getUnitType().equals(TaskConstant.UNIT_TYPE_AGAINST)){
	        	 warningDetail.setZlDept(u.getOrganization());
	         }else if(u.getUnitType().equals(TaskConstant.UNIT_TYPE_RELIEF)){
	        	 warningDetail.setJyDept(u.getOrganization());
	         }
	     }
		
		return warningDetail;
	}

	@Override
	public IPage<EventInfoListVo> getDistributionLiveList(Page page, Map<String, Object> param) {
		
		if(param.get("businessStatus")!=null && param.get("businessStatus")!="" ) {
        	param = toParam(param,"businessStatus");
        }
		if(param.get("eventType")!=null && param.get("eventType")!="" ) {
        	param = toParam(param,"eventType");
        }
		return warningMapper.getDistributionLiveList(page,param);
	}

	@Override
	public IPage<EventTraceListVo> geteventTraceList(Page page, Map<String, Object> param) {
		
		if(param.get("businessStatus")!=null && param.get("businessStatus")!="" ) {
        	param = toParam(param,"businessStatus");
        }
		
		IPage<EventTraceListVo> geteventTraceList = warningMapper.geteventTraceList(page,param);
		List<EventTraceListVo> list = geteventTraceList.getRecords();
		
		List<EventTraceListVo> eventList = new ArrayList<EventTraceListVo>();
		
		String deptId = (String) param.get("deptId");
		List buttonList = null;
		if(StringUtils.isNotEmpty(deptId)) {
			//根据部门、业务状态-->用户可以看到的操作
			for (EventTraceListVo vo : list) {
				buttonList = new ArrayList<String>();
				if (deptId.equals(vo.getFfDeptId()) && GlobalConstant.TO_BE_GOVERNED.equals(vo.getBusinessStatus())) {
					buttonList.add("2");
					buttonList.add("1");
					eventList.add(vo);
				}
				if (deptId.equals(vo.getFfDeptId()) && GlobalConstant.TO_BE_CHECKED.equals(vo.getBusinessStatus())) {
					buttonList.add("4");
					buttonList.add("1");
					eventList.add(vo);
				}
				if (deptId.equals(vo.getFfDeptId()) && GlobalConstant.TO_BE_COMPLETED.equals(vo.getBusinessStatus())) {
					buttonList.add("5");
					buttonList.add("1");
					eventList.add(vo);
				}
//				if (deptId.equals(vo.getZlDeptId()) && GlobalConstant.TO_BE_GOVERNED.equals(vo.getBusinessStatus())) {
//					buttonList.add("3");
//					buttonList.add("1");
//					eventList.add(vo);
//				}
				if (deptId.equals(vo.getFfDeptId()) && GlobalConstant.TO_BE_CONFIRMED.equals(vo.getBusinessStatus())) {
					buttonList.add("6");
					buttonList.add("1");
					eventList.add(vo);
				}
				if (deptId.equals(vo.getZlDeptId()) && GlobalConstant.TO_BE_GOVERNED.equals(vo.getBusinessStatus())) {
					buttonList.add("7");
					buttonList.add("1");
					eventList.add(vo);
				}
				if (deptId.equals(vo.getJyDeptId())) {
					buttonList.add("1");
					eventList.add(vo);
				}
				
				vo.setButtonType(buttonList);
			}
			
				Set<EventTraceListVo> userSet = new HashSet<>(eventList);
				List<EventTraceListVo> tempList = new ArrayList<>(userSet);
				geteventTraceList.setRecords(tempList);
			}
			
		return geteventTraceList;
	}

	@Override
	public IPage<EventFeedbackListVo> getEventFeedbackList(Page page, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return warningMapper.getEventFeedbackList(page, param);
	}

	@Override
	public DistributionLiveVo getDistributionLiveDetail(String uuid,String warnId) {
		
		DistributionLiveVo distributionLiveDetail = warningMapper.getDistributionLiveDetail(uuid);
		//获取预警信息视频或图片
		Map<String,String> param = new HashMap<>();
		//基准图
		param.put("busId", warnId);
		param.put("module",GlobalConstant.BASE_IMG_WARN);
		List<String> warnBaseImgList = warningMapper.getWarnByBusId(param);
		if(warnBaseImgList.size()>0) {
			distributionLiveDetail.setBaseImg(warnBaseImgList.get(0));
		}
		//检查图
		param.put("busId", uuid);
		param.put("module",GlobalConstant.YJMODULE_IMG);
		List<String> warnImgList = warningMapper.getWarnByBusId(param);
		if(warnImgList.size()>0) {
			distributionLiveDetail.setEventImg(warnImgList.get(0));
			distributionLiveDetail.setFileId(warnImgList.get(0));
		}
		//视频
		param.put("module",GlobalConstant.YJMODULE_VEDIO);
		List<String> warnVidList = warningMapper.getWarnByBusId(param);
		if(warnVidList.size()>0) {
			distributionLiveDetail.setEventVio(warnVidList.get(0));
		}
		
		//线索数量监测
		if ("2".equals(distributionLiveDetail.getWarningType())) {
			List<CollectResultVo> result = warningMapper.getResultByWarnId(distributionLiveDetail.getHbuuid(), distributionLiveDetail.getObjectId());
			for (CollectResultVo collectResultVo : result) {
				List<CollectResultItemVo> removeDuplicateResult = removeDuplicateResult(collectResultVo.getList());
				collectResultVo.setList(removeDuplicateResult);
			}
			distributionLiveDetail.setResultVo(result);
		}
		
		return distributionLiveDetail;
	}
	
	/**
	 * 过滤掉重复数据
	 * @param users
	 * @return
	 */
	public static List<CollectResultItemVo> removeDuplicateResult(List<CollectResultItemVo> items){
        Set<CollectResultItemVo> set = new TreeSet<CollectResultItemVo>((item1, item2) -> item1.getRsKey().compareTo(item2.getRsKey()));
        set.addAll(items);
        return new ArrayList<>(set);
    }
	
	
	@Override
	public EventTraceLiveVo geteventTraceDetail(String uuid,String warnId) {
		
		EventTraceLiveVo geteventTraceDetail = warningMapper.geteventTraceDetail(uuid);
		
		//获取预警信息视频或图片
		Map<String,String> param = new HashMap<>();
		
		//基准图
		param.put("busId", warnId);
		param.put("module",GlobalConstant.BASE_IMG_WARN);
		List<String> warnBaseImgList = warningMapper.getWarnByBusId(param);
		if(warnBaseImgList.size()>0) {
			geteventTraceDetail.setWarnBaseImg(warnBaseImgList.get(0));
		}
		
		//检查图
		param.put("busId", uuid);
		param.put("module",GlobalConstant.YJMODULE_IMG);
		List<String> warnImgList = warningMapper.getWarnByBusId(param);
		if(warnImgList.size()>0) {
			geteventTraceDetail.setWarnImg(warnImgList.get(0));
		}
		//预警视频
		param.put("module",GlobalConstant.YJMODULE_VEDIO);
		List<String> warnVidList = warningMapper.getWarnByBusId(param);
		if(warnVidList.size()>0) {
			geteventTraceDetail.setWarnVio(warnVidList.get(0));
		}
		
		//主办信息
		Map messageMap = new HashMap<>();
		List<HostMessageVo> voList = geteventTraceDetail.getHostMessageVo();
		for (HostMessageVo message: voList) {
			messageMap.put("busId", message.getHostId());
			List<FileMessageDTO> fileVo = warningMapper.getFileMessage(messageMap);
			message.setFileVo(fileVo);
		}
		
		//线索数量监测
		if ("2".equals(geteventTraceDetail.getWarningType())) {
			List<CollectResultVo> result = warningMapper.getResultByWarnId(geteventTraceDetail.getHbuuid(), geteventTraceDetail.getObjectId());
			for (CollectResultVo collectResultVo : result) {
				List<CollectResultItemVo> removeDuplicateResult = removeDuplicateResult(collectResultVo.getList());
				collectResultVo.setList(removeDuplicateResult);
			}
			geteventTraceDetail.setResultVo(result);
		}
		
		return geteventTraceDetail;
	}

	@Override
	public EventFeedbackVo getEventFeedbackDetail(String uuid) {
		// TODO Auto-generated method stub
		return warningMapper.getEventFeedbackDetail(uuid);
	}

	@Override
	public int updateBIBS(Map param) {
		// TODO Auto-generated method stub
		return warningMapper.updateBIBS(param);
	}

	@Override
	public boolean updateBFBS(EventFlow eventFlow) {
		
		return eventFlowService.save(eventFlow);
	}
	
	@Override
	public int updateWI(String businessStatus,String uuid) {
		// TODO Auto-generated method stub
		return warningMapper.updateWI(businessStatus,uuid);
	}

	@Override
	public Map<String, Object> getBotEventInfo(String uuid) {
		// TODO Auto-generated method stub
		return warningMapper.getBotEventInfo(uuid);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Result updateEvent(String hostBy, String uuid) {
		
		Result result=new Result();
		Map param = new HashMap();
		param.put("businessStatus", GlobalConstant.REVOKED);
//		param.put("hostBy", hostBy);
		param.put("uuid", uuid);
		//修改预警事件表的业务状态
		int upBFBS=iWarningService.updateBIBS(param);
		
		//通过事件id获取预警流转记录表中的发送人，发送时间
		EventFlow eventFlow = warningMapper.getSendAndSendTime(uuid,GlobalConstant.TO_BE_CONFIRMED);
		eventFlow.setHostBy(hostBy);
		eventFlow.setHostIdea("撤销成功");
		eventFlow.setBusinessStatus(GlobalConstant.REVOKED);
		//添加预警事件流转记录表的业务状态
		iWarningService.updateBFBS(eventFlow);
		if(upBFBS>0 && upBFBS>0) {
			result.setMsg("撤销成功!");
			result.setCode(200);
		}else {
			result.setMsg("撤销失败!");
			result.setCode(200);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Result addWhiteList(String uuid, LocalDate invalidTime,String hostBy) {
		
		//往白名单中添加一条数据
		WhiteList whiteList=new WhiteList();
		Result result = new Result();
		whiteList.setEventId(uuid);
		whiteList.setInvalidTime(invalidTime);
		
		boolean bo = whiteListService.saveOrUpdate(whiteList);
		if(bo) {
			result.setMsg("新增成功!");
		}else {
			result.setMsg("新增失败!");
		}
		result.setCode(200);
		
		/**
		 * 预警消息中的业务状态改为白名单状态
			1.先根据预警事件主键查出预警消息id
			2.通过预警消息主键将业务状态改为白名单状态
		 */
		Warning warning = warningMapper.getWarnByEventId(uuid);
		if (warning != null) {
			warning.setBusinessStatus("3");
			iWarningService.updateById(warning);
		}
		
		//改变预警事件表中状态为白名单状态 且 需要增加 预警流转记录中的状态
		iWarningService.updateStatus(uuid, hostBy,"","", GlobalConstant.WHITE_LIST,"添加白名单成功",GlobalConstant.TO_BE_CONFIRMED);
		
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int updateStatus(String uuid, String hostBy, String fileId,String module,String businessStatus,String idea,String beforeStatus) {
		
		Map param = new HashMap();
		param.put("businessStatus", businessStatus);
//		param.put("hostBy", hostBy);
		param.put("uuid", uuid);
		//修改预警事件表的业务状态
		int upBIBS=iWarningService.updateBIBS(param);
		//改预警事件流转记录表的业务状态
		//通过事件id获取预警流转记录表中的发送人，发送时间
		EventFlow eventFlow = warningMapper.getSendAndSendTime(uuid,beforeStatus);
		eventFlow.setHostBy(hostBy);
		eventFlow.setHostIdea(idea);
		eventFlow.setBusinessStatus(businessStatus);
		//添加预警事件流转记录表的业务状态
		iWarningService.updateBFBS(eventFlow);
		
		//保存文件
		if (StringUtils.isNotEmpty(fileId)) {
			FileBus filebus = new FileBus();
			filebus.setBusId(eventFlow.getUuid());
			filebus.setFileId(fileId);
			filebus.setModule(module);
			filebus.setSort(0);
			fileBusService.save(filebus);
		}
		
		return upBIBS;
	}

	@Override
	public HistoryClueVo getHistoryClueDetail(String uuid,String warnId) {
		
		HistoryClueVo historyClueDetail = warningMapper.getHistoryClueDetail(uuid);
		
		Map<String,String> param = new HashMap<>();
		
		//基准图
		param.put("busId", warnId);
		param.put("module",GlobalConstant.BASE_IMG_WARN);
		List<String> warnBaseImgList = warningMapper.getWarnByBusId(param);
		if(warnBaseImgList.size()>0) {
			historyClueDetail.setWarnBaseImg(warnBaseImgList.get(0));
		}
		
		//检查图
		param.put("busId", uuid);
		param.put("module",GlobalConstant.YJMODULE_IMG);
		List<String> warnImgList = warningMapper.getWarnByBusId(param);
		if(warnImgList.size()>0) {
			historyClueDetail.setWarnImg(warnImgList.get(0));
		}
		//预警视频
		param.put("module",GlobalConstant.YJMODULE_VEDIO);
		List<String> warnVidList = warningMapper.getWarnByBusId(param);
		if(warnVidList.size()>0) {
			historyClueDetail.setWarnVio(warnVidList.get(0));
		}
		
		param.put("busId", uuid);
		//确认文件
		param.put("module",GlobalConstant.QRMODULE_FILE);
		List<FileMessageDTO> fileMessageList = warningMapper.getFileMessage(param);
		if(fileMessageList.size()>0) {
			historyClueDetail.setQrFileList(fileMessageList);
		}
		
		//线索数量监测
		if ("2".equals(historyClueDetail.getWarningType())) {
			List<CollectResultVo> result = warningMapper.getResultByWarnId(historyClueDetail.getHbuuid(), historyClueDetail.getObjectId());
			for (CollectResultVo collectResultVo : result) {
				List<CollectResultItemVo> removeDuplicateResult = removeDuplicateResult(collectResultVo.getList());
				collectResultVo.setList(removeDuplicateResult);
			}
			historyClueDetail.setResultVo(result);
		}
		
		return historyClueDetail;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean eventToDone(DoneDTO dto) {
		
		Map param = new HashMap();
		param.put("businessStatus", dto.getStatus());
		param.put("uuid", dto.getUuid());
		//修改预警事件表的业务状态
		int upBIBS=iWarningService.updateBIBS(param);
		//改预警事件流转记录表的业务状态
		//通过事件id获取预警流转记录表中的发送人，发送时间
		EventFlow eventFlow = warningMapper.getSendAndSendTime(dto.getUuid(),GlobalConstant.TO_BE_GOVERNED);
		eventFlow.setHostBy(dto.getHostBy());
		eventFlow.setHostIdea(dto.getHostIdea());
		eventFlow.setBusinessStatus(GlobalConstant.TO_BE_CHECKED);
		//添加预警事件流转记录表的业务状态
		iWarningService.updateBFBS(eventFlow);
		
		//保存文件
		if (StringUtils.isNotEmpty(dto.getFileId())) {
			FileBus filebus = new FileBus();
			filebus.setBusId(eventFlow.getUuid());
			filebus.setFileId(dto.getFileId());
			filebus.setModule(GlobalConstant.ZLMODULE_FILE);
			filebus.setSort(0);
			fileBusService.save(filebus);
		}
		
		
		return true;
	}
}
