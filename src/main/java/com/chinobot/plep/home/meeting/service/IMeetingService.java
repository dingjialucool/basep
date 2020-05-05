package com.chinobot.plep.home.meeting.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.event.KafkaMsgEvent;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.meeting.entity.Meeting;
import com.chinobot.plep.home.meeting.entity.dto.AddMeetingPersonDTO;
import com.chinobot.plep.home.meeting.entity.dto.ReadMeetingDto;
import com.chinobot.plep.home.meeting.entity.dto.SendMeetingDTO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingDetailVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingUavVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingVO;
import com.chinobot.plep.home.meeting.entity.vo.PersonVo;

/**
 * <p>
 * 会议 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-11-25
 */
public interface IMeetingService extends IBaseService<Meeting> {

	List<PersonVo> getAllOpenIdPerson();
	
	Map<String, Object> sendMeeting(SendMeetingDTO dto) throws Exception;
	
	IPage<MeetingVO> pageMeetingToday(Page page);
	
	IPage<MeetingVO> pageMeetingAll(Page page);
	
	void handelMsg(KafkaMsgEvent kafkaMsgEvent) throws Exception;
	
	Long countUnReadMeeting();

	void closeMeeting(ReadMeetingDto dto);
	
	PersonVo personVoById(String uuid);
	
	MeetingDetailVO meetingDetailById(String uuid);
	
	String kickOutUser(String room, String userId) throws Exception;

	void kickOutUav(BigInteger houseNum, String uavId) throws Exception;

	void addMeetingPerson(AddMeetingPersonDTO dto) throws Exception;

	IPage<MeetingVO> pageMeetingUnread(Page page);

	MeetingDetailVO meetingDetailByRoomNum(BigInteger houseNum);
	
	Boolean checkRoomPerson(String userId, BigInteger houseNum);
	
	IPage<MeetingUavVO> getUavs(Page page);

	String seendWxMsg(String templateId, String openId, String page, JSONObject data);
}
