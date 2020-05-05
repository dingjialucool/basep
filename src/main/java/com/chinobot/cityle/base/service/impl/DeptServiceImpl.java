package com.chinobot.cityle.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.chinobot.aiuas.bot_resource.grid.mapper.ReGridMapper;
import com.chinobot.aiuas.bot_resource.grid.service.IReGridService;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.cityle.base.mapper.DeptMapper;
import com.chinobot.cityle.base.service.IAddressBaseService;
import com.chinobot.cityle.base.service.IDeptService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.domain.Result;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.ResultFactory;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 部门 服务实现类
 * </p>
 *
 * @author laihb
 * @since 2019-03-16
 */
@Service
public class DeptServiceImpl extends BaseService<DeptMapper, Dept> implements IDeptService {
	
	@Autowired
	private DeptMapper deptMapper;
	@Autowired
	private IDeptService deptService;
	@Autowired
	private IAddressBaseService addressBaseService;
	@Autowired
	private IFileBusService fileBusService;
	@Autowired
	private IPersonService iPersonService;
	@Autowired
	private ReGridMapper reGridMapper;
	
	@Override
	public IPage<VoAddressBase<Map>> getDeptAddrPage(Page page, Map<String, String> param) {
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		
		return deptMapper.getDeptAddr(page, param);
	}
	
	@Override
	public VoAddressBase<Map> getDeptAddrById(String uuid) {
		Map<String, String> param = new HashMap<String, String>();
		param.put("dataStatus", GlobalConstant.DATA_STATUS_VALID);
		param.put("uuid", uuid);
		
		return deptMapper.getDeptAddr(param);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Result editDept(VoAddressBase<Dept> voAddressBase) {
		// 地址库
		AddressBase addressBase = voAddressBase.getAddressBase();
		addressBase.setAtype("cle_dept");
		addressBaseService.saveOrUpdate(addressBase);
		// 部门
		Dept dept = voAddressBase.getEntity();
		dept.setAbId(addressBase.getUuid());
		deptService.saveOrUpdate(dept);
		
		// 获取父类的deptcode,并生成本部门的deptCode
		String parentId = dept.getParentId();
		if(!"0".equals(parentId)) {
			QueryWrapper<Dept> dWrapper = new QueryWrapper<Dept>();
			dWrapper.eq("uuid", parentId);
			Dept one = deptService.getOne(dWrapper);
			dept.setDeptCode(one.getDeptCode()+"-"+dept.getUuid());
			deptService.updateById(dept);
		}else {
			dept.setDeptCode(dept.getUuid());
			deptService.updateById(dept);
		}
		
		// 网格文件关联
		List<FileBus> fileBus = voAddressBase.getFileBus();
		QueryWrapper<FileBus> queryWrapper = new QueryWrapper<FileBus>();
		queryWrapper.eq("bus_id", dept.getUuid());
		// 清空文件关联
		fileBusService.remove(queryWrapper);
		if(0 < fileBus.size()) {
			for (int i = 0, size = fileBus.size(); i < size; i++) {
				fileBus.get(i)
					.setBusId(dept.getUuid())
					.setSort(i + 1)
					.setModule("dept_album");
			}
			// 保存关联
			fileBusService.saveOrUpdateBatch(fileBus);
		}
		
		return ResultFactory.success(dept.getUuid());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delDept(String deptId) {
		// 子部门
		List<Dept> childrenDepts = list(new LambdaQueryWrapper<Dept>()
				.eq(Dept::getParentId, deptId)
				.eq(Dept::getDataStatus, GlobalConstant.DATA_STATUS_VALID));
		for (Dept d: childrenDepts) {
			delDept(d.getUuid());
		}
		// 删除部门
		updateById(new Dept()
				.setUuid(deptId)
				.setDataStatus(GlobalConstant.DATA_STATUS_INVALID));
		// 删除人员
		iPersonService.update(new Person().setDataStatus(GlobalConstant.DATA_STATUS_INVALID),
				new LambdaUpdateWrapper<Person>()
					.eq(Person::getDeptId, deptId));
		// 删除网格
		reGridMapper.delGridByDeptId(deptId);
	}

	@Override
	public List<String> checkDeptUnique(String unitPersonType, String organization) {
		return deptMapper.getDeptNotUnique(unitPersonType, organization);
	}
}
