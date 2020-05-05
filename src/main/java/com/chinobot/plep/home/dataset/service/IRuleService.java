package com.chinobot.plep.home.dataset.service;

import com.chinobot.plep.home.dataset.entity.Rule;
import com.chinobot.plep.home.dataset.entity.dto.RuleAndDetailDto;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 规则表 服务类
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
public interface IRuleService extends IBaseService<Rule> {

	IPage<Map> getRuleList(Page page, Map<String, Object> param);

	Map getRuleDetails(String uuid);

	boolean saveRule(RuleAndDetailDto dto);

}
