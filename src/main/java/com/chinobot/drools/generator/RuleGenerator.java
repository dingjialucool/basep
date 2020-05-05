package com.chinobot.drools.generator;

import org.apache.commons.lang.StringUtils;
import org.drools.template.ObjectDataCompiler;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chinobot.drools.executor.RuleExecutor;
import com.chinobot.plep.home.dataset.entity.dto.RuleDto;
import com.chinobot.plep.home.dataset.entity.dto.RuleGroupDto;
import com.chinobot.plep.home.dataset.entity.dto.RuleMetadataDto;

import java.sql.Types;
import java.util.*;

/**
 * @AUTHOR huangw
 * @DATE 
 * @DESCRIPTION 规则生成器
 **/
public class RuleGenerator {
    private static final Logger log = LoggerFactory.getLogger(RuleGenerator.class);

    public static List<String> paths = new ArrayList<String>();
    
    private static KieServices kieServices;
    
    private static KieFileSystem kieFileSystem;
    
    /**
     * 根据传递进来的参数对象生规则
     *
     * 
     */
    public void generateRules(List<RuleDto> allRuleDto) {
//        List<String> ruleDrls = new ArrayList<>();
//        for (int i = 0; i < allRuleDto.size(); i++) {
//            //规则的生成
//            String drlString = applyRuleTemplate(allRuleDto.get(i));
//            ruleDrls.add(drlString);
//            log.info("规则引擎加载规则,序号={}", i);
//        }
//        //规则的加载
//        createOrRefreshDrlInMemory(ruleDrls);
    }
    
    /**
     * 根据Rule生成drl的String
     */
    private String applyRuleTemplate(RuleDto ruleDto) {
//        Map<String, Object> data = prepareData(ruleDto);
////        log.info("rule={}", JSON.toJSON(ruleDTO));
//        ObjectDataCompiler objectDataCompiler = new ObjectDataCompiler();
////        模板文件生成drl String
//        return objectDataCompiler.compile(Arrays.asList(data), Thread.currentThread().getContextClassLoader().getResourceAsStream("event-rule-template.drt"));
    			return null;
    }
    /**
     * 根据Rule生成drl的map data
     */
    protected Map<String, Object> prepareData(RuleDto ruleDto) {
    	Map<String, Object> rsMap = new HashMap<String, Object>();
//    	if("1".equals(ruleDto.getIsGlobal())) {
//    		rsMap.put("groupName", ruleDto.getSetId());
//    	}else {
//    		rsMap.put("groupName", ruleDto.getSetId()+"#"+ruleDto.getTaskId());
//    	}
//    	
//    	rsMap.put("ruleCode", ruleDto.getUuid());
//    	rsMap.put("priority", ruleDto.getPriority()==null ? 0:ruleDto.getPriority());
//    	rsMap.put("ruleCondition", packCondition(ruleDto.getGroups()));
    	return rsMap;
    }
    /**
     * 组装条件
     * @param groups
     * @return
     */
    private String packCondition(List<RuleGroupDto> groups) {
//    	StringBuffer rsb = new StringBuffer();
//    	//"this['"+ (String)ruleMap.get("colName") + "']" + (String)ruleMap.get("type") + "'" + (String)ruleMap.get("value")+"'"
//    	if(groups != null && !groups.isEmpty()) {
//    		for(int i=0; i<groups.size(); i++) {
//    			RuleGroupDto group  = groups.get(i);
//    			//判断第一组是否包含关系
//    			if(i == 0 && StringUtils.isNotBlank(group.getGroupType())) {
//    				rsb.append("1 == 1 ").append(group.getGroupType()).append(" ");
//    			}
//    			if(i >0 ) {
//    				rsb.append(" ").append(group.getGroupType()).append(" ");
//    			}
//    			rsb.append("(");
//    			//开始组装括号里面内容
//    			List<RuleMetadataDto> fields = group.getFields();
//    			if(fields != null && !fields.isEmpty()) {
//    				for(int j=0; j<fields.size(); j++) {
//    					RuleMetadataDto field = fields.get(j);
//    					//判断第一组是否包含关系
////    	    			if(j == 0 && StringUtils.isNotBlank(field.getRelationType())) {
////    	    				rsb.append("1 == 1 ").append(field.getRelationType()).append(" ");
////    	    			}
//    	    			
//    	    			rsb.append("this['").append(field.getField()).append("'] ").append(field.getCompareType()).append(" ");
//    	    			//处理比较值
//    	    			if(field.getCompareType().contains("memberof")) {
//    	    				//比较值为数组，需特殊处理 TODO
//    	    				
//    	    			}else {
//    	    				rsb.append(handleFieldType(field));
//    	    			}
//    	    			if(j < fields.size()-1) {
//    	    				rsb.append(" ").append(field.getRelationType()).append(" ");
//    	    			}
//    				}
//    			}
//    			//括号里面内容结束
//    			rsb.append(")");
//    		}
//    	}
//    	return rsb.toString();
    	return null;
    }
    /**
     * 处理比较值类型
     * @param field
     * @return
     */
    private String handleFieldType(RuleMetadataDto field) {
    	//归属 数字(number)、字符串(String)、时间(time)三大类之一
//    	String bigType = getCatagory(field);
//    	if("number".equals(bigType)) {
//    		return field.getCompareValue();
//    	}
//		if("String".equals(bigType)) {
//			return "\""+field.getCompareValue()+"\"";
//		 }
//		if("time".equals(bigType)) {
//			return "\""+field.getCompareValue()+"\"";
//		}
		return null;
	}

    private String getCatagory(RuleMetadataDto field) {
    	//归属 数字(number)、字符串(String)、时间(time)三大类之一
//    	String bigType = "";
//    	switch (field.getFieldType()) {
//			case Types.BIGINT:
//				bigType = "number";
//				break;
//			case Types.BOOLEAN:
//				bigType = "number";//布尔同数字一样不加引号
//				break;
//			case Types.CHAR:
//				bigType = "String";
//				break;
//			case Types.DATE:
//				bigType = "time";
//				break;
//			case Types.DECIMAL:
//				bigType = "number";
//				break;
//			case Types.DOUBLE:
//				bigType = "number";
//				break;
//			case Types.FLOAT:
//				bigType = "number";
//				break;
//			case Types.REAL:
//				bigType = "number";
//				break;
//			case Types.INTEGER:
//				bigType = "number";
//				break;
//			case Types.LONGNVARCHAR:
//				bigType = "String";
//				break;
//			case Types.LONGVARCHAR:
//				bigType = "String";
//				break;
//			case Types.NCHAR:
//				bigType = "String";
//				break;
//			case Types.NUMERIC:
//				bigType = "number";
//				break;
//			case Types.NVARCHAR:
//				bigType = "String";
//				break;
//			case Types.SMALLINT:
//				bigType = "number";
//				break;
//			case Types.TIME:
//				bigType = "time";
//				break;
//			case Types.TIMESTAMP:
//				bigType = "time";
//				break;
//			case Types.TINYINT:
//				bigType = "number";
//				break;
//			case Types.VARCHAR:
//				bigType = "String";
//				break;
//			default:
//				break;
//		}
//    	return bigType;
    	return null;
    }
	/**
     * 根据String格式的Drl生成Maven结构的规则
     *
     * @param rules
     */
    private void createOrRefreshDrlInMemory(List<String> rules) {
//        for (String str : rules) {
//        	String path = "src/main/resources/" + UUID.randomUUID() + ".drl";
//        	paths.add(path);
//            kieFileSystem.write(path, str);
//            log.info("str={}", str);
//        }
//        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem).buildAll();
//        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
//            log.error("create rule in kieFileSystem Error", kb.getResults());
//            throw new IllegalArgumentException("生成规则文件失败");
//        }
//        doAfterGenerate(kieServices);
    }

    /**
     * 清除规则
     */
    public void clearRule() {
//    	log.info("清除规则：{}条",paths.size());
//    	if(paths.size()>0) {
//	        String[] strings = new String[paths.size()];
//	        paths.toArray(strings);
//			kieFileSystem.delete(strings);
//	        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem).buildAll();
//	        if (kb.getResults().hasMessages(Message.Level.ERROR)) {
//	            log.error("create rule in kieFileSystem Error", kb.getResults());
//	            throw new IllegalArgumentException("清除规则文件失败");
//	        }
//    	}
//    	paths.clear();
    }
    /**
     * 初始化
     */
    public static void init() {
//    	kieServices = KieServices.Factory.get();
//        kieFileSystem = kieServices.newKieFileSystem();
//    	kieFileSystem.generateAndWritePomXML(RuleExecutor.getReleaseId());
//    	log.info("初始化kieFileSystem");
    }
    /**
     * 生成完毕后的清理工作，目前主要用于debug模式测试完毕后，从内存中清理掉规则文件。
     */
    protected void doAfterGenerate(KieServices kieServices) {

    }


}