package com.chinobot.plep.home.meeting.service;

import com.chinobot.plep.home.meeting.entity.MeetingPerson;
import com.chinobot.plep.home.meeting.entity.dto.ReadMeetingDto;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 会议人员 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-11-25
 */
public interface IMeetingPersonService extends IBaseService<MeetingPerson> {

	void readMeeting(ReadMeetingDto dto);

}
