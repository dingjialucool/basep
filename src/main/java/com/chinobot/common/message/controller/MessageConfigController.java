package com.chinobot.common.message.controller;


import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chinobot.aiuas.bot_event.urgent.entity.vo.PersonListVo;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.message.entity.MessageConfig;
import com.chinobot.common.message.entity.dto.MessageConfigDTO;
import com.chinobot.common.message.entity.dto.MessagesDTO;
import com.chinobot.common.message.entity.vo.MessageConfigVo;
import com.chinobot.common.message.entity.vo.MessageListVO;
import com.chinobot.common.message.mapper.MessageConfigMapper;
import com.chinobot.common.message.service.IMessageConfigService;
import com.chinobot.common.utils.ResultFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.util.List;

/**
 * 
 * @ClassName: MessageConfigController   
 * @Description: 模板配置 前端控制器 
 * @author: djl  
 * @date:2020年3月30日 上午10:03:33
 */
@RestController
@RequestMapping("/api/bot/messageConfig")
@Api(tags="模板配置接口")
public class MessageConfigController extends BaseController {

    @Autowired
    private IMessageConfigService messageConfigService;
    @Autowired
    private MessageConfigMapper  messageConfigMapper;

    @ApiOperation(value = "获取模板配置列表", notes = "获取模板配置列表")
    @GetMapping("/getMessageConfigList")
    public Result<IPage<MessageConfigVo>> getMessageConfigList(MessageConfigDTO dto){

        return ResultFactory.success(messageConfigService.getMessageConfigList(dto));
    }
    
    @ApiOperation(value = "新增/修改", notes = "保存模板配置（返回值code 400为保存失败，msg有两种，保存失败。  保存失败，模板编码已存在。200为保存成功）")
    @PostMapping("/saveMessageConfig")
    public Result saveMessageConfig(@RequestBody MessageConfigVo dto){

        return messageConfigService.saveMessageConfig(dto);
    }
   
    @ApiOperation(value = "获取模板配置信息", notes = "获取模板配置信息")
    @GetMapping("/getMessageConfig")
    public Result<MessageConfigVo> getMessageConfig(@ApiParam(name = "uuid", value = "模板配置主键", required = true) @RequestParam(value = "uuid", required = true) String uuid){

        return ResultFactory.success(messageConfigService.getMessageConfig(uuid));
    }
    
    @ApiOperation(value = "删除模板配置信息", notes = "删除模板配置信息")
    @PostMapping("/removeMessageConfig")
    public Result removeMessageConfig(@ApiParam(name = "uuid", value = "模板配置主键", required = true) @RequestParam(value = "uuid", required = true) String uuid){

    	messageConfigService.update(new LambdaUpdateWrapper<MessageConfig>().eq(MessageConfig::getUuid, uuid).set(MessageConfig::getDataStatus, GlobalConstant.DATA_STATUS_INVALID));
        return ResultFactory.success();
    }
    
    @ApiOperation(value = "获取消息列表", notes = "获取消息")
    @GetMapping("/getMessageList")
    public Result<IPage<MessageListVO>> getMessageList(MessagesDTO dto){

        return ResultFactory.success(messageConfigService.getMessageList(dto));
    }
    
    @ApiOperation(value = "获取发起人集合", notes = "获取发起人")
	@GetMapping("/getSendByList")
	public Result<List<PersonListVo>> getSendByList() {
		
		return ResultFactory.success(messageConfigMapper.getSendByList());
	}
    
    @ApiOperation(value = "获取接收人集合", notes = "获取接收人")
	@GetMapping("/getRecevieList")
	public Result<List<PersonListVo>> getRecevieList() {
		
		return ResultFactory.success(messageConfigMapper.getRecevieList());
	}
}
