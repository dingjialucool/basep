package com.chinobot.aiuas.bot_resource.uav.entity.vo;

import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.entity.UavType;
import com.chinobot.common.file.entity.FileBus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-15 17:48
 */
@ApiModel(description = "机型")
@Data
public class UavTypeInfoVo {

    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "型号名称")
    private String moduleName;

    @ApiModelProperty(value = "类型（1.固定翼、2.多旋翼）")
    private String type;

    @ApiModelProperty(value = "最大尺寸-长（mm）")
    private Float maxLength;

    @ApiModelProperty(value = "最大尺寸-宽（mm）")
    private Float maxWidth;

    @ApiModelProperty(value = "最大尺寸-高（mm）")
    private Float maxHeight;

    @ApiModelProperty(value = "最大重量（kg）")
    private Float maxWeight;

    @ApiModelProperty(value = "描述")
    private String uavDescription;

    @ApiModelProperty(value = "最大载重（kg）")
    private Float maxLoad;

    @ApiModelProperty(value = "最大可承受风速（m/s）")
    private Integer maxWindSpeed;

    @ApiModelProperty(value = "最大飞行时间（min）")
    private Float maxFlyTimeMin;

    @ApiModelProperty(value = "工作环境最低温度（。C）")
    private Float jobEnvironmentLowerTemperature;

    @ApiModelProperty(value = "工作环境最高温度（。C）")
    private Float jobEnvironmentHigherTemperature;

    @ApiModelProperty(value = "厂商名称")
    private String firmName;

    @ApiModelProperty(value = "联系人")
    private String linkPerson;

    @ApiModelProperty(value = "联系电话")
    private String linkPhone;

    @ApiModelProperty(value = "文件业务关联")
    private List<FileBus> files;
}
