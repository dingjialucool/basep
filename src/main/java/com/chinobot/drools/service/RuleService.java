package com.chinobot.drools.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import com.chinobot.drools.constant.DroolsConstant;
import com.chinobot.drools.executor.RuleExecutor;
import com.chinobot.drools.generator.RuleGenerator;
import com.chinobot.plep.home.dataset.entity.dto.RuleDto;
import com.chinobot.plep.home.dataset.mapper.RuleMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



@Service
public class RuleService implements ApplicationRunner{
    public static final Logger log = LoggerFactory.getLogger(RuleService.class);

    @Autowired
    private RuleMapper ruleMapper;
    /**
     * 加载规则
     */
    public synchronized void loadRule() {
//        try {
//        	RuleGenerator generator = new RuleGenerator();
//        	generator.clearRule();
//        	List<RuleDto> allRuleDto = ruleMapper.getAllRuleDto();
//            log.info("{}条加入规则引擎", allRuleDto.size());
//            if (!allRuleDto.isEmpty()) {
//                generator.generateRules(allRuleDto);
//            }
//            RuleExecutor.updateKieBase();
//        } catch (Exception e) {
//            log.error("RuleService.loadRule。e={}",e.getMessage(), e);
//        }
    }
   
    public void useRule(Map<String, Object> map) {
//    	RuleExecutor.execute(map, "");
//    	if((boolean) map.get("droolsFilter")) {
//    		System.out.println("符合规则"+ map.get("ruleId"));
//    	}else {
//    		System.out.println("抱歉，没有匹配到的规则");
//    	}
    }

    @Override
	public void run(ApplicationArguments args) throws Exception {
//    	System.setProperty("drools.dateformat", DroolsConstant.DATE_FOMAT);
//    	RuleGenerator.init();
//    	loadRule();
//		log.info("初始化规则");
	}


}
