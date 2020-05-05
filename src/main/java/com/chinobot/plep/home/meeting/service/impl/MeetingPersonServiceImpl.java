package com.chinobot.plep.home.meeting.service.impl;

import com.chinobot.plep.home.meeting.entity.Meeting;
import com.chinobot.plep.home.meeting.entity.MeetingPerson;
import com.chinobot.plep.home.meeting.entity.dto.ReadMeetingDto;
import com.chinobot.plep.home.meeting.mapper.MeetingPersonMapper;
import com.chinobot.plep.home.meeting.service.IMeetingPersonService;
import com.chinobot.plep.home.meeting.service.IMeetingService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会议人员 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-11-25
 */
@Service
public class MeetingPersonServiceImpl extends BaseService<MeetingPersonMapper, MeetingPerson> implements IMeetingPersonService {

	@Autowired
	private IMeetingService meetingService;
	
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public void readMeeting(ReadMeetingDto dto) {
		QueryWrapper meetingWrapper = new QueryWrapper();
		meetingWrapper.eq("data_status", "1");
		meetingWrapper.eq("house_num", dto.getHouseNum());
		List<Meeting> list = meetingService.list(meetingWrapper);
		if(list.size()>0) {
			UpdateWrapper<MeetingPerson> updateWrapper = new UpdateWrapper<MeetingPerson>();
			updateWrapper.eq("meeting_id", list.get(0).getUuid());
			updateWrapper.eq("data_status", "1");
			updateWrapper.eq("is_read", 0);
			updateWrapper.eq("user_id", ThreadLocalUtil.getResources().getUuid());
			MeetingPerson entity = new MeetingPerson();
			entity.setIsRead(true);
			this.update(entity, updateWrapper);
		}
	}

}
