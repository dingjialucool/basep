package com.chinobot.aiuas.bot_collect.info.entity.dto;

import java.util.List;
import java.util.Map;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @author 周莉莉
 *
 */
@ApiModel("采查对象采查图片以及采查视频列表")
@Data
public class InfoCheckDataListDTO {

	@ApiModelProperty("采查对象外键")
	private String collect_uuid;
	
	@ApiModelProperty("起飞日期时间")
	private String flight_date;
	
	@ApiModelProperty("图片uuid")
	private List<String> imgList;
	
	@ApiModelProperty("视频uuid")
	private List<String> vedioList;
	
	@ApiModelProperty("图片或视频外键")
	private List<InfoCheckDataFileDTO> fileList;

	
	@ApiModelProperty("是否展开图片")
	private boolean expanPic =  false;
	
	@ApiModelProperty("是否展开视频")
	private boolean expanVideo =  false;
}
