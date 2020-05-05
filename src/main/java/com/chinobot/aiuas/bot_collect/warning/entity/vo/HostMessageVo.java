package com.chinobot.aiuas.bot_collect.warning.entity.vo;

import java.util.List;

import com.chinobot.aiuas.bot_collect.warning.entity.dto.FileMessageDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: FeedBackVo   
 * @Description: 主办信息 
 * @author: djl  
 * @date:2020年2月26日 下午4:57:15
 */
@ApiModel(description = "主办信息")
@Data
public class HostMessageVo {

	@ApiModelProperty(value = "主办主键")
    private String hostId;
	
    @ApiModelProperty(value = "主办人")
    private String hostBy;
    
    @ApiModelProperty(value = "主办内容")
    private String hostIdea;
    
    @ApiModelProperty(value = "主办时间")
    private String operateTime;
    
    @ApiModelProperty(value = "状态 ---- 20: 确认信息  30 治理信息  40 核查信息  90 办结信息   99 督办信息")
    private String businessStatus;
    
    @ApiModelProperty(value = "主办文件")
    private List<FileMessageDTO> fileVo;
    
}