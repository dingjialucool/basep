package com.chinobot.plep.home.dataset.mapper;

import com.chinobot.plep.home.dataset.entity.WarningInfo;
import com.chinobot.plep.home.dataset.entity.WhiteList;
import com.chinobot.plep.home.dataset.entity.vo.FilterWarningVO;
import com.chinobot.plep.home.dataset.entity.vo.WhiteListVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.warning.entity.Warning;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 白名单 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-10-11
 */
public interface WhiteListMapper extends IBaseMapper<WhiteList> {

	IPage<WhiteListVO> getWhiteList(Page page, @Param("p")Map<String, Object> param);

	Map showWhite(String uuid);

	IPage<FilterWarningVO> getFilterList(Page page, @Param("p")Map<String, Object> param);

	Map showFilterWarn(String uuid);
	
	/**
	 * 白名单过滤
	 */
	String getFilterWhiteIds(@Param("param")Map<String, Object> param, @Param("info")WarningInfo info);

	List<Map> getFlyPersons(@Param("p") HashMap param);

	String getFilterWhiteIdsNew(@Param("param")Map whiteParam, @Param("warning")Warning warning);

}
