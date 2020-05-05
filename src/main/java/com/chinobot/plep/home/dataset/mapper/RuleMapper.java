package com.chinobot.plep.home.dataset.mapper;

import com.chinobot.plep.home.dataset.entity.Rule;
import com.chinobot.plep.home.dataset.entity.dto.RuleDto;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 规则表 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
public interface RuleMapper extends IBaseMapper<Rule> {

	IPage<Map> getRuleList(Page page,@Param("p") Map<String, Object> param);

	List<Map> getRuleDetails(String uuid);
	
	/**
	 * 获取所有有效规则
	 * @return
	 */
	List<RuleDto> getAllRuleDto();

}
