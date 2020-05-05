package com.chinobot.common.message.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.message.entity.dto.MessageDto;
import com.chinobot.common.message.entity.vo.MessageVO;
import com.chinobot.common.message.service.IMessageConfigService;
import com.chinobot.common.message.service.IMessageService;
import com.chinobot.common.message.utils.MessageUtils;
import com.chinobot.common.utils.ResultFactory;
import com.google.gson.Gson;
import freemarker.template.TemplateException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.chinobot.framework.web.controller.BaseController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息 前端控制器
 * </p>
 *
 * @author shizt
 * @since 2019-12-11
 */
@RestController
@RequestMapping("/api/message")
@Api(tags="消息接口")
public class MessageController extends BaseController {

    @Autowired
    private IMessageConfigService iMessageConfigService;
    @Autowired
    private IMessageService iMessageService;

    @ApiOperation(value = "发送消息", notes = "发送消息")
    @GetMapping("/send")
    public Result sendMessage(@RequestBody MessageDto dto, Map<String, Object> customParam) throws IOException, TemplateException {
        System.err.println("=======customParam:" + new Gson().toJson(customParam));
//        iMessageService.sendMessage(dto, customParam);
        MessageUtils.send(dto, customParam);

        return ResultFactory.success();
    }
    
    @ApiOperation(value = "获取所有消息分页", notes = "获取所有消息分页")
    @GetMapping("/pageMyMessage")
    public Result<IPage<MessageVO>> pageMyMessage(Page page, @RequestParam Map<String, Object> param) {
    	return ResultFactory.success(iMessageService.pageMyMessage(page, param));
    }
    
    @ApiOperation(value = "获取所有未读消息", notes = "获取所有未读消息")
    @GetMapping("/listMyMessageUnRead")
    public Result<List<MessageVO>> listMyMessageUnRead(){
    	return ResultFactory.success(iMessageService.listMyMessageUnRead());
    }
    
    @ApiOperation(value = "消息已读", notes = "消息已读")
    @GetMapping("/readMessage")
    public Result readMessage(String uuid) {
    	iMessageService.readMessage(uuid);
    	return ResultFactory.success();
    }
    
    @ApiOperation(value = "根据消息主键获取消息", notes = "根据消息主键获取消息")
    @GetMapping("/getMessageVOById")
    public Result<MessageVO> getMessageVOById(String uuid) {
    	return ResultFactory.success(iMessageService.getMessageVOById(uuid));
    }
}
