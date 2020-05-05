package com.chinobot.aiuas.bot_prospect.obstacle.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: ObstacleAndTypeVo   
 * @Description: 障碍物和障碍物类型  
 * @author: djl  
 * @date:2020年3月19日 下午2:54:30
 */
@ApiModel(description = "障碍物和障碍物类型")
@Data
public class ObstacleAndTypeVo {

    @ApiModelProperty("障碍物id")
    private String id;

    @ApiModelProperty("名称")
    private String title;

    @ApiModelProperty("父类")
    private String parentUuid;
    
    @ApiModelProperty("障碍物类型 1 表示障碍物类型 2 表示障碍物  3 表示顶级父类 （新增障碍物类型时 在顶级父类下新增，新增障碍物时，在 障碍物类型下新增）")
    private String type;
}
