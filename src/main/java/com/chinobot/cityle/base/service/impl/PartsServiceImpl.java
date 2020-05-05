package com.chinobot.cityle.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Parts;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.mapper.PartsMapper;
import com.chinobot.cityle.base.service.IPartsService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 部件 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-04-04
 */
@Service
public class PartsServiceImpl extends BaseService<PartsMapper, Parts> implements IPartsService {

	@Autowired
	private PartsMapper partsMapper;
	@Autowired
	private IFileBusService fileBusService;
	
	@Override
	public IPage<Map> getPartsPage(Page page, Map<String, Object> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("deptId", ThreadLocalUtil.getResources().getDept().getUuid());
		if(param.get("ptype")!=null && param.get("ptype")!="" ) {
        	String str = (String) param.get("ptype");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("ptype", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("ptype", split);
			}
        }
	 if(param.get("isMount")!=null && param.get("isMount")!="" ) {
        	String str = (String) param.get("isMount");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("isMount", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("isMount", split);
			}
        }
		return partsMapper.getParts(page, param);
	}

	@Override
	public Map getPartsById(String uuid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("uuid", uuid);
		List<Map> parts = partsMapper.getParts(param);
		
		if(0 <= parts.size()) {
			return parts.get(0);
		}
		return null;
	}

	@Override
	public List<Map> getPartsList(Map<String, String> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		
		return partsMapper.getParts(param);
	}

	@Override
	public Result editUav(VoAddressBase<Parts> voAddressBase) {
		// 保存部件
		Parts parts = voAddressBase.getEntity();
		parts.setDeptId(ThreadLocalUtil.getResources().getDept().getUuid());
		this.saveOrUpdate(parts);
		
		// 设备文件关联
		List<FileBus> fileBus = voAddressBase.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", parts.getUuid());
		// 清空文件关联
		fileBusService.remove(queryWrapper);
		if(0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i)
					.setBusId(parts.getUuid())
					.setSort(i + 1)
					.setModule("parts_album");
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
		
		return ResultFactory.success();
	}
}
