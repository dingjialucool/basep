package com.chinobot.aiuas.bot_prospect.flight.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-03-18 20:01
 */
@ApiModel("MMC航点")
@Accessors(chain = true)
@Data
public class MMCPoint {

    @ApiModelProperty("")
    private boolean autoContinue = true;

    @ApiModelProperty("22：起飞点，16：一般点，178：速度点")
    private int command;

    @ApiModelProperty("[纬度，经度，高度]")
    private Object[] coordinate;

    @ApiModelProperty("command22/16：3, command178：2")
    private int frame = 3;

    @ApiModelProperty("command22/16：1开始自增，command178：0")
    private int id;

    @ApiModelProperty("悬停")
    private Float param1 = 0f;

    @ApiModelProperty("速度 command22/16：0")
    private Float param2 = 0f;

    @ApiModelProperty("转头方向 command22/16：0，command178：-1")
    private Float param3 = 0f;

    @ApiModelProperty("")
    private Float param4 = 0f;

    @ApiModelProperty("")
    private String type = "missionItem";

    public MMCPoint setCommand(int command){
        if(178 == command){
            this.frame = 2;
            this.id = 0;
            this.param3 = -1f;
        }else{
            this.frame = 3;
            this.param3 = 0f;
        }
        this.command = command;
        return this;
    }
}
