package com.chinobot.aiuas.bot_collect.info.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.aiuas.bot_collect.info.entity.Info;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectAndGeoWithStringDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.CollectObjectDTO;
import com.chinobot.aiuas.bot_collect.info.entity.dto.InfoTreeDto;
import com.chinobot.aiuas.bot_collect.info.entity.vo.CollectPolygon;
import com.chinobot.aiuas.bot_collect.info.entity.vo.GovAreaVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 采查对象 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
public interface InfoMapper extends IBaseMapper<Info> {
	
	/**
	 * 获取采查对象树
	 * @param param
	 * @return
	 */
	List<CollectObjectDTO> getTreeObject(@Param(value = "p") Map<String, Object> param);

	/**
	 * 采查对象经纬度集合(多边形)
	 * @param id
	 * @return
	 */
	List<CollectPolygon> checkedCollectObject(@Param(value = "p")Map param);

	/**
	 * 回显采查对象
	 * @param infoId
	 * @return
	 */
	CollectObjectAndGeoWithStringDTO getCollectObject(String infoId);

	/**
	 * 获取行政code
	 * @return
	 */
	List<GovAreaVo> getCode();

	/**
	 * 获取标签
	 * @return
	 */
	List<Map<String, String>> getTags();

	/**
	 * 根据策略主键获取对象领域列表
	 * @Author: shizt
	 * @Date: 2020/2/21 17:47
	 */
	List<InfoTreeDto> getInfoDomainList(@Param("strategyId")String strategyId);
	
	/**
	 * 根据策略主键,任务主键获取对象ID
	 * @Author: zhoull
	 * @Date: 
	 */
	List<Info> getInfosByTaskStragy(@Param("p") Map<String,Object> param);
	
	/**
	 * 根据任务主键获取对象ID
	 * @Author: zhoull
	 * @Date: 
	 */
	List<Info> getInfosByInfoList(@Param("p") Map<String, Object> paramMap);


}
