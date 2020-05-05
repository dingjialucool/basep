package com.chinobot.framework.web.event;

import lombok.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.context.ApplicationEvent;

/**
 * kafka消息事件
 */
@Data
public class KafkaMsgEvent extends ApplicationEvent {
    ConsumerRecord<String, String> record;

    public KafkaMsgEvent(ConsumerRecord<String, String> record) {
        super (record);
        this.record = record;
    }
}
