package com.chinobot.plep.home.building.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.building.entity.Building;

/**
 * <p>
 * 网格 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-03-26
 */
public interface BuildingMapper extends IBaseMapper<Building> {
    
	/**
	 * 网格/地址 列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月1日
	 * @company chinobot
	 */
	List<Map> getBuildingAddrList(@Param("p") Map<String, String> param);
	
	/**
	 * 网格 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt
	 * @date 2019年3月28日
	 * @company chinobot
	 */
	IPage<VoAddressBase<Map>> getBuildingAddr(Page page, @Param("p") Map<String, String> param);
	
    /**
     * 根据id获取网格/地址
     * @param param
     * @return
     * @author shizt
     * @date 2019年3月26日
     * @company chinobot
     */
    VoAddressBase<Map> getBuildingAddr(@Param("p") Map<String, String> param);

    /**
     *	 辅助分析数据 各区总览
     * @param param
     * @return
     */
    List<LinkedHashMap<String, Object>> areaData(@Param("p")Map<String, String> param);

	IPage<VoAddressBase<Map>> getBuildingWeiAddr(Page page, @Param("p") Map<String, String> param);

	IPage<VoAddressBase<Map>> getBuildingYiAddr(Page page, @Param("p") Map<String, String> param);
	
	List<Map> getNearbyBuilding(Map<String, Object> param);

	List<Map> getBuildingAddrPolicy(@Param("p")Map<String, Object> param);
	
	/**
	 *  获取所有的建筑
	 * @param page
	 * @param param
	 * @return
	 */
	IPage<VoAddressBase<Map>> getBuildAddr(Page page, @Param("p")Map<String, Object> param);

	List<Map> getImgListByBusId(@Param("module")String module, @Param("busId")String busId);
}
