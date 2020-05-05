package com.chinobot.aiuas.bot_resource.uav.entity.vo;

import com.chinobot.common.file.entity.FileBus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-16 11:25
 */
@ApiModel(description = "无人机")
@Data
public class UavInfoVo {
    @ApiModelProperty(value = "无人机主键")
    private String uuid;

    @ApiModelProperty(value = "无人机名称")
    private String ename;

    @ApiModelProperty(value = "设备序列号")
    private String serialNumber;

    @ApiModelProperty(value = "机型主键")
    private String uavTypeUuid;

    @ApiModelProperty(value = "型号名称")
    private String moduleName;

    @ApiModelProperty(value = "配件信息列表")
    private List<FittingVo> fittingVoList;

    @ApiModelProperty(value = "文件业务关联")
    private List<FileBus> files;

    @ApiModelProperty(value = "人员外键")
    private String personId;

    @ApiModelProperty(value = "人员名称")
    private String personName;


}
