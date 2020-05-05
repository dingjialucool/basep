package com.chinobot.plep.home.dataset.service.impl;

import com.chinobot.plep.home.dataset.entity.DataDto;
import com.chinobot.plep.home.dataset.entity.DataSet;
import com.chinobot.plep.home.dataset.entity.Metadata;
import com.chinobot.plep.home.dataset.mapper.DataSetMapper;
import com.chinobot.plep.home.dataset.service.IDataSetService;
import com.chinobot.plep.home.dataset.service.IMetadataService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.task.entity.vo.DomainSceneVo;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 数据集 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
@Service
public class DataSetServiceImpl extends BaseService<DataSetMapper, DataSet> implements IDataSetService {

	@Autowired
	private DataSetMapper dataSetMapper;
	@Autowired
	private IDataSetService dataSetService;
	@Autowired
	private IMetadataService metadataService;
	
	@Override
	public IPage<Map> getDataSetList(Page page, Map<String, Object> param) {
		if(param.get("sceneId")!=null && param.get("sceneId")!="" ) {
        	param = toParam(param,"sceneId");
        }
        if(param.get("domainId")!=null && param.get("domainId")!="" ) {
        	param = toParam(param,"domainId");
        }
        if(param.get("taskId")!=null && param.get("taskId")!="" ) {
        	param = toParam(param,"taskId");
        }
		return dataSetMapper.getDataSetList(page,param);
	}
	
	private Map toParam(Map param, String type) {
		
    	String str = (String) param.get(type);
    	if(str.indexOf(',')>0) {
    		String[] split = str.split(",");
    		param.put(type, split);
    	}else {
			String[] split = new String[1];
			split[0] = str;
			param.put(type, split);
		}
		return param;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean saveData(DataDto dataDto) {
		
		DataSet dataSet = dataDto.getDataSet();
		boolean bo = false;
		//如果数据集uuid不为空(修改) 
		if(CommonUtils.isNotEmpty(dataSet.getUuid())) {
			//保存时需要删除元数据，新增元数据
			UpdateWrapper<Metadata> updateWrapper = new UpdateWrapper<Metadata>();
			updateWrapper.eq("set_id", dataSet.getUuid());
			updateWrapper.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
			bo = metadataService.update(updateWrapper);
			
		}
		
		//保存数据集
		bo = dataSetService.saveOrUpdate(dataSet);
		
		//没有(新增)直接新增元数据
		List<Metadata> metadatas = dataDto.getMetadatas();
		for (int i = 0; i < metadatas.size(); i++) {
			metadatas.get(i).setSetId(dataSet.getUuid());
			metadatas.get(i).setSort(i+1);
		}
		
		bo = metadataService.saveBatch(metadatas);
		
		return bo;
	}

	@Override
	public List<Map> getMetadata(String uuid) {
		
		return dataSetMapper.getMetadata(uuid);
	}

	@Override
	public List<String> getParamValue() {
		
		return dataSetMapper.getParamValue();
	}

	@Override
	public List<Map> getMetadatas(String uuid) {
		
		return dataSetMapper.getMetadatas(uuid);
	}

	@Override
	public List<DomainSceneVo> treeDomainSceneTask() {
		return dataSetMapper.treeDomainSceneTask();
	}

}
