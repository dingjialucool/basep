package com.chinobot.aiuas.bot_collect.warning.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.aiuas.bot_collect.warning.Constant.GlobalConstant;
import com.chinobot.aiuas.bot_collect.warning.entity.EventFlow;
import com.chinobot.aiuas.bot_collect.warning.entity.EventInfo;
import com.chinobot.aiuas.bot_collect.warning.entity.Feedback;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.aiuas.bot_collect.warning.entity.WarningInfo;
import com.chinobot.aiuas.bot_collect.warning.entity.dto.DoneDTO;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.DistributionLiveListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventInfoTypeListVo;
import com.chinobot.aiuas.bot_collect.warning.entity.vo.EventTraceListVo;
import com.chinobot.aiuas.bot_collect.warning.mapper.EventInfoMapper;
import com.chinobot.aiuas.bot_collect.warning.mapper.WarningMapper;
import com.chinobot.aiuas.bot_collect.warning.service.IEventFlowService;
import com.chinobot.aiuas.bot_collect.warning.service.IEventInfoService;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningInfoService;
import com.chinobot.aiuas.bot_collect.warning.service.IWarningService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 预警事件 服务实现类
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
@Service
public class EventInfoServiceImpl extends BaseService<EventInfoMapper, EventInfo> implements IEventInfoService {

	@Autowired
	private IEventInfoService eventInfoService;
	@Autowired
	private IEventFlowService eventFlowService;
	@Autowired
	private EventInfoMapper eventInfoMapper;
	@Autowired
	private IWarningService warningService;
	@Autowired
	private WarningMapper warningMapper;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IWarningInfoService warningInfoService;
	
	
	@Override
	public Result quantityDis(List<String> idList, String personId) {
		Result result = new Result();
		//预警事件批量分派保存
		List<EventInfo> list =new ArrayList<EventInfo>();
		EventInfo eventInfo = null;
		for (String uuid : idList) {
			eventInfo = new EventInfo();
			eventInfo.setUuid(uuid);
			eventInfo.setBusinessStatus(GlobalConstant.TO_BE_CONFIRMED);
			eventInfo.setHostBy(personId);
			list.add(eventInfo);
		}
		boolean bo = eventInfoService.updateBatchById(list);
		if(bo) {
			result.setMsg("分派成功!");
			result.setCode(200);
		}else {
			result.setMsg("分派失败!");
			result.setCode(200);
		}
		//向预警事件流转记录表中添加一条分派记录
		List<EventFlow> eventFlowList = new ArrayList<EventFlow>();
		EventFlow eventFlow = new EventFlow();
		for (String uuid : idList) {
			eventFlow = new EventFlow();
			eventFlow.setEventUuid(uuid);
			//根据预警事件的主键查出-->主办人(发送人)+操作时间(发送时间)
			EventInfo infoById = eventInfoService.getById(uuid);
			eventFlow.setSendBy(infoById.getCreateBy());
			eventFlow.setSendTime(infoById.getCreateTime());
			eventFlowList.add(eventFlow);
		}
		eventFlowService.saveBatch(eventFlowList);
		return result;
	}

	@Override
	public List<Map> getFlyPerson(Map param) {
		
		return eventInfoMapper.getFlyPerson(param);
	}

	@Override
	public List<Map> getUav(Map param) {
		
		return eventInfoMapper.getUav(param);
	}

	@Override
	public boolean batchRevocation(List<String> idList, String personId) {
		
		int num = 0;
		for (String eventId : idList) {
			 num = warningService.updateStatus(eventId, personId,"","", GlobalConstant.REVOKED,"撤销成功",GlobalConstant.TO_BE_CONFIRMED);
		}
		if(num>0) {
			return true;
		}
		return false;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Result eventCheck(String hostBy, String uuid,String fileId,String idea,String businessStatus) {
		
		Result result = new Result();
		
		Map param = new HashMap();
		param.put("businessStatus", businessStatus);
		param.put("uuid", uuid);
		
		EventFlow eventFlow = warningMapper.getSendAndSendTime(uuid,GlobalConstant.TO_BE_CHECKED);
		eventFlow.setHostBy(hostBy);
		eventFlow.setHostIdea(idea);
		eventFlow.setBusinessStatus(GlobalConstant.TO_BE_COMPLETED);
		//添加预警事件流转记录表的业务状态记录
		boolean bo = warningService.updateBFBS(eventFlow);
		
		//保存
		if (StringUtils.isNotEmpty(fileId)) {
			FileBus filebus = new FileBus();
			filebus.setBusId(eventFlow.getUuid());
			filebus.setFileId(fileId);
			filebus.setModule(GlobalConstant.HCMODULE_FILE);
			filebus.setSort(0);
			fileBusService.save(filebus);
		}
		
		//修改预警事件表的业务状态
		int upBFBS=warningService.updateBIBS(param);
		
		if(bo) {
			result.setMsg("核查成功!");
		}else {
			result.setMsg("核查失败!");
		}
		result.setCode(200);
		
		return result;
	}

	@Override
	public Result eventDone(String hostBy, String uuid,String fileId, String idea,String businessStatus) {
		
		Result result = new Result();
		
		Map param = new HashMap();
		param.put("businessStatus", businessStatus);
		param.put("hostBy", hostBy);
		param.put("uuid", uuid);
		//修改预警事件表的业务状态
		int upBFBS=warningService.updateBIBS(param);
		
		EventFlow eventFlow = warningMapper.getSendAndSendTime(uuid,GlobalConstant.TO_BE_COMPLETED);
		eventFlow.setHostBy(hostBy);
		eventFlow.setHostIdea(idea);
		eventFlow.setBusinessStatus(GlobalConstant.COMPLETED);
		//添加预警事件流转记录表的业务状态记录
		boolean bo = warningService.updateBFBS(eventFlow);
		
		//保存文件
		if (StringUtils.isNotEmpty(fileId)) {
			FileBus filebus = new FileBus();
			filebus.setBusId(eventFlow.getUuid());
			filebus.setFileId(fileId);
			filebus.setModule(GlobalConstant.BJMODULE_FILE);
			filebus.setSort(0);
			fileBusService.save(filebus);
		}
		
		if(bo) {
			result.setMsg("办结成功!");
		}else {
			result.setMsg("办结失败!");
		}
		result.setCode(200);
		
		return result;
	}

	@Override
	public IPage<EventInfoListVo> getClueList(Page page, Map<String, Object> param) {
		
		IPage<EventInfoListVo> cluePage = eventInfoMapper.getClueList(page,param);
		List<EventInfoListVo> eventList = cluePage.getRecords();
		
		List<EventInfoListVo> eventListTemp = new ArrayList<EventInfoListVo>();
		
		String deptId = (String) param.get("deptId");
		List buttonList = null;
		if(StringUtils.isNotEmpty(deptId)) {
			//根据部门、业务状态-->用户可以看到的操作
			for (EventInfoListVo vo : eventList) {
				buttonList = new ArrayList<String>();
				if (deptId.equals(vo.getFfDeptId()) && GlobalConstant.TO_BE_CONFIRMED.equals(vo.getBusinessStatus())) {
					buttonList.add("2");
					buttonList.add("1");
					eventListTemp.add(vo);
				}
				if (deptId.equals(vo.getJyDeptId())) {
					buttonList.add("1");
					eventListTemp.add(vo);
				}
				vo.setButtonType(buttonList);
			}
		}
			cluePage.setRecords(eventListTemp);
			cluePage.setSize(eventListTemp.size());
		return cluePage;
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
	public IPage<EventInfoTypeListVo> getHistoryEventAndClueList(Page page, Map<String, Object> param) {
		
		if(param.get("businessStatus")!=null && param.get("businessStatus")!="" ) {
        	param = toParam(param,"businessStatus");
        }
		return eventInfoMapper.getHistoryEventAndClueList(page,param);
	}

	@Override
	public IPage<EventInfoTypeListVo> getHistoryClueList(Page page, Map<String, Object> param) {

		return eventInfoMapper.getHistoryClueList(page,param);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeWarningMessage(String warnId) {
		//预警删除
		warningService.update(new LambdaUpdateWrapper<Warning>().eq(Warning::getUuid, warnId).set(Warning::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//删除预警事件关联表
		WarningInfo warningInfo = warningInfoService.getOne(new LambdaUpdateWrapper<WarningInfo>().eq(WarningInfo::getWarningUuid, warnId));
		if (warningInfo == null) {
			return ;
		}
		warningInfoService.update(new LambdaUpdateWrapper<WarningInfo>().eq(WarningInfo::getUuid, warningInfo.getUuid()).set(WarningInfo::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//删除事件
		eventInfoService.update(new LambdaUpdateWrapper<EventInfo>().eq(EventInfo::getUuid, warningInfo.getEventUuid()).set(EventInfo::getIsDeleted, PartConstant.DATA_YES_DELETE));
	}

}
