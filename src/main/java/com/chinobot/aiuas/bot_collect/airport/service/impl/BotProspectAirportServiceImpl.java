package com.chinobot.aiuas.bot_collect.airport.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.chinobot.aiuas.bot_collect.airport.entity.BotProspectAirport;
import com.chinobot.aiuas.bot_collect.airport.entity.BotProspectAirportUav;
import com.chinobot.aiuas.bot_collect.airport.entity.dto.AirportGeographyDTO;
import com.chinobot.aiuas.bot_collect.airport.entity.dto.UavAirportDTO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.AirportVO;
import com.chinobot.aiuas.bot_collect.airport.entity.vo.UavOfAirportVO;
import com.chinobot.aiuas.bot_collect.airport.mapper.BotProspectAirportMapper;
import com.chinobot.aiuas.bot_collect.airport.service.IBotProspectAirportService;
import com.chinobot.aiuas.bot_collect.airport.service.IBotProspectAirportUavService;
import com.chinobot.aiuas.bot_collect.base.entity.Geography;
import com.chinobot.aiuas.bot_collect.base.mapper.GeographyMapper;
import com.chinobot.aiuas.bot_collect.base.service.IGeographyService;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.aiuas.bot_collect.info.entity.dto.GeographyDTO;
import com.chinobot.aiuas.bot_prospect.flight.entity.Flight;
import com.chinobot.aiuas.bot_prospect.flight.entity.FlightWork;
import com.chinobot.aiuas.bot_prospect.flight.mapper.FlightMapper;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightService;
import com.chinobot.aiuas.bot_prospect.flight.service.IFlightWorkService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 机场 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2020-01-15
 */
@Service
public class BotProspectAirportServiceImpl extends BaseService<BotProspectAirportMapper, BotProspectAirport> implements IBotProspectAirportService {

	@Autowired
	private IBotProspectAirportService botProspectAirportService;
	@Autowired
	private IBotProspectAirportUavService botProspectAirportUavService;
	@Autowired
	private GeographyMapper geographyMapper;
	@Autowired
	private IGeographyService geographyService;
	@Autowired
	private BotProspectAirportMapper airportMapper;
	@Autowired
	private IFlightService flightService;
	@Autowired
	private IFlightWorkService flightWorkService;
	
	/**
	 * 地理信息业务类型
	 */
	private static final String BUSI_TYPE = "bot_prospect_airport";
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void deleteAirport(String airportId) {
		
		//删除机场
		botProspectAirportService.update(new LambdaUpdateWrapper<BotProspectAirport>().eq(BotProspectAirport::getUuid, airportId).set(BotProspectAirport::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//删除机场下的无人机
		botProspectAirportUavService.update(new LambdaUpdateWrapper<BotProspectAirportUav>().eq(BotProspectAirportUav::getAirportUuid, airportId).set(BotProspectAirportUav::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//获取机场下的所有航班
		List<Flight> list = flightService.list(new LambdaQueryWrapper<Flight>().eq(Flight::getAirportUuid, airportId));
		//删除机场下的航班
		flightService.update(new LambdaUpdateWrapper<Flight>().eq(Flight::getAirportUuid, airportId).set(Flight::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//删除机场下所有航班的所有作业
		for (Flight flight : list) {
			flightWorkService.update(new LambdaUpdateWrapper<FlightWork>().eq(FlightWork::getFlightUuid, flight.getUuid()).set(FlightWork::getIsDeleted, PartConstant.DATA_YES_DELETE));
		}
	
	}

	@Override
	public void addAirport(AirportGeographyDTO dto) {
		
		BotProspectAirport airport = new BotProspectAirport();
		BeanUtils.copyProperties(dto.getAirportDTO(), airport);
		//新增/编辑机场
		botProspectAirportService.saveOrUpdate(airport);
		
		//新增/编辑地理信息
		GeographyDTO geographyDto = dto.getGeography();
		Geography geography = packGeography(geographyDto,airport.getUuid());
		geographyService.saveOrUpdate(geography);
	}

	/**
	 * 地理信息封装
	 * @param geographyDto
	 * @return
	 */
	private Geography packGeography(GeographyDTO geographyDto,String busiId) {
		Geography geography = new Geography();
		if(StringUtils.isNotEmpty(geographyDto.getGeographyId())) {
			geography.setUuid(geographyDto.getGeographyId());
		}else {
			//获取该业务类型最大的序号
			Long maxSort = geographyMapper.getMaxSort(BUSI_TYPE);
			Integer sort = (int) (maxSort+1);
			geography.setSort(sort);
		}
		geography.setBusiId(busiId);
		geography.setBusiType(BUSI_TYPE);
		geography.setGeoType("polygon");
		geography.setLnglats(geographyDto.getLnglats());
		geography.setLat(geographyDto.getLat());
		geography.setLng(geography.getLat());
		return geography;
	}

	@Override
	public List<AirportVO> selectAirport() {
		
		return airportMapper.selectAirport();
	}

	@Override
	public AirportGeographyDTO getAirport(String airportId) {
		
		return airportMapper.getAirport(airportId);
	}

	@Override
	public IPage<UavOfAirportVO> getUavList(Page page, Map<String, Object> param) {
		
		return airportMapper.getUavList(page,param);
	}

	@Override
	public void addUavAirport(UavAirportDTO dto) {
		
		List<BotProspectAirportUav> list =  new ArrayList<BotProspectAirportUav>();
		String airportId = dto.getAirportId();
		List<String> uavList = dto.getUavList();
		for (String uavId : uavList) {
			BotProspectAirportUav proUav = new BotProspectAirportUav();
			proUav.setAirportUuid(airportId);
			proUav.setUavUuid(uavId);
			list.add(proUav);
		}
		//批量新增
		botProspectAirportUavService.saveBatch(list);
	}

	@Override
	public IPage<UavOfAirportVO> getUavOfAirportList(Page page, Map<String, Object> param) {
		
		IPage<UavOfAirportVO> uavOfAirportList = airportMapper.getUavOfAirportList(page,param);
		return airportMapper.getUavOfAirportList(page,param);
	}
	
	
}
