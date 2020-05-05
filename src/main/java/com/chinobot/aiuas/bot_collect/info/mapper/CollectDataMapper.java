package com.chinobot.aiuas.bot_collect.info.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.info.entity.CollectData;
import com.chinobot.aiuas.bot_collect.info.entity.dto.InfoCheckDataListDTO;
import com.chinobot.aiuas.bot_collect.info.entity.vo.HistoryVedioVo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 采查数据 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2020-03-11
 */
public interface CollectDataMapper extends IBaseMapper<CollectData> {
	/**
	 * 根据对象id获取对象最近的采查视频
	 * @param param
	 * @return
	 */
	IPage<HistoryVedioVo> searchInfoCheckVedioFile (Page page,@Param("p") Map<String,Object> param);
	
	/**
	 *查询采查对象的采查图片数量以及采查视频数量
	 * @param paraMap
	 * @return
	 */
	List<Map> searchInfoCheckData(@Param(value = "p")Map<String, Object> paraMap);
	
	/**
	 *查询采查对象的采查图片以及采查视频
	 * @param paraMap
	 * @return
	 */
	List<InfoCheckDataListDTO> searchInfoCheckDataDTO(@Param(value = "p")Map<String, Object> paraMap);

}
