package com.chinobot.framework.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinobot.common.utils.log.LogTypeName;
import com.chinobot.common.utils.log.LoggerUtils;
import com.chinobot.framework.web.constant.KafkaConstant;
import com.chinobot.framework.web.event.KafkaMsgEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * kafka消费者
 */
@Component
//@Slf4j
public class KafkaConsumer {

//    @Autowired
//    private ApplicationEventPublisher publisher;
//    
//    private Logger receive = LoggerUtils.logger(LogTypeName.KAFKA_RECEIVE);
//
//    @KafkaListener(topics = "${spring.kafka.template.default-topic}")
//    public void listen (ConsumerRecord<String, String> record) throws Exception {
//        //log.debug ("topic = {}, offset = {}, key = {}，value = {}",record.topic(), record.offset(), record.key (), record.value());
//    	String key = record.key();
//        if(KafkaConstant.PIPE_PUSH_WARNING.equals(key)) {
//        	JSONObject jsonObject = JSON.parseObject(record.value());
//        	jsonObject.put("warningImg", "");
//        	jsonObject.put("baseImg", "");
//        	receive.info("topic = {}, offset = {}, key = {}，value = {}",record.topic(), record.offset(), record.key (), jsonObject.toJSONString());
//        }else {
//        	receive.info("topic = {}, offset = {}, key = {}，value = {}",record.topic(), record.offset(), record.key (), record.value());
//        }
//        // 触发消费事件
//        publisher.publishEvent (new KafkaMsgEvent (record));
        
        
//    }
    
//    @KafkaListener(topics = "aiuasWork")
//    public void listenWork (ConsumerRecord<String, String> record) throws Exception {
//        //log.debug ("topic = {}, offset = {}, key = {}，value = {}",record.topic(), record.offset(), record.key (), record.value());
//    	String value = record.value();
//    	System.out.println(value);
//        // 触发消费事件
////        publisher.publishEvent (new KafkaMsgEvent (record));
//    }
}
