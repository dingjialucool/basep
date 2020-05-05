package com.chinobot.plep.home.building.service;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.building.entity.Village;

public interface IVillageService extends IBaseService<Village>{

	Map getVillageAddrPage(Page page, Map<String, Object> param);

	Map deptInfo();

	Result editVillage(VoAddressBase<Village> voAddressBase);

	boolean delVillage(Village village);


}
