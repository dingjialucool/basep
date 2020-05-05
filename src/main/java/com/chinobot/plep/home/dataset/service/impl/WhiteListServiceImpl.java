package com.chinobot.plep.home.dataset.service.impl;

import com.chinobot.plep.home.dataset.entity.WarningInfo;
import com.chinobot.plep.home.dataset.entity.WhiteList;
import com.chinobot.plep.home.dataset.entity.vo.FilterWarningVO;
import com.chinobot.plep.home.dataset.entity.vo.WhiteListVO;
import com.chinobot.plep.home.dataset.mapper.WhiteListMapper;
import com.chinobot.plep.home.dataset.service.IWhiteListService;
import com.chinobot.plep.home.event.entity.EventMain;
import com.chinobot.plep.home.event.mapper.EventMainMapper;
import com.chinobot.plep.home.event.service.IEventMainService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 白名单 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-10-11
 */
@Service
public class WhiteListServiceImpl extends BaseService<WhiteListMapper, WhiteList> implements IWhiteListService {

	@Autowired
	private WhiteListMapper whiteListMapper;
	@Autowired
	private EventMainMapper eventMainMapper;
	@Autowired
	private IWhiteListService whiteListService;
	@Autowired
	private IEventMainService eventMainService;
//	@Autowired
//	private IWarningInfoService warningInfoService;
	
	
	@Override
	public IPage<WhiteListVO> getWhiteList(Page page, Map<String, Object> param) {
		
		if(param.get("businessStatus")!=null && param.get("businessStatus")!="" ) {
        	param = toParam(param,"businessStatus");
        }
		IPage<WhiteListVO> whiteList = whiteListMapper.getWhiteList(page, param);
		return whiteList;
	}
	
	private Map toParam(Map param, String type) {
		
    	String str = (String) param.get(type);
    	if(str.indexOf(',')>0) {
    		String[] split = str.split(",");
    		param.put(type, split);
    	}else {
			String[] split = new String[1];
			split[0] = str;
			param.put(type, split);
		}
		return param;
	}
	
	@Override
	public boolean delWhite(String uuid) {
		boolean bo = false;
		
		//删除白名单
		UpdateWrapper<WhiteList> updateWrapperWhite = new UpdateWrapper<WhiteList>();
		updateWrapperWhite.eq("uuid", uuid);
		updateWrapperWhite.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		bo = whiteListService.update(updateWrapperWhite);
		
		return bo;
	}

	@Override
	public Map showWhite(String uuid) {
		
		Map map = whiteListMapper.showWhite(uuid);
		String waUuid = (String) map.get("waId");
		String pointId = (String) map.get("point_id");
		map.put("basicImg", eventMainMapper.getImgListByBusId(pointId, "basic_img"));//获取全部基准图片
		map.put("vedioList", eventMainMapper.getImgListByBusId(waUuid, "fly_video"));//获取楼顶加建全部视频
		map.put("imgList", eventMainMapper.getImgListByBusId(waUuid, "warning_img"));//获取预警图片
		return map;
	}

	@Override
	public IPage<FilterWarningVO> getFilterList(Page page, Map<String, Object> param) {
		
		
		IPage<FilterWarningVO> filterList = whiteListMapper.getFilterList(page, param);
        return filterList;
	}

	@Override
	public Map showFilterWarn(String uuid) {
		
		Map map = whiteListMapper.showFilterWarn(uuid);
		String emUuid = (String) map.get("emId");
		map.put("basicImg", eventMainMapper.getImgListByBusId(emUuid, "basic_img"));//获取全部基准图片
		map.put("vedioList", eventMainMapper.getImgListByBusId(emUuid, "fly_video"));//获取楼顶加建全部视频
		map.put("imgList", eventMainMapper.getImgListByBusId(emUuid, "warning_img"));//获取预警图片
		return map;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public boolean addWhite(WhiteList whiteList) {
//		boolean bo = false;
//		bo = whiteListService.save(whiteList);
//		String evenId = whiteList.getEventId();//保存白名单
//		
//		UpdateWrapper<EventMain> updateWrapperEven = new UpdateWrapper<EventMain>();
//		updateWrapperEven.eq("uuid", evenId);
//		updateWrapperEven.set("status", "4");
//		bo = eventMainService.update(updateWrapperEven);//改变事件状态为白名单
//		
//		EventMain eventMain = eventMainService.getById(evenId);
//		String warnId = eventMain.getWarningId();//预警信息id
//		UpdateWrapper<WarningInfo> updateWrapper = new UpdateWrapper<WarningInfo>();
//		updateWrapper.eq("uuid", warnId);
//		updateWrapper.set("filter_type", "3");
//		bo = warningInfoService.update(updateWrapper);//改变预警信息的过滤类型为白名单
//		
//		return bo;
		return false;
	}

	@Override
	public List<Map> getFlyPersons(HashMap param) {
		
		return whiteListMapper.getFlyPersons(param);
	}

}
