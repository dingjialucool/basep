package com.chinobot.common.file.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-04-09 17:24
 */
@ApiModel(description = "业务文件列表")
@Data
public class FileBusListVo {

    @ApiModelProperty(value = "文件ids")
    private String[] fileIds;
    @ApiModelProperty(value = "业务id")
    private String busId;
    @ApiModelProperty(value = "模块")
    private String module;
}
