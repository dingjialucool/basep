package com.chinobot.plep.home.dataset.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.dataset.entity.DataSet;

/**
 * <p>
 * 数据集 Mapper 接口
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
public interface DataSetMapper extends IBaseMapper<DataSet> {

	IPage<Map> getDataSetList(Page page, @Param("p") Map<String, Object> param);

	List<Map> getMetadata(String uuid);

	List<String> getParamValue();

	List<Map> getMetadatas(String uuid);
	
	List<DomainSceneVo> treeDomainSceneTask();
}
