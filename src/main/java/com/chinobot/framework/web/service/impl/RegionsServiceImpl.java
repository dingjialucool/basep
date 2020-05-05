package com.chinobot.framework.web.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.entity.Regions;
import com.chinobot.framework.web.mapper.RegionsMapper;
import com.chinobot.framework.web.service.IRegionsService;

/**
 * <p>
 * 行政区划表（2018） 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-05-16
 */
@Service
public class RegionsServiceImpl extends BaseService<RegionsMapper, Regions> implements IRegionsService {

	@Override
	public String getRegionsCode(String name) {
		QueryWrapper<Regions> queryWrapper = new QueryWrapper<Regions>();
		queryWrapper.eq("name", name);
		Regions regions = super.getOne(queryWrapper);
		if(CommonUtils.isObjEmpty(regions)) {
			queryWrapper = new QueryWrapper<Regions>();
			queryWrapper.like("name", name);
			regions = super.getOne(queryWrapper);
		}
		
		return CommonUtils.objNotEmpty(regions)?regions.getCode():"";
	}

}
