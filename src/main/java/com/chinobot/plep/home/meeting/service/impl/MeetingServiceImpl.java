package com.chinobot.plep.home.meeting.service.impl;

import java.io.IOException;
import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.mapper.PersonMapper;
import com.chinobot.common.message.entity.Message;
import com.chinobot.common.message.entity.dto.MessageDto;
import com.chinobot.common.message.utils.MessageUtils;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.HttpPostUrl;
import com.chinobot.common.utils.HttpUtils;
import com.chinobot.common.utils.TencentCloudAPIUtils;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.common.websocket.server.WebSocketServer;
import com.chinobot.framework.web.event.KafkaMsgEvent;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.meeting.entity.Meeting;
import com.chinobot.plep.home.meeting.entity.MeetingPerson;
import com.chinobot.plep.home.meeting.entity.MeetingUav;
import com.chinobot.plep.home.meeting.entity.dto.AddMeetingPersonDTO;
import com.chinobot.plep.home.meeting.entity.dto.ReadMeetingDto;
import com.chinobot.plep.home.meeting.entity.dto.SendMeetingDTO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingDetailVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingUavVO;
import com.chinobot.plep.home.meeting.entity.vo.MeetingVO;
import com.chinobot.plep.home.meeting.entity.vo.PersonVo;
import com.chinobot.plep.home.meeting.mapper.MeetingMapper;
import com.chinobot.plep.home.meeting.service.IMeetingPersonService;
import com.chinobot.plep.home.meeting.service.IMeetingService;
import com.chinobot.plep.home.meeting.service.IMeetingUavService;


/**
 * <p>
 * 会议 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-11-25
 */
@Service
public class MeetingServiceImpl extends BaseService<MeetingMapper, Meeting> implements IMeetingService {

	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private IMeetingPersonService meetingPersonService;
	@Autowired
	private MeetingMapper meetingMapper;
	@Autowired
	private IReUavService iReUavService;
	
	@Value("${config.wxAppId}")
	private String APP_ID;
	
	@Value("${config.wxAppSecret}")
	private String APP_SECRET;
	
	@Value("${config.templateId}")
	private String APP_TEMPLATE;
	
	//@Value("${config.newTemplateId}")
	//private String APP_TEMPLATE_NEW;//紧急调度用
	
	@Value("${config.accessTokenUrl}")
	private String TOKEN_URL;
	
	@Value("${config.sendMsgUrl}")
	private String SEND_URL;
	
	@Value("${config.trtcAppId}")
	private String TRTC_APPID;
	
	@Value("${config.secretId}")
	private String SECRET_ID;
	
	@Value("${config.secretKey}")
	private String SECRET_KEY;
	
	private String token = null;
	
	private LocalDateTime tokenTime;
	
	@Autowired
	private IMeetingUavService meetingUavService;
	
	private final String pageUrl = "/pages/webrtc-room/index/index";

	@Override
	public List<PersonVo> getAllOpenIdPerson() {
//		return personMapper.getAllOpenIdPerson(ThreadLocalUtil.getResources().getDeptId());
		return personMapper.getAllOpenIdPerson(null);
	}

	@Override
	public Map<String, Object> sendMeeting(SendMeetingDTO dto) throws Exception {
		Map<String, Object> rsMap = new HashMap<String, Object>();

		//插数据库和发websocket
		String meetingId = insertAndWebsocket(ThreadLocalUtil.getResources().getUuid(), dto);
		Map<String, Object> rsMapAll = new HashMap<String, Object>();
		//发消息
		MessageDto msgDto = new MessageDto();
		msgDto.setSendPid(ThreadLocalUtil.getResources().getUuid());
		msgDto.setUrlParams("type=joinConference&houseNum="+dto.getHouseNum()+"&conferenceId="+ meetingId);
		Map<String, Object> customParam = new HashMap<String, Object>();//模板参数
		customParam.put("lng", dto.getLongitude());
		customParam.put("lat", dto.getLatitude());
		customParam.put("address", dto.getAddress());
		String templateId = APP_TEMPLATE;
		JSONObject data = packTemplateData(meetingId);
		for(Map<String, String> map : dto.getPerson()) {
		if(!ThreadLocalUtil.getResources().getUuid().equals(map.get("userId"))) {
			msgDto.setReceivePid(map.get("userId"));
			Person ps = (Person) personMapper.selectById(map.get("userId"));
			msgDto.setCode("meeting-other");
			if(dto.getLatitude() != null && dto.getLongitude() != null) {
				//templateId = APP_TEMPLATE_NEW;
				if("duties_fly".equals(ps.getDuties())) {
					msgDto.setCode("meeting-fly");
				}
				if("duties_grid".equals(ps.getDuties())) {
					msgDto.setCode("meeting-grid");
				}
			}
			//发消息
			Message message = MessageUtils.send(msgDto, customParam);
			//if(dto.getLatitude() != null && dto.getLongitude() != null) {
				setTemplateValueObject(data, "thing3", CommonUtils.doLongString(message.getContent(), 20));//会议消息内容
			//}
			String page = pageUrl + "?type=1&roomNum="+ dto.getHouseNum()+"&msgId=" + message.getUuid();
			if(StringUtils.isNotBlank(map.get("openId"))) {
				String rsStr = seendWxMsg(templateId, map.get("openId"), page, data);
				rsMap.put(map.get("openId"), JSONObject.parseObject(rsStr));
			}
		}
	}
		rsMapAll.put("meetingId", meetingId);
		rsMapAll.put("openIdResult", rsMap);
		return rsMapAll;
	}

	@Override
	public IPage<MeetingVO> pageMeetingToday(Page page) {
		Map<String, Object> param = new HashMap<String, Object>();
		Person person = ThreadLocalUtil.getResources();
		param.put("userId", person.getUuid());
		param.put("onlyToday", true);
		return meetingMapper.pageMeetingByUserId(page, param);
	}

	@Override
	public IPage<MeetingVO> pageMeetingAll(Page page) {
		Map<String, Object> param = new HashMap<String, Object>();
		Person person = ThreadLocalUtil.getResources();
		param.put("userId", person.getUuid());
		param.put("onlyToday", false);
		param.put("unread", false);
		return meetingMapper.pageMeetingByUserId(page, param);
	}
	
	@Override
	public IPage<MeetingVO> pageMeetingUnread(Page page) {
		Map<String, Object> param = new HashMap<String, Object>();
		Person person = ThreadLocalUtil.getResources();
		param.put("userId", person.getUuid());
		param.put("onlyToday", false);
		param.put("unread", true);
		return meetingMapper.pageMeetingByUserId(page, param);
	}
	
	@Override
	public Long countUnReadMeeting() {
		Person person = ThreadLocalUtil.getResources();
		return meetingMapper.countUnReadMeeting(person.getUuid());
	}
	@Transactional
	public String insertAndWebsocket(String userId, SendMeetingDTO dto) throws Exception {
		//插数据库
		//会议
		Meeting meeting = new Meeting();
		meeting.setHouseNum(dto.getHouseNum());
		if(StringUtils.isNotBlank(dto.getMeetingName())) {
			meeting.setMeetingName(dto.getMeetingName());
		}else {
			meeting.setMeetingName("专项行动");
		}
		
		meeting.setPromoter(userId);
		//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		meeting.setPromotTime(LocalDateTime.now());
		meeting.setRemark(dto.getRemark());
		meeting.setIsClosed(false);
		meeting.setAddress(dto.getAddress());
		meeting.setLongitude(dto.getLongitude());
		meeting.setLatitude(dto.getLatitude());
		this.save(meeting);
		//会议人员
		if(dto.getPerson() != null && dto.getPerson().size()>0) {
			for(Map<String, String> map : dto.getPerson()) {
				MeetingPerson mp = new MeetingPerson();
				mp.setIsRead(false);
				if(userId.equals(map.get("userId"))) {
					mp.setIsRead(true);
				}
				mp.setMeetingId(meeting.getUuid());
				mp.setUserId(map.get("userId"));
				meetingPersonService.save(mp);
			}
		}
		//参会无人机
		if(dto.getUavs() != null && dto.getUavs().size()>0) {
			for(String uavId : dto.getUavs()) {
				MeetingUav uav = new MeetingUav();
				uav.setMeetingId(meeting.getUuid());
				uav.setUavId(uavId);
				meetingUavService.save(uav);
			}
		}
		//websocket
		//createMeetingWebSocket(userId, dto);
		return meeting.getUuid();
	}
	private void createMeetingWebSocket(String userId, SendMeetingDTO dto) throws Exception {
		if(dto.getPerson() != null && dto.getPerson().size()>0) {
			Person perosn = (Person) personMapper.selectById(userId);
			List<MeetingVO> list = new ArrayList<MeetingVO>();
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime time = LocalDateTime.parse(dto.getMeetingTime(), dtf);
			MeetingDetailVO meeting = meetingDetailByRoomNum(dto.getHouseNum());
			for(Map<String, String> map : dto.getPerson()) {
					MeetingVO meetingVo = new MeetingVO();
					meetingVo.setUuid(meeting.getUuid());
					meetingVo.setHouseNum(dto.getHouseNum());
					meetingVo.setMeetingName(dto.getMeetingName());
					meetingVo.setUserId(map.get("userId"));
					meetingVo.setPname(perosn.getPname());
					meetingVo.setPromotTime(time);
					meetingVo.setRemark(dto.getRemark());
					meetingVo.setIsClosed(false);
					meetingVo.setIsRead(false);
					meetingVo.setPersonNum(Long.parseLong(((Integer)dto.getPerson().size()).toString()));
					if(!userId.equals(map.get("userId"))) {
						list.add(meetingVo);
					}
					
				}
			JSONObject jsb = new JSONObject ();
			jsb.put ("key", "meeting-online");
			jsb.put ("msg", list);
			WebSocketServer.sendInfo("meeting-online", jsb.toJSONString());
		}
		
	}
	private void addPersonMeetingWebSocket(AddMeetingPersonDTO dto) throws Exception {
		if(dto.getPerson() != null && dto.getPerson().size()>0) {
			MeetingDetailVO meeting = meetingDetailByRoomNum(dto.getHouseNum());
			List<MeetingVO> list = new ArrayList<MeetingVO>();
			for(Map<String, String> map : dto.getPerson()) {
				MeetingVO meetingVo = new MeetingVO();
				meetingVo.setUuid(meeting.getUuid());
				meetingVo.setHouseNum(meeting.getHouseNum());
				meetingVo.setMeetingName(meeting.getMeetingName());
				meetingVo.setUserId(map.get("userId"));
				meetingVo.setPname(meeting.getPname());
				meetingVo.setPromotTime(meeting.getPromotTime());
				meetingVo.setRemark(meeting.getRemark());
				meetingVo.setIsClosed(false);
				meetingVo.setIsRead(false);
				meetingVo.setPersonNum(Long.parseLong(((Integer)(meeting.getPerson().size() + dto.getPerson().size())).toString()));
				list.add(meetingVo);
			}
			JSONObject jsb = new JSONObject ();
			jsb.put ("key", "meeting-online");
			jsb.put ("msg", list);
			WebSocketServer.sendInfo("meeting-online", jsb.toJSONString());
		}
		if(dto.getUavs() != null && dto.getUavs().size()>0) {
			List<MeetingUavVO> list = new ArrayList<MeetingUavVO>();
			for(String uavId : dto.getUavs()) {
				MeetingUavVO uavVO = meetingMapper.meetingUavVOById(uavId);
				list.add(uavVO);
			}
			JSONObject jsb = new JSONObject ();
			jsb.put ("key", "meeting-uav");
			jsb.put ("msg", list);
			WebSocketServer.sendInfo("meeting-uav", jsb.toJSONString());
		}
	}
	private void kickOutUavWebsocket(BigInteger houseNum, String uavId) throws Exception {
		JSONObject jsb = new JSONObject ();
		jsb.put ("key", "meeting-uav-out");
		JSONObject msg = new JSONObject ();
		msg.put("room", houseNum);
		msg.put("uavId", uavId);
		jsb.put ("msg", msg);
		WebSocketServer.sendInfo("meeting-uav-out", jsb.toJSONString());
	}
	@EventListener
	public void handelMsg(KafkaMsgEvent kafkaMsgEvent) throws Exception{
		String key = kafkaMsgEvent.getRecord().key();
//		if("meeting-online".equals(key)) {
//			String value = kafkaMsgEvent.getRecord().value();
//			JSONObject jsonObject = JSON.parseObject(value);
//			String userId = jsonObject.getString("userId");
//			SendMeetingDTO dto = jsonObject.getObject("meetingInfo", SendMeetingDTO.class);
//			createMeetingWebSocket(userId, dto);
//		}
		if("meeting-add-person".equals(key)) {
			String value = kafkaMsgEvent.getRecord().value();
			JSONObject jsonObject = JSON.parseObject(value);
			AddMeetingPersonDTO dto = jsonObject.getObject("meetingInfo", AddMeetingPersonDTO.class);
			addPersonMeetingWebSocket(dto);
		}
//		if("meeting-uav-out".equals(key)) {
//			String value = kafkaMsgEvent.getRecord().value();
//			JSONObject jsonObject = JSON.parseObject(value);
//			kickOutUavWebsocket(jsonObject.getBigInteger("room"), jsonObject.getString("uavId"));
//		}
	}
	private String sendMeetingRequest(String openId, String page, JSONObject data) {
		String accessToken = getAccessToken();
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("touser", openId);
		obj.put("template_id", APP_TEMPLATE);
		obj.put("page", page);
		obj.put("data", data);
		String url = SEND_URL + "?access_token=" + accessToken;
		String rs = "网络请求错误";
		try {
			rs = HttpPostUrl.postJson(url, obj);
			if(rs.contains("42001")) {
				generateToken();//万一还是过期了
				accessToken = getAccessToken();
				url = SEND_URL + "?access_token=" + accessToken;
				rs = HttpPostUrl.postJson(url, obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return rs;
	}
	@Override
	public String seendWxMsg(String templateId,String openId, String page, JSONObject data) {
		String accessToken = getAccessToken();
		Map<String, Object> obj = new HashMap<String, Object>();
		obj.put("touser", openId);
		obj.put("template_id", templateId);
		obj.put("page", page);
		obj.put("data", data);
		String url = SEND_URL + "?access_token=" + accessToken;
		String rs = "网络请求错误";
		try {
			rs = HttpPostUrl.postJson(url, obj);
			if(rs.contains("42001")) {
				generateToken();//万一还是过期了
				accessToken = getAccessToken();
				url = SEND_URL + "?access_token=" + accessToken;
				rs = HttpPostUrl.postJson(url, obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
			
		}
		return rs;
	}
	private String getAccessToken() {
		if(token == null) {
			generateToken();
		}else {
			LocalDateTime now = LocalDateTime.now();
			Duration duration = Duration.between(tokenTime, now);
			long minutes = duration.toMinutes();
			if(minutes > 110) {
				generateToken();
			}
		}
		return token;
	}
	private void generateToken() {
		String rs = HttpPostUrl.sendGet(TOKEN_URL, "grant_type=client_credential&appid=" + APP_ID + "&secret=" + APP_SECRET);
		JSONObject jsonObject = JSONObject.parseObject(rs);
		token = jsonObject.getString("access_token");
		tokenTime = LocalDateTime.now();
	}

	@Override
	public void closeMeeting(ReadMeetingDto dto) {
		UpdateWrapper<Meeting> updateWrapper = new UpdateWrapper<Meeting>();
		updateWrapper.eq("house_num", dto.getHouseNum());
		updateWrapper.eq("data_status", "1");
		updateWrapper.eq("is_closed", "0");
		Meeting entity = new Meeting();
		entity.setIsClosed(true);
		this.update(entity, updateWrapper);
	}

	@Override
	public PersonVo personVoById(String uuid) {
		return personMapper.personVoById(uuid);
	}

	@Override
	public MeetingDetailVO meetingDetailById(String uuid) {
		MeetingDetailVO vo = meetingMapper.meetingDetailById(uuid);
		sortPerson(vo);
		return vo;
	}

	@Override
	public MeetingDetailVO meetingDetailByRoomNum(BigInteger houseNum) {
		MeetingDetailVO vo = meetingMapper.meetingDetailByRoomNum(houseNum);
		sortPerson(vo);
		return vo;
	}
	
	private void sortPerson(MeetingDetailVO vo) {
		List<PersonVo> persons = vo.getPerson();
		for(int i=0; i<persons.size(); i++) {
			if(persons.get(i).getUuid().equals(vo.getPuuid())) {
				PersonVo fqr = persons.get(i);
				persons.remove(i);
				persons.add(0, fqr);
				return ;
			}
		}
	}

	@Override
	public String kickOutUser(String room, String userId) throws Exception {
		String url = TencentCloudAPIUtils.getKickOutUserUrl(room, userId, TRTC_APPID, SECRET_ID, SECRET_KEY);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("room", BigInteger.valueOf(Long.valueOf(room)));
		meetingMapper.kickOutUser(param);
		return HttpPostUrl.sendGet(url, "");
	}

	@Override
	public void kickOutUav(BigInteger houseNum, String uavId) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("uavId", uavId);
		param.put("room", houseNum);
		meetingMapper.kickOutUav(param);
		kickOutUavWebsocket(houseNum, uavId);
	}

	@Override
	public void addMeetingPerson(AddMeetingPersonDTO dto) throws Exception {
		MeetingDetailVO meeting = meetingDetailByRoomNum(dto.getHouseNum());
		//会议人员
		if(dto.getPerson() != null && dto.getPerson().size()>0) {
			for(Map<String, String> map : dto.getPerson()) {
//				if(StringUtils.isNotBlank(map.get("openId")) && !meeting.getPuuid().equals(map.get("userId"))) {
//					sendMeetingRequest(map.get("openId"), dto.getPage(), dto.getData());
//				}
				MeetingPerson mp = new MeetingPerson();
				mp.setIsRead(false);
				mp.setMeetingId(meeting.getUuid());
				mp.setUserId(map.get("userId"));
				meetingPersonService.save(mp);
			}
		}
		//参会无人机
		if(dto.getUavs() != null && dto.getUavs().size()>0) {
			for(String uavId : dto.getUavs()) {
				MeetingUav uav = new MeetingUav();
				uav.setMeetingId(meeting.getUuid());
				uav.setUavId(uavId);
				meetingUavService.save(uav);
			}
		}
		if(dto.getPerson() != null && dto.getPerson().size()>0) {
			MessageDto msgDto = new MessageDto();
			msgDto.setSendPid(ThreadLocalUtil.getResources().getUuid());
			msgDto.setUrlParams("type=joinConference&houseNum="+dto.getHouseNum()+"&conferenceId="+ meeting.getUuid());
			Map<String, Object> customParam = new HashMap<String, Object>();//模板参数
			customParam.put("lng", meeting.getMeetingLongitude());
			customParam.put("lat", meeting.getMeetingLatitude());
			customParam.put("address", meeting.getAddress());
			String templateId = APP_TEMPLATE;
			JSONObject data = packTemplateData(meeting.getUuid());
			for(Map<String, String> map : dto.getPerson()) {
				if(!meeting.getPuuid().equals(map.get("userId"))) {
					msgDto.setReceivePid(map.get("userId"));
					Person ps = (Person) personMapper.selectById(map.get("userId"));
					msgDto.setCode("meeting-other");
					if(meeting.getMeetingLongitude() != null && meeting.getMeetingLatitude() != null) {
						//templateId = APP_TEMPLATE_NEW;
						if("duties_fly".equals(ps.getDuties())) {
							msgDto.setCode("meeting-fly");
						}
						if("duties_grid".equals(ps.getDuties())) {
							msgDto.setCode("meeting-grid");
						}
					}
					//发消息
					Message message = MessageUtils.send(msgDto, customParam);
					//if(meeting.getMeetingLongitude() != null && meeting.getMeetingLatitude() != null) {
						setTemplateValueObject(data, "thing3", CommonUtils.doLongString(message.getContent(), 20));//会议消息内容
					//}
					String page = pageUrl + "?type=1&roomNum="+ dto.getHouseNum()+"&msgId=" + message.getUuid();
					if(StringUtils.isNotBlank(map.get("openId"))) {
						String rsStr = seendWxMsg(templateId, map.get("openId"), page, data);
					}
					
				}
			}
		}	
		//websocket
		//addPersonMeetingWebSocket(dto);
		
	}
	
	@Override
	public Boolean checkRoomPerson(String userId, BigInteger houseNum) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", userId);
		param.put("room", houseNum);
		MeetingPerson mp = meetingMapper.meetingPersonByUserIdAndRoom(param );
		if(mp != null) {
			return true;
		}
		return false;
	}

	@Override
	public IPage<MeetingUavVO> getUavs(Page page) {
//		IPage<MeetingUavVO> uavs = meetingMapper.getUavs(page, ThreadLocalUtil.getResources().getDeptId());
		IPage<MeetingUavVO> uavs = meetingMapper.getReUavs(page, null);
		for(MeetingUavVO vo : uavs.getRecords()) {
			if(vo.getLongitude() != null && vo.getLatitude() != null) {
				vo.setAddress(HttpUtils.lnglatToAddress(vo.getLongitude().toString(), vo.getLatitude().toString()));
			}
		}
		return uavs;
	}
	
	private JSONObject packTemplateData(String meetingId) {
		MeetingDetailVO vo = meetingDetailById(meetingId);
		JSONObject all = new JSONObject();
		//会议名称
		setTemplateValueObject(all, "thing5", CommonUtils.doLongString(vo.getMeetingName(), 20));
		//发起人
		setTemplateValueObject(all, "name1", CommonUtils.doLongString(vo.getPname(), 10));
		//发起时间
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		setTemplateValueObject(all, "date2", vo.getPromotTime().format(dtf));
		//参与人
//		StringBuffer sb = new StringBuffer();
//		for(PersonVo ps : vo.getPerson()) {
//			sb.append(ps.getPname()).append(",");
//		}
//		setTemplateValueObject(all, "thing4", CommonUtils.doLongString(sb.substring(0, sb.length()-1), 20));
		//调度地址
		String address = "无";
		if(vo.getMeetingLatitude() != null && vo.getMeetingLongitude() != null) {
			address = "(" + vo.getMeetingLongitude() + "," + vo.getMeetingLatitude() + ")" + vo.getAddress();
		}
		setTemplateValueObject(all, "thing6", CommonUtils.doLongString(address, 20));
		return all;
	}
	
	private void setTemplateValueObject(JSONObject obj, String key, Object Val) {
		JSONObject objSon = new JSONObject();
		objSon.put("value", Val);
		obj.put(key, objSon);
	}
}
