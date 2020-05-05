package com.chinobot.drools.executor;


import java.util.Map;

import org.drools.compiler.kproject.ReleaseIdImpl;
import org.kie.api.KieBase;
import org.kie.api.KieServices;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinobot.drools.generator.RuleGenerator;

/**
 * 规则执行器
 */
public class RuleExecutor {
    private static final Logger log = LoggerFactory.getLogger(RuleExecutor.class);
    static ReleaseId releaseId = new ReleaseIdImpl("com.chinobot.droolsdrt", "drools-drt", "1.0");
    private static KieBase kieBase;


    /**
     * 单例(非线程安全,避免影响性能)
     *
     * @return
     * @author xiaohua 2016年10月24日 下午2:08:32
     */
    public static KieBase getKieBase() {
//        if (kieBase == null) {
//            KieServices kieServices = KieServices.Factory.get();
//            KieContainer kieContainer = kieServices.newKieContainer(getReleaseId());
//            kieBase = kieContainer.getKieBase();
//        }
        return kieBase;
    }

    /**
     * 更新kieBase
     *
     * @return
     * @author xiaohua 2016年11月8日 下午7:42:04
     */
    public static KieBase updateKieBase() {
        //重置kiebase
//        kieBase = null;
//        if(RuleGenerator.paths.isEmpty()) {//防止报错Cannot find KieModule: com.chinobot.droolsdrt:drools-drt:1.0
//        	return null;
//        }
        return getKieBase();
    }



    public static void execute(Map<String, Object> map, String groupCode) {
//    	if(RuleGenerator.paths.size()>0) {
//    		System.out.println("进入规则过滤");
//    		KieSession KieSession = getKieBase().newKieSession();
//        	//KieSession.getGlobals().set("globalParams", global);
//        	KieSession.insert(map);
//        	KieSession.getAgenda().getAgendaGroup(groupCode).setFocus();
//        	KieSession.fireAllRules();
//        	KieSession.dispose();
//    	}
    	
    	
    }
    public static ReleaseId getReleaseId() {
        return releaseId;
    }

}
