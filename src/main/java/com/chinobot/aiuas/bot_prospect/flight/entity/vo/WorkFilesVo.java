package com.chinobot.aiuas.bot_prospect.flight.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-03-10 09:49
 */
@ApiModel("航班作业文件")
@Data
public class WorkFilesVo {

    @ApiModelProperty("航班作业id")
    private String flightWorkId;

    @ApiModelProperty("日志文件id")
    private List<String> logFileIds;

    @ApiModelProperty("视频文件id")
    private List<String> videoFileIds;
    
    @ApiModelProperty("采查对象媒体文件id")
    private List<String> objMediaIds;
}
