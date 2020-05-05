package com.chinobot.cityle.base.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Grid;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 网格 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-03-26
 */
public interface GridMapper extends IBaseMapper<Grid> {
    
	/**
	 * 网格/地址 列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月1日
	 * @company chinobot
	 */
	List<Map> getGridAddrList(@Param("p") Map<String, String> param);
	
	/**
	 * 网格 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt
	 * @date 2019年3月28日
	 * @company chinobot
	 */
	IPage<VoAddressBase<Map>> getGridAddr(Page page, @Param("p") Map<String, String> param);
	
    /**
     * 根据id获取网格/地址
     * @param param
     * @return
     * @author shizt
     * @date 2019年3月26日
     * @company chinobot
     */
    VoAddressBase<Map> getGridAddr(@Param("p") Map<String, String> param);

    /**
     *	 辅助分析数据 各区总览
     * @param param
     * @return
     */
    List<LinkedHashMap<String, Object>> areaData(@Param("p")Map<String, String> param);
}
