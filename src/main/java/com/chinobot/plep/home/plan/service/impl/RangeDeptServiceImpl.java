package com.chinobot.plep.home.plan.service.impl;

import com.chinobot.plep.home.plan.entity.RangeDept;
import com.chinobot.plep.home.plan.mapper.RangeDeptMapper;
import com.chinobot.plep.home.plan.service.IRangeDeptService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 范围部门关系表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-06-16
 */
@Service
public class RangeDeptServiceImpl extends BaseService<RangeDeptMapper, RangeDept> implements IRangeDeptService {
	@Autowired
	private RangeDeptMapper rangeDeptMapper;
	@Override
	public List<Map> getRangeGrid(Map paramDept) {
		return rangeDeptMapper.getRangeGrid(paramDept);
	}

}
