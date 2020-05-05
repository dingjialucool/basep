package com.chinobot.plep.home.route.service.impl;

import com.chinobot.aiuas.bot_resource.uav.service.IReUavService;
import com.chinobot.plep.home.homepage.entity.vo.HomePageAreaRendaSceneVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageEarlyWarningDeptMonthVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageTaskCountOfPointVo;
import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import com.chinobot.plep.home.route.entity.CheckPoint;
import com.chinobot.plep.home.route.mapper.CheckPointMapper;
import com.chinobot.plep.home.route.service.ICheckPointService;
import com.google.common.collect.Maps;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 起飞点表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-07-16
 */
@Service
public class CheckPointServiceImpl extends BaseService<CheckPointMapper, CheckPoint> implements ICheckPointService {
	@Autowired
	private CheckPointMapper checkPointMapper;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IReUavService iReUavService;

	@Override
	public List<CheckPoint> getAllCheckPointBydept(Map<String, String> param) {
		
		return checkPointMapper.getAllCheckPointBydept(param);
	}
	
	
	@Override
	public List<HomePageTaskCountOfPointVo> getCheckPointAndTaskCount(Map<String, Object> param) {
		return checkPointMapper.getCheckPointAndTaskCount(param);
	}
	
	@Override
	public List<String> getRangePolygon(Map<String, Object> param) {
		return checkPointMapper.getRangePolygon(param);
	}
	@Override
	public String getDeptPolygon(Map<String, Object> param) {
		return checkPointMapper.getDeptPolygon(param);
	}
	@Override
	public Long getMaxCount(Map<String, Object> param) {
		return checkPointMapper.getMaxCount(param);
	}
	@Override
	public List<HomePageAreaRendaSceneVo> getPathCountByDeptAndScene(Map<String, Object> param) {
		return checkPointMapper.getPathCountByDeptAndScene(param);
	}
	@Override
	public List<HomePageEarlyWarningDeptMonthVo> getHomePageEarlyWarningInfo(Map<String, Object> param) {
		return checkPointMapper.getHomePageEarlyWarningInfo(param);
	}


	@Override
	public Long getCountOfPath(Map<String, String> param) {
		return checkPointMapper.getCountOfPath(param);
	}


	@Override
	public Long getCountOfCheckPoint(Map<String, String> param) {
		return checkPointMapper.getCountOfCheckPoint(param);
	}


	@Override
	public Long getCountOfLine(Map<String, Object> param) {
		
		return checkPointMapper.getCountOfLine(param);
	}


	@Override
	public List<String> getRangeByLinePolygon(Map<String, Object> param) {
		
		return checkPointMapper.getRangeByLinePolygon(param);
	}


	@Override
	public String getTotalOfFlyTime(Map<String, Object> param) {
		
		return checkPointMapper.getTotalOfFlyTime(param);
	}


	@Override
	public List<Map> getFlyNum(Map<String, Object> param) {
		
		return checkPointMapper.getFlyNum(param);
	}


	@Override
	public IPage<HomePageUavVo> getUavByDept(Page<HomePageUavVo> page, String uuid) {
//		IPage<HomePageUavVo> uavs = checkPointMapper.getUavByDept(page, ThreadLocalUtil.getResources().getDeptId(), uuid);
		IPage<HomePageUavVo> uavs = iReUavService.getReUavPage(page, new HashMap<>());
		List<HomePageUavVo> records = uavs.getRecords();
		for (HomePageUavVo homePageUavVo : records) {
			String uavId = homePageUavVo.getUuid();
			HashMap param = Maps.newHashMap();
			param.put("busId", uavId);
			param.put("url", GlobalConstant.IO_READ_IMAGE_PREFIX);
			param.put("module", "equipment_video");
			homePageUavVo.setFileLists(fileBusService.getFileIdByBusId(param));;//设备上传视频
		}
		
		return uavs.setRecords(records);
	}

}
