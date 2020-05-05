package com.chinobot.cityle.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Uav;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.mapper.UavMapper;
import com.chinobot.cityle.base.service.IUavService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 设备 服务实现类
 * </p>
 *
 * @author huangw
 * @since 2019-03-27
 */
@Service
public class UavServiceImpl extends BaseService<UavMapper, Uav> implements IUavService {

	@Autowired
	private UavMapper uavMapper;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IUavService uavService;
	
	@Override
	public IPage<Map> getUavPage(Page page, Map<String, Object> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		if(param.get("etype")!=null && param.get("etype")!="" ) {
        	String str = (String) param.get("etype");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("etype", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("etype", split);
			}
        }
		if(param.get("runStatus")!=null && param.get("runStatus")!="" ) {
        	String str = (String) param.get("runStatus");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("runStatus", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("runStatus", split);
			}
        }
		return uavMapper.getUav(page, param);
	}

	@Override
	public Map getUavById(String uuid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("uuid", uuid);
		
		return uavMapper.getUav(param).get(0);
	}

	
	
	
	@Override
	public Result editUav(VoAddressBase<Uav> voAddressBase) {
		
		// 保存设备
		Uav uav = voAddressBase.getEntity();
		if(uav.getUuid()==null || uav.getUuid() == "") {
			boolean bo = isTheSame(uav.getEcode());
			if(bo) {//如果设备编号已存在，则保存失败
				Map resuMap = new HashMap();
				resuMap.put("result", "1");
				return ResultFactory.success(resuMap);
			}
		}else {
			Uav uav2 = uavService.getById(uav.getUuid());
			if(!uav2.getEcode().equals(uav.getEcode())) {
				boolean bo = isTheSame(uav.getEcode());
				if(bo) {//如果设备编号已存在，则保存失败
					Map resuMap = new HashMap();
					resuMap.put("result", "1");
					return ResultFactory.success(resuMap);
				}
			}
		}
		uav.setDeptId(ThreadLocalUtil.getResources().getDept().getUuid());
		this.saveOrUpdate(uav);
		
		// 设备文件关联(图片)
		List<FileBus> fileBus = voAddressBase.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", uav.getUuid());
		// 清空文件关联(图片、视频)
		fileBusService.remove(queryWrapper);
		if(0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i)
					.setBusId(uav.getUuid())
					.setSort(i + 1)
					.setModule(fileBus.get(i).getModule());
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
		
		return ResultFactory.success();
	}

	@Override
	public IPage<Map> getUavList(Page page, Map<String, Object> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		return uavMapper.getUavList(page, param);
	}

	@Override
	public List<Map> getUavList(Map<String, Object> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		return uavMapper.getUavList(param);
	}

	@Override
	public IPage<Map> getUavTask(Page page, Map<String, Object> param) {
		// TODO Auto-generated method stub
		return uavMapper.getUavTask(page, param);
	}

	@Override
	public IPage<Map> getHistoryUavList(Page page, Map<String, Object> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		return uavMapper.getHistoryUavList(page, param);
	}

	@Override
	public List<Map> getHistoryGui(Map<String, Object> param) {
		// TODO Auto-generated method stub
		 return uavMapper.getHistoryGui(param);
	}

	@Override
	public IPage<Map> getAllUav(Page page,Map<String, Object> param) {
		
		return uavMapper.getAllUav(page,param);
	}

	@Override
	public boolean isTheSame(String ecode) {
		QueryWrapper<Uav> queryWrapper = new QueryWrapper<Uav>();
		queryWrapper.eq("ecode", ecode).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		List<Uav> list = uavService.list(queryWrapper);
		if(list.size()>0) {
			return true;
		}
		return false;
	}

}
