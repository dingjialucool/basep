package com.chinobot.aiuas.bot_prospect.obstacle.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chinobot.aiuas.bot_collect.info.constant.PartConstant;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.Obstacle;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.ProspectObstacleType;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleLocationDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleTypeDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndObstacleTypeVo;
import com.chinobot.aiuas.bot_prospect.obstacle.mapper.ObstacleMapper;
import com.chinobot.aiuas.bot_prospect.obstacle.mapper.ProspectObstacleTypeMapper;
import com.chinobot.aiuas.bot_prospect.obstacle.service.IObstacleService;
import com.chinobot.aiuas.bot_prospect.obstacle.service.IProspectObstacleTypeService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 障碍物类型 服务实现类
 * </p>
 *
 * @author djl
 * @since 2020-03-19
 */
@Service
public class ProspectObstacleTypeServiceImpl extends BaseService<ProspectObstacleTypeMapper, ProspectObstacleType> implements IProspectObstacleTypeService {

	@Autowired
	private IProspectObstacleTypeService  obstacleTypeService;
	
	@Autowired
	private IObstacleService  obstacleService;
	
	@Autowired
	private ObstacleMapper obstacleMapper;
	
	@Override
	public void addObstacleType(ObstacleTypeDTO dto) {
		
		//新增障碍物类型
		ProspectObstacleType type = new ProspectObstacleType();
		type.setUuid(dto.getUuid())
		.setOName(dto.getTypeName())
		.setIcon(dto.getIcon());
		
		obstacleTypeService.saveOrUpdate(type);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void removeObstacleType(String typeId) {
		
		//删除障碍物
		obstacleService.update(new LambdaUpdateWrapper<Obstacle>().eq(Obstacle::getObstacleType, typeId)
				.set(Obstacle::getIsDeleted, PartConstant.DATA_YES_DELETE));
		//删除障碍物类型
		obstacleTypeService.update(new LambdaUpdateWrapper<ProspectObstacleType>().eq(ProspectObstacleType::getUuid, typeId)
				.set(ProspectObstacleType::getIsDeleted, PartConstant.DATA_YES_DELETE));
	}

	@Override
	public ObstacleTypeDTO getObstacleType(String typeId) {
		
		return obstacleMapper.getObstacleType(typeId);
	}

	@Override
	public List<ObstacleTypeDTO> getAllObstacleType() {
		
		return obstacleMapper.getAllObstacleType();
	}

	@Override
	public List<ObstacleAndObstacleTypeVo> getAllObstacleByType(ObstacleLocationDTO dto) {
		
		return obstacleMapper.getAllObstacleByType(dto);
	}

}
