package com.chinobot.aiuas.bot_prospect.obstacle.service;

import java.util.List;

import com.chinobot.aiuas.bot_prospect.obstacle.entity.ProspectObstacleType;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleLocationDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleTypeDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndObstacleTypeVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 障碍物类型 服务类
 * </p>
 *
 * @author djl
 * @since 2020-03-19
 */
public interface IProspectObstacleTypeService extends IBaseService<ProspectObstacleType> {

	/**
	 * 障碍物类型新增/修改
	 * @param dto
	 */
	void addObstacleType(ObstacleTypeDTO dto);

	/**
	 * 删除障碍物类型
	 * @param typeId
	 */
	void removeObstacleType(String typeId);

	/**
	 * 回显障碍物类型
	 * @param typeId
	 * @return
	 */
	ObstacleTypeDTO getObstacleType(String typeId);

	/**
	 * 获取所有障碍物类型
	 * @return
	 */
	List<ObstacleTypeDTO> getAllObstacleType();

	/**
	 * 获取障碍物呃类型下的所有障碍物
	 * @return
	 */
	List<ObstacleAndObstacleTypeVo> getAllObstacleByType(ObstacleLocationDTO dto);

}
