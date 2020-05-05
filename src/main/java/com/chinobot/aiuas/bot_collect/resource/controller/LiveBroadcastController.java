package com.chinobot.aiuas.bot_collect.resource.controller;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chinobot.aiuas.bot_collect.resource.entity.LiveBroadcast;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.LiveBroadcastVO;
import com.chinobot.aiuas.bot_collect.resource.entity.vo.PlayUrlVO;
import com.chinobot.aiuas.bot_collect.resource.service.ILiveBroadcastService;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.controller.BaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 直播 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2020-01-16
 */
@Api(tags="直播管理接口")
@RestController
@RequestMapping("/api/bot/resource/live-broadcast")
public class LiveBroadcastController extends BaseController {
	
	@Autowired
	private ILiveBroadcastService service;

	@ApiOperation(value = "保存直播信息", notes = "保存直播信息")
	@PostMapping("/save")
	public Result save(@RequestBody LiveBroadcastVO vo) {
		service.saveLive(vo);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "获取直播信息", notes = "获取直播信息")
	@GetMapping("/get")
	public Result<LiveBroadcastVO> get() {
		LiveBroadcastVO rs = service.getLive();
		return ResultFactory.success(rs);
	}
	
	@ApiOperation(value = "获取推流地址", notes = "获取推流地址")
	@ApiImplicitParams({
		@ApiImplicitParam(name="streamName",value="无人机主键",required=true,paramType="query"),
		@ApiImplicitParam(name="days",value="过期时间,单位天；最小为1，最大数可设30天" ,required=true,paramType="query")
	})
	@GetMapping("/getSendUrl")
	public Result<String> getSendUrl(String streamName, int days){
		if(days <= 0) {
	    	days = 1;
	    }
		if(days > 30) {
	    	days = 30;
	    }
		String sendUrl = service.getSendUrl(streamName, days);
		if(sendUrl == null) {
			return ResultFactory.error("无人机信息校验失败！");
		}
		return ResultFactory.success(sendUrl);
	}
	
	@ApiOperation(value = "获取播放地址", notes = "获取播放地址")
	@ApiImplicitParams({
		@ApiImplicitParam(name="streamName",value="无人机主键",required=true,paramType="query"),
		@ApiImplicitParam(name="mode",value="格式，rtmp或flv或hls,为空默认rtmp",required=false,paramType="query")
	})
	@GetMapping("/getPlayUrl")
	public Result<List<PlayUrlVO>> getPlayUrl(String mode, String streamName){
		LiveBroadcast play = service.getOne(CommonUtils.getEqQueryWrapper("is_deleted","type",0, "2"));
		String protocol = "";
		String suffix = "";
		if(StringUtils.isBlank(mode)) {
			mode = "rtmp";
		}
		if("rtmp".equals(mode)) {
			protocol = "rtmp";
		}
		if("flv".equals(mode)) {
			protocol = "http";
			suffix = ".flv";
		}
		if("hls".equals(mode)) {
			protocol = "http";
			suffix = ".m3u8";
		}
		if(play != null) {
			List<PlayUrlVO> list = new ArrayList<PlayUrlVO>();
			StringBuilder playUrlPre = new StringBuilder();
			playUrlPre.append(protocol)
			.append("://")
			.append(play.getDomainName())
			.append("/live/")
			.append(streamName);
			PlayUrlVO yh = new PlayUrlVO();
			yh.setRateName("原画");
			yh.setPlayUrl(playUrlPre + suffix);
			PlayUrlVO gq = new PlayUrlVO();
			gq.setRateName("高清");
			gq.setPlayUrl(playUrlPre + "_900" +suffix);
			PlayUrlVO bq = new PlayUrlVO();
			bq.setRateName("标清");
			bq.setPlayUrl(playUrlPre + "_550" + suffix);
			list.add(yh);
			list.add(gq);
			list.add(bq);
			return ResultFactory.success(list);
		}
		return ResultFactory.error("无");
	}
	
}
