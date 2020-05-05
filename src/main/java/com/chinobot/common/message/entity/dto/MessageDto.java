package com.chinobot.common.message.entity.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: chinobot
 * @description: MessageDto
 * @author: shizt
 * @create: 2019-12-12 09:44
 */
@Data
public class MessageDto {

    @ApiModelProperty(value = "消息类型代码，必填")
    private String code;

    @ApiModelProperty(value = "发送人，必填")
    private String sendPid;

    @ApiModelProperty(value = "接收人，必填")
    private String receivePid;

    @ApiModelProperty(value = "url参数")
    private String urlParams;
}
