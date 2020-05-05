package com.chinobot.plep.home.plan.service;

import com.chinobot.plep.home.plan.entity.Range;
import com.chinobot.plep.home.plan.entity.vo.VoAddressBaseRangeGrids;
import com.chinobot.plep.home.plan.entity.vo.VoAreaPathParm;
import com.chinobot.plep.home.plan.entity.vo.VoPathPlan;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 巡查范围表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-06-16
 */
public interface IRangeService extends IBaseService<Range> {
	
	IPage<VoAddressBase<Map>> getRangeAddrPage(Page page, Map<String, Object> param);

	Result editRange(VoAddressBaseRangeGrids<Range> vo) throws Exception;

	Result delRange(Range range);
	
	VoAddressBase<Map> getRangeAddrById(String uuid);

	void test() throws Exception;

	boolean saveArea(VoAreaPathParm vo);

	boolean saveConfig(VoPathPlan vo);

	boolean deleteAll(Map<String, Object> param);
}
