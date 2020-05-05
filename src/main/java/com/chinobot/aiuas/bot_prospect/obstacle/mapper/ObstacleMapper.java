package com.chinobot.aiuas.bot_prospect.obstacle.mapper;

import com.chinobot.aiuas.bot_prospect.obstacle.entity.Obstacle;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleAndGeographyDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleLocationDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.dto.ObstacleTypeDTO;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndObstacleTypeVo;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleAndTypeVo;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacleVo;
import com.chinobot.aiuas.bot_prospect.obstacle.entity.vo.ObstacletPolygon;
import com.chinobot.framework.web.mapper.IBaseMapper;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 障碍物表 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-02-24
 */
public interface ObstacleMapper extends IBaseMapper<Obstacle> {

    /**
     * 障碍物 列表
     * @Author: shizt
     * @Date: 2020/2/24 11:18
     */
    List<ObstacleVo> getList();

    /**
     * 获取树形障碍物
     * @param name
     * @return
     */
	List<ObstacleAndTypeVo> getTreeObstacle(@Param("name")String name);

	/**
	 * 回显障碍物类型
	 * @param typeId
	 * @return
	 */
	ObstacleTypeDTO getObstacleType(@Param("typeId")String typeId);

	/**
	 * 回显障碍物
	 * @param obstacleId
	 * @return
	 */
	ObstacleAndGeographyDTO getObstacle(@Param("uuid")String obstacleId);

	/**
	 * 复选框障碍物多边形
	 * @return
	 */
	List<ObstacletPolygon> checkedObstacletPolygon(@Param("obstacleList") List<String> obstacleList);

	/**
	 * 复选框障碍物多边形-根据障碍物类型
	 * @param typeList
	 * @return
	 */
	List<ObstacletPolygon> checkedObstacletOfTypePolygon(@Param("typeList") List<String> typeList);

	/**
	 * 获取所有的障碍物类型
	 * @return
	 */
	List<ObstacleTypeDTO> getAllObstacleType();

	/**
	 * 获取障碍物类型下的所有障碍物
	 * @param typeId
	 * @return
	 */
	List<ObstacleAndObstacleTypeVo> getAllObstacleByType(@Param("p") ObstacleLocationDTO dto);
}
