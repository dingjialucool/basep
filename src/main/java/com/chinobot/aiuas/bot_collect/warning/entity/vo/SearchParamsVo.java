package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;
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
public class SearchParamsVo {

    @ApiModelProperty(value = "筛选条件名称")
    private String name;
    
    @ApiModelProperty(value = "筛选条件key")
    private String key;
    
    @ApiModelProperty(value = "筛选条件集合")
    private List<Map> list;
    
}
