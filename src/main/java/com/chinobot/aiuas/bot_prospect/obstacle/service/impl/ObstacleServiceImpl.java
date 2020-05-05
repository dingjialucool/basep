package com.chinobot.aiuas.bot_prospect.obstacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chinobot.aiuas.bot_collect.base.entity.Geography;
import com.chinobot.aiuas.bot_collect.base.service.IGeographyService;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.aiuas.bot_collect.info.entity.dto.GeographyDTO;
import com.chinobot.aiuas.bot_collect.warning.mapper.WarningMapper;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.Obstacle;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleAndGeographyDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndTypeVo;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacletPolygon;
import com.chinobot.aiuas.bot_prospect.obstacle.mapper.ObstacleMapper;
import com.chinobot.aiuas.bot_prospect.obstacle.service.IObstacleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 障碍物表 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2020-02-24
 */
@Service
public class ObstacleServiceImpl extends BaseService<ObstacleMapper, Obstacle> implements IObstacleService {

    @Autowired
    private ObstacleMapper obstacleMapper;
    @Autowired
    private IObstacleService obstacleService;
    @Autowired
	private IGeographyService geographyService;
    @Autowired
	private IFileBusService fileBusService;
    @Autowired
    private WarningMapper warningMapper;

    /**
	 * 地理信息业务类型
	 */
	private static final String BUSI_TYPE = "bot_prospect_obstacle";
	/**
	 * 障碍物文件业务类型
	 */
	private static final String COLLECT_BUS_TYPE = "obstacle_img";
    
	@Override
	public void addObstacle(ObstacleAndGeographyDTO dto) {
		
		//障碍物新增更新
		ObstacleDTO obstacleDTO = dto.getObstacleDTO();
		Obstacle obstacle = new Obstacle();
		BeanUtils.copyProperties(obstacleDTO, obstacle);
		obstacleService.saveOrUpdate(obstacle);

		// 地理信息更新
		GeographyDTO geographyDto = dto.getGeography();
		Geography geography = packGeography(geographyDto, obstacle.getUuid());
		geographyService.saveOrUpdate(geography);


		// 如果是修改，先清空采查文件关联
		if (StringUtils.isNotEmpty(obstacleDTO.getUuid())) {
			List<FileBus> list = fileBusService
					.list(new LambdaQueryWrapper<FileBus>().eq(FileBus::getBusId, obstacleDTO.getUuid()));
			for (FileBus fileBus : list) {
				fileBus.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);
				fileBusService.updateById(fileBus);
			}
		}

		// 文件业务关联
		List<String> idList = dto.getIdList();
		if (idList != null && idList.size() > 0) {
			for (int i = 0; i < idList.size(); i++) {
				FileBus fileBus = new FileBus();
				fileBus.setBusId(obstacle.getUuid());
				fileBus.setFileId(idList.get(i));
				fileBus.setModule(COLLECT_BUS_TYPE);
				fileBus.setSort(i + 1);
				fileBusService.save(fileBus);
			}
		}
	}
	
	/**
	 * 地理信息封装
	 * 
	 * @param geographyDto
	 * @return
	 */
	private Geography packGeography(GeographyDTO geographyDto, String busiId) {
		Geography geography = new Geography();
		if (StringUtils.isNotEmpty(geographyDto.getGeographyId())) {
			geography.setUuid(geographyDto.getGeographyId());
		} 
		geography.setBusiId(busiId);
		geography.setBusiType(BUSI_TYPE);
		geography.setGeoType("polygon");
		geography.setLnglats(geographyDto.getLnglats());
		geography.setLat(geographyDto.getLat());
		geography.setLng(geographyDto.getLng());
		geography.setArea(geographyDto.getArea());
		return geography;
	}

	@Override
	public void removeObstacle(String obstacleId) {
		//删除障碍物
		obstacleService.update(new LambdaUpdateWrapper<Obstacle>().eq(Obstacle::getUuid, obstacleId)
				.set(Obstacle::getIsDeleted, PartConstant.DATA_YES_DELETE));
	}

	@Override
	public List<ObstacleAndTypeVo> getTreeObstacle(String name) {
		List<ObstacleAndTypeVo> treeObstacleList = obstacleMapper.getTreeObstacle(name);
		if(StringUtils.isEmpty(name)) {
			//顶级父类
			ObstacleAndTypeVo vo = new ObstacleAndTypeVo();
			vo.setId("0");
			vo.setTitle("障碍物");
			vo.setParentUuid("");
			vo.setType("3");
			treeObstacleList.add(vo);
		}
		return treeObstacleList;
	}

	@Override
	public ObstacleAndGeographyDTO getObstacle(String obstacleId) {
		
		//获取障碍物文件
		Map<String,String> param = new HashMap<>();
		param.put("busId", obstacleId);
		param.put("module",COLLECT_BUS_TYPE);
		List<String> idList = warningMapper.getWarnByBusId(param);
		ObstacleAndGeographyDTO vo = obstacleMapper.getObstacle(obstacleId);
		vo.setIdList(idList);
		
		return vo;
	}

	@Override
	public List<ObstacletPolygon> checkedObstacletPolygon(List<String> idList) {
		
		//障碍物id集合
		List<String> obstacleList = new ArrayList<>();
		if (idList.size() == 1 && "0".equals(idList.get(0))) {
			return obstacleMapper.checkedObstacletPolygon(obstacleList);
		}
		//障碍物类型id集合
		List<String> typeList = new ArrayList<>();
		for (String id : idList) {
			Obstacle obstacle = obstacleService.getById(id);
			if (obstacle == null) {
				typeList.add(id);
			}else {
				obstacleList.add(id);
			}
		}
		List<ObstacletPolygon> list = new ArrayList<ObstacletPolygon>();
		if (typeList.size() >0) {
			list.addAll(obstacleMapper.checkedObstacletOfTypePolygon(typeList));
		}
		if (obstacleList.size() >0) {
			list.addAll(obstacleMapper.checkedObstacletPolygon(obstacleList));
		}
		return list;
	}
	
}
