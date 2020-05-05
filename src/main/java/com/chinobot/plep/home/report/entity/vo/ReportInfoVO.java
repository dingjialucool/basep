package com.chinobot.plep.home.report.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2019-12-25 11:47
 */
@ApiModel(description = "采查报告详详细")
@Data
public class ReportInfoVO {
    @ApiModelProperty(value = "num：存量数量, yoy：存量同比, mom：存量环比，dname:区域名称")
    private Map<String, Object> chart;

    @ApiModelProperty(value = "航线规划")
    private List<Map> mapRoute;
    
    @ApiModelProperty(value = "飞行巡查")
    private FlyMessageVo flyMessageVo;
    
    @ApiModelProperty(value = "预警信息")
    private List<WarnImgVo> warnList;
}
