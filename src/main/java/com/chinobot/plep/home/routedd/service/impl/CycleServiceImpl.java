package com.chinobot.plep.home.routedd.service.impl;

import com.chinobot.plep.home.routedd.entity.Cycle;
import com.chinobot.plep.home.routedd.entity.UavDispatch;
import com.chinobot.plep.home.routedd.entity.dto.CycleDto;
import com.chinobot.plep.home.routedd.entity.dto.CycleLeDto;
import com.chinobot.plep.home.routedd.entity.vo.CyclesVo;
import com.chinobot.plep.home.routedd.entity.vo.PageAndCyclesVo;
import com.chinobot.plep.home.routedd.mapper.CycleMapper;
import com.chinobot.plep.home.routedd.service.ICycleService;
import com.chinobot.plep.home.routedd.service.IUavDispatchService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 周期计划表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-10-23
 */
@Service
public class CycleServiceImpl extends BaseService<CycleMapper, Cycle> implements ICycleService {

	@Autowired
	private CycleMapper cycleMapper;
	@Autowired
	private ICycleService cycleService;
	@Autowired
	private IUavDispatchService uavDispatchService;
	
	@Override
	public PageAndCyclesVo getCycles(Page page, CycleDto dto) {
		
		if(dto.getPlayStatus()!=null && dto.getPlayStatus()!="" ) {
        	String str = dto.getPlayStatus();
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		dto.setSplit(split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				dto.setSplit(split);
			}
        }
		
		IPage<CyclesVo> cyclesPage = cycleMapper.getCycles(page,dto);
		PageAndCyclesVo vo = new PageAndCyclesVo();
		vo.setList(cyclesPage.getRecords());
		vo.setTotal(cyclesPage.getTotal());
		return vo;
	}

	@Override
	public CyclesVo getCycle(String uuid) {
		
		return cycleMapper.getCycle(uuid);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delCycle(String uuid) {
		
		Cycle cycle = cycleService.getById(uuid);
//		UavDispatch uavDispatch = new UavDispatch();
//		uavDispatch.setUavId(cycle.getUavDspId());
		//删除计划
//		uavDispatchService.cleanOneDay(uavDispatch);
		//删除计划周期
		cycle.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
		
		return cycleService.updateById(cycle);
	}

	@Override
	public List<CycleLeDto> getCyclePlans() {

		return cycleMapper.getCyclePlans();
	}
}
