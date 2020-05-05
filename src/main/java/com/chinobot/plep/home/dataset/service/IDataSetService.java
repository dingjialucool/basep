package com.chinobot.plep.home.dataset.service;

import com.chinobot.plep.home.dataset.entity.DataDto;
import com.chinobot.plep.home.dataset.entity.DataSet;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 数据集 服务类
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
public interface IDataSetService extends IBaseService<DataSet> {

	IPage<Map> getDataSetList(Page page, Map<String, Object> param);

	boolean saveData(DataDto dataDto);

	List<Map> getMetadata(String uuid);

	List<String> getParamValue();

	List<Map> getMetadatas(String uuid);

	
	List<DomainSceneVo> treeDomainSceneTask();

}
