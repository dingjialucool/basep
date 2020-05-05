package com.chinobot.aiuas.bot_prospect.obstacle.service;

import java.util.List;

import com.chinobot.aiuas.bot_prospect.obstacle.entity.Obstacle;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleAndGeographyDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndTypeVo;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacletPolygon;
import com.chinobot.framework.web.service.IBaseService;


/**
 * <p>
 * 障碍物表 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-02-24
 */
public interface IObstacleService extends IBaseService<Obstacle> {

	/**
	 * 新增修改障碍物
	 * @param dto
	 */
	void addObstacle(ObstacleAndGeographyDTO dto);

	/**
	 * 删除障碍物
	 * @param obstacleId
	 */
	void removeObstacle(String obstacleId);

	/**
	 * 获取障碍物树形结构
	 * @param name
	 * @return
	 */
	List<ObstacleAndTypeVo> getTreeObstacle(String name);

	/**
	 * 回显障碍物
	 * @param obstacleId
	 * @return
	 */
	ObstacleAndGeographyDTO getObstacle(String obstacleId);

	/**
	 * 复选框选中的障碍物
	 * @param idList
	 * @return
	 */
	List<ObstacletPolygon> checkedObstacletPolygon(List<String> idList);


}
