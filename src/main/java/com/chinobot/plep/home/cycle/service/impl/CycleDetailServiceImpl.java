package com.chinobot.plep.home.cycle.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.flyPlan.task.CycleTask;
import com.chinobot.plep.home.cycle.entity.CycleDetail;
import com.chinobot.plep.home.cycle.entity.dto.ChangeStateDto;
import com.chinobot.plep.home.cycle.entity.dto.CycleSearhDto;
import com.chinobot.plep.home.cycle.entity.dto.DeleteCycleDto;
import com.chinobot.plep.home.cycle.entity.dto.RouteSearchDto;
import com.chinobot.plep.home.cycle.entity.vo.CycleWithDetailVo;
import com.chinobot.plep.home.cycle.entity.vo.RouteVo;
import com.chinobot.plep.home.cycle.mapper.CycleDetailMapper;
import com.chinobot.plep.home.cycle.service.ICycleDetailService;
import com.chinobot.plep.home.routedd.entity.Cycle;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.entity.DispatchDetailPath;
import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.entity.DispatchDetailType;
import com.chinobot.plep.home.routedd.entity.UavDispatch;
import com.chinobot.plep.home.routedd.service.ICycleService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailPathService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailRouteService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailTypeService;
import com.chinobot.plep.home.routedd.service.IUavDispatchService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-10-25
 */
@Service
public class CycleDetailServiceImpl extends BaseService<CycleDetailMapper, CycleDetail> implements ICycleDetailService {

	@Autowired
	private CycleDetailMapper cycleDetailMapper;
	@Autowired
	private ICycleService cycleServcie;
	@Autowired
	private IUavDispatchService uavDispatchService;
	@Autowired
	private IDispatchDetailService dispatchDetailService;
	@Autowired
	private IDispatchDetailTypeService typeService;
	@Autowired
	private IDispatchDetailPathService detailPathService;
	@Autowired
	private IDispatchDetailRouteService detailRouteService;
	@Autowired
	private CycleTask cycleTask;
	
	@Override
	public IPage<Cycle> searchCyclePage(Page page, CycleSearhDto dto) {
		return cycleDetailMapper.searchCyclePage(page, dto);
	}

	@Override
	public IPage<RouteVo> searchRoutePage(Page page, RouteSearchDto dto) {
		IPage<RouteVo> searchRoute = cycleDetailMapper.searchRoute(page, dto);
		for(RouteVo vo : searchRoute.getRecords()) {
			vo.setList(cycleDetailMapper.getRouteDetaill(vo.getUuid()));
		}
		return searchRoute;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveCycle(CycleWithDetailVo vo) {
		vo.setOperateTime(null);
		vo.setDeptId(ThreadLocalUtil.getResources().getDeptId());
		cycleServcie.saveOrUpdate(vo);
		//删除明细
		CycleDetail cd = new CycleDetail();
		cd.setDataStatus("0");
		this.update(cd, CommonUtils.getEqUpdateWrapper("data_status","cycle_id", "1", vo.getUuid()));
		//id去重
		List<String> list = new ArrayList<String>();
		for(String id : vo.getList()) {
			if(StringUtils.isNotBlank(id) && !list.contains(id)) {
				list.add(id);
			}
		}
		for(String id : list) {
			CycleDetail cycleDetail = new CycleDetail();
			cycleDetail.setCycleId(vo.getUuid());
			cycleDetail.setUavDspId(id);
			this.save(cycleDetail);
		}
		//立即触发一次
		cycleTask.addFlyTask();
	}

	@Override
	public CycleWithDetailVo getCycleVo(String uuid) {
		CycleWithDetailVo cycleVo = cycleDetailMapper.getCycleVo(uuid);
		cycleVo.setList(cycleDetailMapper.getDetailDspIds(uuid));
		return cycleVo;
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void changeState(ChangeStateDto dto) {
		Cycle cycle = new Cycle();
		cycle.setUuid(dto.getUuid());
		cycle.setUseStatus(dto.getState());
		cycleServcie.updateById(cycle);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteCycle(DeleteCycleDto dto) {
		Cycle cycle = new Cycle();
		cycle.setUuid(dto.getUuid());
		cycle.setDataStatus("0");
		cycleServcie.updateById(cycle);
		if(dto.isDelAll()) {
			//删除所有
			deltaskOfCycle(dto.getUuid());
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void buildTask(String uavDspId, LocalDate flyTime, String cycleId) {
		UavDispatch uavDispatch = uavDispatchService.getById(uavDspId);
		uavDispatch.setUuid(null);
		uavDispatch.setOperateBy("system");
		uavDispatch.setCreateBy("system");
		uavDispatch.setCreateTime(null);
		uavDispatch.setOperateTime(null);
		uavDispatch.setTime(flyTime);
		uavDispatch.setCycleId(cycleId);
		uavDispatchService.save(uavDispatch);
		List<DispatchDetail> dispatchDetails = dispatchDetailService.list(CommonUtils.getEqQueryWrapper("data_status","uav_dsp_id", "1", uavDspId));
		for(DispatchDetail dispatchDetail : dispatchDetails) {
			List<DispatchDetailType> types = typeService.list(CommonUtils.getEqQueryWrapper("data_status","dspdt_id", "1", dispatchDetail.getUuid()));
			List<DispatchDetailPath> paths = detailPathService.list(CommonUtils.getEqQueryWrapper("data_status","dspdt_id", "1", dispatchDetail.getUuid()));
			List<DispatchDetailRoute> routes = detailRouteService.list(CommonUtils.getEqQueryWrapper("data_status","dspdt_id", "1", dispatchDetail.getUuid()));
			dispatchDetail.setUuid(null);
			dispatchDetail.setOperateBy("system");
			dispatchDetail.setCreateBy("system");
			dispatchDetail.setCreateTime(null);
			dispatchDetail.setOperateTime(null);
			dispatchDetail.setUavDspId(uavDispatch.getUuid());
			dispatchDetailService.save(dispatchDetail);
			for(DispatchDetailType type : types) {
				type.setUuid(null);
				type.setOperateBy("system");
				type.setCreateBy("system");
				type.setCreateTime(null);
				type.setOperateTime(null);
				type.setDspdtId(dispatchDetail.getUuid());
				typeService.save(type);
			}
			for(DispatchDetailPath path : paths) {
				path.setUuid(null);
				path.setOperateBy("system");
				path.setCreateBy("system");
				path.setCreateTime(null);
				path.setOperateTime(null);
				path.setDspdtId(dispatchDetail.getUuid());
				detailPathService.save(path);
			}
			for(DispatchDetailRoute route : routes) {
				route.setUuid(null);
				route.setOperateBy("system");
				route.setCreateBy("system");
				route.setCreateTime(null);
				route.setOperateTime(null);
				route.setDspdtId(dispatchDetail.getUuid());
				detailRouteService.save(route);
			}
		}
	}
	
	@Transactional(rollbackFor = Exception.class)
	private void deltaskOfCycle(String uuid) {
		QueryWrapper queryWrapper = CommonUtils.getEqQueryWrapper("data_status","cycle_id", "1", uuid);
		queryWrapper.gt("time", LocalDate.now());
		List<UavDispatch> list = uavDispatchService.list(queryWrapper);
		for(UavDispatch uavDispatch : list) {
			uavDispatchService.cleanOneDay(uavDispatch);
		}
		
	}
}
