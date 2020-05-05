package com.chinobot.framework.web.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.log.LogTypeName;
import com.chinobot.common.utils.log.LoggerUtils;
import com.chinobot.framework.web.constant.KafkaConstant;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/kafka")
//@Slf4j
public class KafkaProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    
    private Logger sendLog = LoggerUtils.logger(LogTypeName.KAFKA_SEND);

    @RequestMapping("/send/{key}")
    public Result send(@PathVariable String key, @RequestBody JSONObject jsonMsg){
    	
        kafkaTemplate.sendDefault(key, jsonMsg.toString ());
        if(KafkaConstant.PIPE_PUSH_WARNING.equals(key)) {
        	jsonMsg.put("warningImg", "");
        	jsonMsg.put("baseImg", "");
        }
        sendLog.info ("send key = {}, offset = {}, value = {} ", key, jsonMsg.size(), jsonMsg.toJSONString());
        return ResultFactory.success ();
    }
}
