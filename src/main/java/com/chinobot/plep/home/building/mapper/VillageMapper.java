package com.chinobot.plep.home.building.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.building.entity.Village;

public interface VillageMapper extends IBaseMapper<Village> {

	IPage<VoAddressBase<Map>> getVillageAddr(Page page, @Param("p")Map<String, Object> param);

	List<Map> getImgListByBusId(@Param("module")String module, @Param("busId")String busId);
	/**
	 * 获取所有的小区边界
	 * @return
	 */
	List<Map<String, String>> getAllPolyLine();

}
