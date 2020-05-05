package com.chinobot.plep.home.dataset.service.impl;

import com.chinobot.plep.home.dataset.entity.Rule;
import com.chinobot.plep.home.dataset.entity.RuleDetail;
import com.chinobot.plep.home.dataset.entity.dto.RuleAndDetailDto;
import com.chinobot.plep.home.dataset.entity.dto.RuleDto;
import com.chinobot.plep.home.dataset.entity.dto.RuleSDto;
import com.chinobot.plep.home.dataset.mapper.RuleMapper;
import com.chinobot.plep.home.dataset.service.IRuleDetailService;
import com.chinobot.plep.home.dataset.service.IRuleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.drools.service.RuleService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 规则表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
@Service
public class RuleServiceImpl extends BaseService<RuleMapper, Rule> implements IRuleService {

	@Autowired
	private RuleMapper ruleMapper;
	@Autowired
	private IRuleService ruleService;
	@Autowired
	private IRuleDetailService ruleDetailService;
	@Autowired
	private RuleService rService;
	
	@Override
	public IPage<Map> getRuleList(Page page, Map<String, Object> param) {
		
//		return ruleMapper.getRuleList(page,param);
		return null;
	}

	@Override
	public Map getRuleDetails(String uuid) {
//		List<Map> list = ruleMapper.getRuleDetails(uuid);
//		Map<String, List> timeMap = new TreeMap(new Comparator<String>() {
//			public int compare(String obj1, String obj2) {
//				// 升序排序
//				return obj1.compareTo(obj2);
//			}
//		});
//		List listTmp = null;
//		for (Map hMap : list) {
//			if(timeMap.containsKey(hMap.get("groupNum").toString())){
//				listTmp = timeMap.get(hMap.get("groupNum").toString());
//				listTmp.add(hMap);
//				timeMap.put(hMap.get("groupNum").toString(), listTmp);
//			}else{
//				listTmp = new ArrayList();
//				listTmp.add(hMap);
//				timeMap.put(hMap.get("groupNum").toString(), listTmp);
//			}
//		}
//		List totalList = new ArrayList();
//		for(Entry<String, List> entry : timeMap.entrySet()){
//			List value = entry.getValue();
//			String groupType = "";
//			if(value.size()>0) {
//				Map maps = (Map) value.get(0);
//				groupType = (String) maps.get("groupType");
//			}
//			Map map = new HashMap();
//			map.put("groupNum",  entry.getKey());
//			map.put("groupType", groupType);
//			map.put("childList",  entry.getValue());
//			totalList.add(map);
//		}
//		Rule rule = ruleService.getById(uuid);
		Map map = new HashMap();
//		map.put("ruleDetailList", totalList);
//		map.put("rule", rule);
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean saveRule(RuleAndDetailDto dto) {
		
		RuleSDto ruleDto = dto.getRuleDto();
		Rule rule = new Rule();
		BeanUtils.copyProperties(ruleDto, rule);
		ruleService.saveOrUpdate(rule);//保存规则
		
		UpdateWrapper<RuleDetail> updateWrapper = new UpdateWrapper<RuleDetail>();
		updateWrapper.eq("rule_id", rule.getUuid());
		updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		ruleDetailService.update(updateWrapper);//先让该规则下的所有规则明细无效
		
		List<RuleDetail> output=convertList2List(dto.getRuleDetails(),RuleDetail.class);
		for (RuleDetail ruleDetail : output) {
			ruleDetail.setRuleId(rule.getUuid());
		}
		//保存规则明细
		boolean bo = ruleDetailService.saveBatch(output);
		rService.loadRule();//加载规则
		return bo;
	}

	/**
     * @param input 输入集合
     * @param clzz  输出集合类型
     * @param <E>   输入集合类型
     * @param <T>   输出集合类型
     * @return 返回集合
     */
    public static <E, T> List<T> convertList2List(List<E> input, Class<T> clzz) {
        List<T> output = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(input)) {
            for (E source : input) {
                T target = BeanUtils.instantiate(clzz);
                BeanUtils.copyProperties(source, target);
                output.add(target);
            }
        }
        return output;
    }	
}
