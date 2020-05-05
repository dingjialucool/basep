package com.chinobot.plep.home.meeting.mapper;

import java.math.BigInteger;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.meeting.entity.Meeting;
import com.chinobot.plep.home.meeting.entity.MeetingPerson;
import com.chinobot.plep.home.meeting.entity.vo.MeetingDetailVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingUavVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingVO;

/**
 * <p>
 * 会议 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-11-25
 */
public interface MeetingMapper extends IBaseMapper<Meeting> {

	IPage<MeetingVO> pageMeetingByUserId(Page page, @Param("p") Map<String, Object> param);
	
	Long countUnReadMeeting(@Param("userId") String userId);
	
	MeetingDetailVO meetingDetailById(@Param("uuid") String uuid);
	
	void kickOutUser(@Param("p") Map<String, Object> param);
	
	void kickOutUav(@Param("p") Map<String, Object> param);

	MeetingDetailVO meetingDetailByRoomNum(@Param("houseNum") BigInteger houseNum);
	
	MeetingPerson meetingPersonByUserIdAndRoom(@Param("p") Map<String, Object> param);
	
	IPage<MeetingUavVO> getUavs(Page page, @Param("deptId") String deptId);

	IPage<MeetingUavVO> getReUavs(Page page, @Param("deptId") String deptId);

	MeetingUavVO meetingUavVOById(@Param("uuid") String uuid);
}
