package com.chinobot.plep.home.dataset.service;

import com.chinobot.plep.home.dataset.entity.WhiteList;
import com.chinobot.plep.home.dataset.entity.vo.FilterWarningVO;
import com.chinobot.plep.home.dataset.entity.vo.WhiteListVO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 白名单 服务类
 * </p>
 *
 * @author djl
 * @since 2019-10-11
 */
public interface IWhiteListService extends IBaseService<WhiteList> {

	IPage<WhiteListVO> getWhiteList(Page page, Map<String, Object> param);

	boolean delWhite(String uuid);

	Map showWhite(String uuid);

	IPage<FilterWarningVO> getFilterList(Page page, Map<String, Object> param);

	Map showFilterWarn(String uuid);

	boolean addWhite(WhiteList whiteList);

	List<Map> getFlyPersons(HashMap param);

}
