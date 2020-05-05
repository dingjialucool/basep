package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: SearchParamVo   
 * @Description: 问题预警总况页面筛选条件  
 * @author: djl  
 * @date:2020年3月16日 下午2:29:01
 */
@ApiModel(description = "问题预警总况页面筛选条件 - 时间查询条件码值-  time_type")
@Data
public class SearchParamVo {

    @ApiModelProperty(value = "预警场景")
    private Map<String,String> sceneMap;
    
    @ApiModelProperty(value = "区划")
    private Map<String,String> areaMap;
    
    @ApiModelProperty(value = "部门")
    private Map<String,String> deptMap;
    
}
