package com.chinobot.plep.home.routedd.service.impl;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.common.message.entity.Message;
import com.chinobot.common.message.entity.dto.MessageDto;
import com.chinobot.common.message.utils.MessageUtils;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.home.meeting.service.IMeetingService;
import com.chinobot.plep.home.meeting.service.impl.MeetingServiceImpl;
import com.chinobot.plep.home.point.entity.FixedFlyPoint;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.RoutePoint;
import com.chinobot.plep.home.point.service.IFixedFlyPointService;
import com.chinobot.plep.home.point.service.IFixedPointService;
import com.chinobot.plep.home.point.service.IRoutePointService;
import com.chinobot.plep.home.route.entity.CheckPoint;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.service.ICheckPointService;
import com.chinobot.plep.home.route.service.IRouteService;
import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.entity.DispatchDetailRoute;
import com.chinobot.plep.home.routedd.entity.DispatchDetailType;
import com.chinobot.plep.home.routedd.entity.UavDispatch;
import com.chinobot.plep.home.routedd.entity.dto.UrgentDto;
import com.chinobot.plep.home.routedd.entity.vo.UrgentUavVo;
import com.chinobot.plep.home.routedd.mapper.DispatchDetailRouteMapper;
import com.chinobot.plep.home.routedd.service.IDispatchDetailRouteService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailService;
import com.chinobot.plep.home.routedd.service.IDispatchDetailTypeService;
import com.chinobot.plep.home.routedd.service.IUavDispatchService;

import freemarker.template.TemplateException;

/**
 * <p>
 * 调度明细-路线表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-07-25
 */
@Service
public class DispatchDetailRouteServiceImpl extends BaseService<DispatchDetailRouteMapper, DispatchDetailRoute> implements IDispatchDetailRouteService {

	@Autowired
	private DispatchDetailRouteMapper dispatchDetailRouteMapper;
	@Autowired
	private ICheckPointService checkPointService;
	@Autowired
	private IFixedPointService fixedPointService;
	@Autowired
	private IRouteService routeService;
	@Autowired
	private IRoutePointService routePointService;
	@Autowired
	private IUavDispatchService uavDispatchService;
	@Autowired
	private IDispatchDetailService dispatchDetailService;
	@Autowired
	private IDispatchDetailTypeService dispatchDetailTypeService;
	@Autowired
	private IFixedFlyPointService fixedFlyPointService;
	@Autowired
	private IMeetingService meetingService;
	@Autowired
	private IPersonService personService;
	@Value("${config.templateId}")
	private String templateFlyTask;
	private final String pageUrl = "/pages/webrtc-room/index/index";
	
	@Override
	public List<Map> getRouteFlightTotal() {
		// TODO Auto-generated method stub
		return dispatchDetailRouteMapper.getRouteFlightTotal();
	}
	
	@Override
	public List<UrgentUavVo> getAllUavForUrgent() {
		return dispatchDetailRouteMapper.getAllUavForUrgent(ThreadLocalUtil.getResources().getDeptId());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveUrgent(UrgentDto dto) throws Exception {
//		LocalDateTime now = LocalDateTime.now();
//		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HHmmss");
//		String time = now.format(dateTimeFormatter);
//		String deptId = ThreadLocalUtil.getResources().getDeptId();
//		//插入起飞点
//		CheckPoint cp = new CheckPoint();
//		if(StringUtils.isBlank(dto.getUavAddress())) {
//			cp.setAddress("紧急调度起飞点" + time);
//			cp.setName("紧急调度起飞点" + time);
//		}else {
//			cp.setAddress(dto.getUavAddress());
//			cp.setName(dto.getUavAddress());
//		}
//		cp.setDeptId(deptId);
//		cp.setLatitude(dto.getUavLat());
//		cp.setLongitude(dto.getUavLng());
//		checkPointService.save(cp);
//		//插入定点
//		FixedPoint fp2 = new FixedPoint();
//		fp2.setDeptId(deptId);
//		fp2.setAddress(dto.getUavAddress());
//		fp2.setCenter(dto.getUavLng() + "," + dto.getUavLat());
//		fp2.setName(dto.getUavAddress());
//		fixedPointService.save(fp2);
//		FixedPoint fp = new FixedPoint();
//		fp.setDeptId(deptId);
//		fp.setAddress(dto.getPointAddress());
//		fp.setCenter(dto.getPointLng() + "," + dto.getPointLat());
//		fp.setName(dto.getPointAddress());
//		fixedPointService.save(fp);
//		//插入航点
//		FixedFlyPoint ffp = new FixedFlyPoint();
//		ffp.setCenter(fp.getCenter());
//		ffp.setFixedId(fp.getUuid());
//		ffp.setSort(1);
//		ffp.setName("航点1");
//		fixedFlyPointService.save(ffp);
//		//插入航线
//		Route route  = new Route();
//		route.setBuildingNum(2);
//		route.setCheckPointId(cp.getUuid());
//		route.setDeptId(deptId);
//		route.setFlyHeight(dto.getFlyHeight());
//		route.setFlySpeed(dto.getFlySpeed());
//		route.setFlyTime(dto.getTimeExpect());
//		route.setTimeMax(dto.getTimeMax());
//		route.setRouteName("紧急调度航线" + time);
//		routeService.save(route);
//		//插入航线定点关系
//		RoutePoint rp2 = new RoutePoint();
//		rp2.setPointId(fp2.getUuid());
//		rp2.setRouteId(route.getUuid());
//		rp2.setSort(1);
//		routePointService.save(rp2);
//		RoutePoint rp = new RoutePoint();
//		rp.setPointId(fp.getUuid());
//		rp.setRouteId(route.getUuid());
//		rp.setSort(2);
//		routePointService.save(rp);
//		//插入调度
//		UavDispatch ud = new UavDispatch();
//		ud.setDeptId(deptId);
//		ud.setEventNum(2);
//		ud.setRouteName("紧急调度计划" + time);
//		ud.setUavId(dto.getUavId());
//		ud.setTime(LocalDate.now());
//		ud.setTimeAll(dto.getTimeExpect());
//		ud.setFlyPerson(dto.getFlyPersonId());
//		uavDispatchService.save(ud);
//		//插入调度明细
//		DispatchDetail dd = new DispatchDetail();
//		dd.setUavDspId(ud.getUuid());
//		dd.setPointId(cp.getUuid());
//		dd.setCheckTime(dto.getTimeExpect());
//		dd.setRouteNum(1);
//		dd.setEventNum(1);
//		dd.setSort(1);
//		dd.setAreaNum(0);
//		dispatchDetailService.save(dd);
//		//插入调度明细类型
//		DispatchDetailType type = new DispatchDetailType();
//		type.setDspdtId(dd.getUuid());
//		type.setType("1");
//		dispatchDetailTypeService.save(type);
//		//插入调度明细路线
//		DispatchDetailRoute ddr = new DispatchDetailRoute();
//		ddr.setDspdtId(dd.getUuid());
//		ddr.setRouteId(route.getUuid());
//		ddr.setStatus("1");
//		this.save(ddr);
		//发送微信通知
		Person person = personService.getById(dto.getFlyPersonId());
		//发消息
		MessageDto msgDto = new MessageDto();
		msgDto.setSendPid(ThreadLocalUtil.getResources().getUuid());
		msgDto.setCode("sky-monitor");
		msgDto.setReceivePid(dto.getFlyPersonId());
		Map<String, Object> customParam = new HashMap<String, Object>();//模板参数
		customParam.put("center", dto.getPointLng() + "," + dto.getPointLat());
		customParam.put("address", dto.getPointAddress());
		Message message = MessageUtils.send(msgDto, customParam);
		String msgAddress = "(" + dto.getPointLng() + "," + dto.getPointLat() + ")" + dto.getPointAddress();
		String page = pageUrl + "?type=2&msgId=" + message.getUuid() + "&msgAddress=" + msgAddress;
		JSONObject data = new JSONObject();
		setTemplateValueObject(data, "thing5", "空中监控");//名称
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		setTemplateValueObject(data, "date2", LocalDateTime.now().format(dtf));//发送时间
		setTemplateValueObject(data, "name1", CommonUtils.doLongString(ThreadLocalUtil.getResources().getPname(), 10));//发送人
		setTemplateValueObject(data, "thing6", CommonUtils.doLongString(msgAddress, 20));//地址
		setTemplateValueObject(data, "thing3", CommonUtils.doLongString(message.getContent(), 20));//内容
		if(StringUtils.isNotBlank(person.getOpenId())) {
			meetingService.seendWxMsg(templateFlyTask, person.getOpenId(), page, data);
		}
	}

	private void setTemplateValueObject(JSONObject obj, String key, Object Val) {
		JSONObject objSon = new JSONObject();
		objSon.put("value", Val);
		obj.put(key, objSon);
	}
}
