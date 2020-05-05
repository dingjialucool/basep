package com.chinobot.cityle.base.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinobot.cityle.base.entity.DeptGrid;
import com.chinobot.cityle.base.mapper.DeptGridMapper;
import com.chinobot.cityle.base.service.IDeptGridService;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 部门网格关联 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-04-02
 */
@Service
public class DeptGridServiceImpl extends BaseService<DeptGridMapper, DeptGrid> implements IDeptGridService {

	@Autowired
	private DeptGridMapper deptGridtMapper;
	/**
	 * 获取已关联网格部门
	 */
	@Override
	public List<Map> getDeptGrid(Map paramDept) {
		// TODO Auto-generated method stub
		return deptGridtMapper.getDeptGrid(paramDept);
	}

}
