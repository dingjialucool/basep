package com.chinobot.common.message.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * @ClassName: MessageConfigVo   
 * @Description: 模板配置列表
 * @author: djl  
 * @date:2020年3月30日 上午10:57:22
 */
@ApiModel(description = "模板配置列表")
@Data
public class MessageConfigVo {

	@ApiModelProperty(value = "模板配置主键")
	private String uuid;

	@ApiModelProperty(value = "模板名称")
    private String moduleName;
	
	@ApiModelProperty(value = "模板编码" )
    private String moduleCode;
	
	@ApiModelProperty(value = "消息链接" )
    private String url;
	
	@ApiModelProperty(value = "消息模板" )
	private String template;
	
	@ApiModelProperty(value = "打开方式：1-当前页面不覆盖菜单栏  2-弹窗 3-新标签页  4-当前页面覆盖导航栏（铺满）" )
	private String openType;
}
