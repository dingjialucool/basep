package com.chinobot.aiuas.bot_resource.uav.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-16 10:22
 */
@ApiModel(description = "无人机列表")
@Data
public class UavListVo {

    @ApiModelProperty(value = "无人机主键")
    private String uuid;

    @ApiModelProperty(value = "无人机名称")
    private String ename;

    @ApiModelProperty(value = "设备序列号")
    private String serialNumber;

    @ApiModelProperty(value = "型号名称")
    private String moduleName;

    @ApiModelProperty(value = "类型（1.固定翼、2.多旋翼）")
    private String type;

    @ApiModelProperty(value = "最大尺寸-长（mm）")
    private String maxLength;

    @ApiModelProperty(value = "最大尺寸-宽（mm）")
    private String maxWidth;

    @ApiModelProperty(value = "最大尺寸-高（mm）")
    private String maxHeight;

    @ApiModelProperty(value = "最大载重（kg）")
    private String maxLoad;

    @ApiModelProperty(value = "最大飞行时间（min）")
    private String maxFlyTimeMin;

    @ApiModelProperty(value = "封面图片主键")
    private String fileId;
}
