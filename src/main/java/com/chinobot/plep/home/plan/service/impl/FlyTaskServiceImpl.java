package com.chinobot.plep.home.plan.service.impl;

import com.chinobot.plep.home.plan.entity.FlyTask;
import com.chinobot.plep.home.plan.mapper.FlyTaskMapper;
import com.chinobot.plep.home.plan.service.IFlyTaskService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.framework.web.service.impl.BaseService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 飞行任务表 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-06-19
 */
@Service
public class FlyTaskServiceImpl extends BaseService<FlyTaskMapper, FlyTask> implements IFlyTaskService {
	@Autowired
	private FlyTaskMapper flyTaskMapper;
	@Autowired
	private IFileBusService fileBusService;
	
	@Override
	public IPage<Map> getTaskPage(Page page, Map<String, String> param) {
		
		return flyTaskMapper.getTaskPage(page, param);
	}


	@Override
	public Map getTaskByTime(Map<String, Object> param) {
		return flyTaskMapper.getTaskByTime(param);
	}


	@Override
	public void doUpload(VoAddressBase<FlyTask> vo,String moduleName) {
		List<FileBus> fileBus = vo.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", vo.getEntity().getUuid());
		queryWrapper.eq("module", moduleName);
		// 清空文件关联
		fileBusService.remove(queryWrapper);
		if(0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i)
					.setBusId(vo.getEntity().getUuid())
					.setSort(i + 1).setModule(moduleName);
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
	}

	@Override
	public IPage<Map> getUavList(Page page, Map<String, String> param) {
		return flyTaskMapper.getUavList(page, param);
	}


	@Override
	public Map getTaskById(String uuid) {
		Map param = new HashMap();
		param.put("uuid",uuid);
		return flyTaskMapper.getTaskPage(param);
	}


}
