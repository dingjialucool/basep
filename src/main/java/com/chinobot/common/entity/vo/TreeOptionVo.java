package com.chinobot.common.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-02-21 17:26
 */
@ApiModel(description = "树列表")
@Data
public class TreeOptionVo {

    @ApiModelProperty(value = "数据的key对应字段")
    private String keyName;

    @ApiModelProperty(value = "数据的title对应字段")
    private String titleName;

    @ApiModelProperty(value = "数据的parent对应字段")
    private String parentName;

    @ApiModelProperty(value = "额外参数")
    private Map<String, Object> extendParam;
}
