package com.chinobot.plep.home.meeting.controller;


import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.chinobot.plep.home.meeting.entity.Meeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.domain.Result;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.controller.BaseController;
import com.chinobot.plep.home.meeting.entity.dto.AddMeetingPersonDTO;
import com.chinobot.plep.home.meeting.entity.dto.ReadMeetingDto;
import com.chinobot.plep.home.meeting.entity.dto.SendMeetingDTO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingDetailVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingUavVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingVO;
import com.chinobot.plep.home.meeting.entity.vo.PersonVo;
import com.chinobot.plep.home.meeting.service.IMeetingPersonService;
import com.chinobot.plep.home.meeting.service.IMeetingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 会议 前端控制器
 * </p>
 *
 * @author huangw
 * @since 2019-11-25
 */
@RestController
@RequestMapping("/api/meeting")
@Api(tags="视频会议接口")
public class MeetingController extends BaseController {

	@Autowired
	private IMeetingService meetingService;
	
	@Autowired
	private IMeetingPersonService mpService;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;
	
	/**
	 * 房间号
	 */
	public static final String HOUSE_ROOM = "house_room";
	
	/**
	 * 最大房间号
	 */
	public static final int MAX_HOUSE_ROOM =  99999998;
	
	/**
	 * 自增数
	 */
	public static final int INCREMENT = 1;
	
	/**
	 * 重置为0
	 */
	public static final String STR = "0";
	
	
	@ApiOperation(value = "获取人员", notes = "无需传参")
	@GetMapping("/getPerson")
	public Result<List<PersonVo>> getPerson(){
		return ResultFactory.success(meetingService.getAllOpenIdPerson());
	}
	
	@ApiOperation(value = "发起视频会议接口", notes = "返回结果：{'meetingId': '' ,  'openIdResult': {'openId1' : '对应的接口返回结果', 'openId2' : '对应的接口返回结果'}}")
	@PostMapping("/sendMeeting")
	public Result<Map<String, Object>> sendMeeting(@RequestBody SendMeetingDTO dto) throws Exception{
		
		return ResultFactory.success(meetingService.sendMeeting(dto));
	}
	
	@ApiOperation(value = "会议已读接口", notes = "传房间号即可")
	@PostMapping("/readMeeting")
	public Result readMeeting(@RequestBody ReadMeetingDto dto){
		mpService.readMeeting(dto);
		return ResultFactory.success();
	}
	
	
	/*
	 * @ApiOperation(value = "获取今日会议分页", notes = "获取今日会议分页")
	 * 
	 * @GetMapping("/pageMeetingToday") public Result<IPage<MeetingVO>>
	 * pageMeetingToday(Page page){ return
	 * ResultFactory.success(meetingService.pageMeetingToday(page)); }
	 */
	  
	 @ApiOperation(value = "获取所有有效会议分页", notes = "获取所有有效会议分页")
	 @GetMapping("/pageMeetingAll") 
	 public Result<IPage<MeetingVO>> pageMeetingAll(Page page){ 
		 return ResultFactory.success(meetingService.pageMeetingAll(page)); 
	}
	 
	 @ApiOperation(value = "获取未读有效会议分页", notes = "获取未读会议分页")
	 @GetMapping("/pageMeetingUnread") 
	 public Result<IPage<MeetingVO>> pageMeetingUnread(Page page){ 
		 return ResultFactory.success(meetingService.pageMeetingUnread(page)); 
	}
	
	@ApiOperation(value = "获取登录用户的openId", notes = "无需传参")
	@GetMapping("/getMyOpenId")
	public Result<String> getMyOpenId() {
		return ResultFactory.success(ThreadLocalUtil.getResources().getOpenId());
	}
	
	@ApiOperation(value = "获取登录用户的有效未读数", notes = "无需传参")
	@GetMapping("/countUnReadMeeting")
	public Result<Long> countUnReadMeeting(){
		return ResultFactory.success(meetingService.countUnReadMeeting());
	}
	
	@ApiOperation(value = "会议关闭接口", notes = "传房间号即可")
	@PostMapping("/closeMeeting")
	public Result closeMeeting(@RequestBody ReadMeetingDto dto){
		meetingService.closeMeeting(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "根据id获取用户信息", notes = "根据id获取用户信息")
	@GetMapping("/PersonVoById")
	public Result<PersonVo> PersonVoById(@ApiParam(name = "id", value = "人员主键uuid", required = true)String id){
		return ResultFactory.success(meetingService.personVoById(id));
	}
	
	@ApiOperation(value = "根据id获取会议详细信息", notes = "根据id获取会议详细信息")
	@GetMapping("/meetingDetailById")
	public Result<MeetingDetailVO> meetingDetailById(@ApiParam(name = "id", value = "会议主键uuid", required = true) String id){
		return ResultFactory.success(meetingService.meetingDetailById(id));
	}
	
	@ApiOperation(value = "根据房间号获取会议详细信息", notes = "根据房间号获取会议详细信息")
	@GetMapping("/meetingDetailByRoomNum")
	public Result<MeetingDetailVO> meetingDetailByRoomNum(@ApiParam(name = "roomNum", value = "房间号", required = true)BigInteger roomNum){
		return ResultFactory.success(meetingService.meetingDetailByRoomNum(roomNum));
	}
	
	@ApiOperation(value = "踢人接口", notes = "返回接口调用结果")
	@GetMapping("/kickOutUser")
	public Result<String> kickOutUser(@ApiParam(name = "houseNum", value = "房间号", required = true)String houseNum, @ApiParam(name = "userId", value = "用户ID", required = true)String userId) throws Exception{
		return ResultFactory.success(meetingService.kickOutUser(houseNum, userId));
	}
	
	@ApiOperation(value = "踢无人机接口", notes = "踢无人机接口")
	@GetMapping("/kickOutUav")
	public Result kickOutUav(@ApiParam(name = "houseNum", value = "房间号", required = true)BigInteger houseNum, @ApiParam(name = "uavId", value = "无人机ID", required = true)String uavId) throws Exception{
		meetingService.kickOutUav(houseNum, uavId);
		return ResultFactory.success();
	}
	@ApiOperation(value = "加人加无人机接口", notes = "加人加无人机接口")
	@PostMapping("/addMeetingPerson")
	public Result addMeetingPerson(@RequestBody AddMeetingPersonDTO dto) throws Exception{
		meetingService.addMeetingPerson(dto);
		return ResultFactory.success();
	}
	
	@ApiOperation(value = "进房权限接口", notes = "进房权限接口")
	@GetMapping("/checkRoomUser")
	public Result<Boolean> checkRoomUser(@ApiParam(name = "houseNum", value = "房间号", required = true)BigInteger houseNum) throws Exception{
		return ResultFactory.success(meetingService.checkRoomPerson(ThreadLocalUtil.getResources().getUuid(), houseNum));
	}

	/**
	 *  获取房间号
	 * @return
	 */
	@ApiOperation(value = "获取房间号", notes = "获取本部门下的无人机")
	@GetMapping("/getRoomNum")
	public Result getRoomNum() {
		
		return ResultFactory.success(getNum());
	}

	@ApiOperation(value = "获取本部门下的无人机", notes = "获取本部门下的无人机")
	@GetMapping("/getUavs")
	public Result<IPage<MeetingUavVO>> getUavs(Page page){
		return ResultFactory.success(meetingService.getUavs(page));
	}

	/**
	 * 检查房间是否关闭
	 * @Param: [id]
	 * @Return: com.chinobot.common.domain.Result<java.lang.Boolean>
	 * @Author: shizt
	 * @Date: 2019/12/9 20:53
	 */
	@ApiOperation(value = "检查房间是否关闭", notes = "检查房间是否关闭")
	@GetMapping("/checkCloseRoom")
	public Result<Boolean> checkCloseRoom(@ApiParam(name = "id", value = "会议主键uuid", required = true) String id){
		return ResultFactory.success(meetingService.getById(id).getIsClosed());
	}

	/**
	 *  用Redis获取房间号
	 * @return
	 */
	private Long getNum() {
		
		Long increment = redisTemplate.opsForValue().increment(HOUSE_ROOM, INCREMENT);
		if (increment > MAX_HOUSE_ROOM) {
			redisTemplate.opsForValue().set(HOUSE_ROOM, STR);
		}
		return increment;
	}
	
}
