package com.chinobot.common.file.service.impl;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.mapper.FileBusMapper;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 业务文件关联 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-04-08
 */
@Service
public class FileBusServiceImpl extends BaseService<FileBusMapper, FileBus> implements IFileBusService {
	
	@Autowired
	private FileBusMapper fileBusMapper;

	@Override
	public List<Map> getFileIdByBusId(Map param) {
		
		return fileBusMapper.getFileIdByBusId(param);
	}

	@Override
	public List<FileBus> getFileBusList(Map<String, Object> param) {
		QueryWrapper<FileBus> fileBusQueryWrapper = new QueryWrapper<>();
		fileBusQueryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		fileBusQueryWrapper.eq("bus_id", param.get("busId"));
		if(!CommonUtils.isObjEmpty(param.get("module"))){
			fileBusQueryWrapper.eq("module", param.get("module"));
		}

		return list(fileBusQueryWrapper);
	}

	@Override
	public boolean saveFileBusList(List<FileBus> fileBus, String busId, String module) {
		if (!CommonUtils.isObjEmpty(fileBus)) {
			QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
			queryWrapper.eq("bus_id", busId)
					.eq("module", module);
			remove(queryWrapper);// 清空文件关联
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i)
						.setBusId(busId)
						.setSort(i + 1)
						.setModule(module);
			}
			if(0 < fileBus.size()){
				return saveOrUpdateBatch(fileBus);
			}
		}
		return false;
	}

	@Override
	public boolean delFileBus(String busId, String module) {
		UpdateWrapper<FileBus> fileBusUpdateWrapper = new UpdateWrapper<>();
		fileBusUpdateWrapper.eq("bus_id", busId);
		fileBusUpdateWrapper.eq("module", module);

		FileBus fileBus = new FileBus();
		fileBus.setDataStatus(GlobalConstant.DATA_STATUS_INVALID);

		return update(fileBus, fileBusUpdateWrapper);
	}

}
