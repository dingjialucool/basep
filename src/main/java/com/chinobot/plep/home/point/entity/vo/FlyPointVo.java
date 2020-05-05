package com.chinobot.plep.home.point.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FlyPointVo {

	@ApiModelProperty(value = "文件id")
    private String fileId;

	@ApiModelProperty(value = "飞行时间")
    private String flyTime;
}
