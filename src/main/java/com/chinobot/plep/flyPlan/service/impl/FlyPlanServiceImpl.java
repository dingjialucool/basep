package com.chinobot.plep.flyPlan.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.service.IAddressBaseService;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.utils.ThreadLocalUtil;
import com.chinobot.framework.web.service.impl.BaseService;
import com.chinobot.plep.flyPlan.entity.FlyPlan;
import com.chinobot.plep.flyPlan.entity.PlanBuilding;
import com.chinobot.plep.flyPlan.entity.PlanRange;
import com.chinobot.plep.flyPlan.mapper.FlyPlanMapper;
import com.chinobot.plep.flyPlan.service.IFlyPlanService;
import com.chinobot.plep.flyPlan.service.IPlanBuildingService;
import com.chinobot.plep.flyPlan.service.IPlanRangeService;
import com.chinobot.plep.home.building.entity.Building;
import com.chinobot.plep.home.building.service.IBuildingService;
import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.FlyTask;
import com.chinobot.plep.home.plan.entity.Range;
import com.chinobot.plep.home.plan.service.IFlyAreaService;
import com.chinobot.plep.home.plan.service.IFlyTaskService;
import com.chinobot.plep.home.plan.service.IRangeService;

/**
 * <p>
 * 飞行计划表 服务实现类
 * </p>
 *
 * @author djl
 * @since 2019-06-18
 */
@Service
public class FlyPlanServiceImpl extends BaseService<FlyPlanMapper, FlyPlan> implements IFlyPlanService {

	@Autowired
	private FlyPlanMapper flyPlanMapper;
	@Autowired
	private IRangeService rangeService;
	@Autowired
	private IBuildingService buildingService;
	@Autowired
	private IPersonService personService;
	@Autowired
	private IFlyPlanService flyPlanService;
	@Autowired
	private IPlanBuildingService planBuildingService;
	@Autowired
	private IPlanRangeService planRangeService;
	@Autowired
	private IFlyTaskService flyTaskService;
	@Autowired
	private IFlyAreaService flyAreaService;
	@Autowired
	private IAddressBaseService addressBaseService;
	@Autowired
	private IRoleService roleService;
	
	@Override
	public IPage getFlyPlanList(Page page, Map<String, Object> param) {
		if(param.get("planType")!=null && param.get("planType")!="" ) {
        	String str = (String) param.get("planType");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("planType", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("planType", split);
			}
        }
        if(param.get("playStatus")!=null && param.get("playStatus")!="" ) {
        	String str = (String) param.get("playStatus");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("playStatus", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("playStatus", split);
			}
        }
        if(param.get("strategy")!=null && param.get("strategy")!="" ) {
        	String str = (String) param.get("strategy");
        	if(str.indexOf(',')>0) {
        		String[] split = str.split(",");
        		param.put("strategy", split);
        	}else {
				String[] split = new String[1];
				split[0] = str;
				param.put("strategy", split);
			}
        }
        Person person = ThreadLocalUtil.getResources();
		if(person != null) {
			person = personService.getById(person.getUuid());
		}
		param.put("deptId",person.getDeptId());
		List<Map> roles = roleService.getRoleByPersonId(person.getUuid());
		for(Map map : roles) {
			if(GlobalConstant.SUPER_ADMIN_ID.equals(map.get("roleId"))) {
				param.remove("deptId");//超级管理员查看所有
			}
		}
    	
		
		return flyPlanMapper.getFlyPlanList(page,param);
	}

	@Override
	public List<Range> getRangeData(Map<String, Object> param) {
		Person person = ThreadLocalUtil.getResources();
		if(person != null) {
			person = personService.getById(person.getUuid());
		}
		param.put("deptId",person.getDeptId());
		List<Map> roles = roleService.getRoleByPersonId(person.getUuid());
		for(Map map : roles) {
			if(GlobalConstant.SUPER_ADMIN_ID.equals(map.get("roleId"))) {
				param.remove("deptId");//超级管理员查看所有
			}
		}
		return flyPlanMapper.getRangeData(param);
	}

	@Override
	public List<Building> getBuildData(Map<String, Object> param) {
		Person person = ThreadLocalUtil.getResources();
		if(person != null) {
			person = personService.getById(person.getUuid());
		}
		param.put("deptId",person.getDeptId());
		List<Map> roles = roleService.getRoleByPersonId(person.getUuid());
		for(Map map : roles) {
			if(GlobalConstant.SUPER_ADMIN_ID.equals(map.get("roleId"))) {
				param.remove("deptId");//超级管理员查看所有
			}
		}
	
		return flyPlanMapper.getBuildingData(param);
	}

	@Override
	public boolean saveOrUpdates(Map<String, Object> param) {
		if(param==null) {
			return false;
		}
		boolean bo = true;
		LocalDate beginDateTime = null;
		LocalDate endDateTime = null;
		Float gapCycle = null;
		String uuid = (String) param.get("uuid");
		String personId = (String) param.get("createBy");
		String start = (String) param.get("time_start");
		if(StringUtils.isNotEmpty(start)) {
			 beginDateTime = LocalDate.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}else {
			 beginDateTime = LocalDate.parse("2000-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String end = (String) param.get("time_end");
		if(StringUtils.isNotEmpty(end)) {
			endDateTime = LocalDate.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}else {
			endDateTime = LocalDate.parse("2099-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		String specialType = (String) param.get("special_type");
		String cycle = String.valueOf(param.get("cycle"));
		if(cycle!=null && cycle!="" && cycle!="null") {
			gapCycle = Float.parseFloat(cycle) ;
		}
		String  gapType = (String) param.get("cycle_unit");
		String reformId = (String) param.get("reform_id");
		String planName = (String) param.get("name");
		String planType = (String) param.get("plan_type");
		String strategy = (String) param.get("strategy");
		String planRangId = (String) param.get("planRangId");
		String oth = (String) param.get("oth");
		ArrayList<LinkedHashMap> rangeList = (ArrayList) param.get("range");
		
		//根据创建人ID获取部门
		Person person = personService.getById(personId);
		String deptId = person.getDeptId();
		//生成飞行计划
		FlyPlan flyPlan = new FlyPlan();
		flyPlan.setName(planName);
		flyPlan.setPlanType(planType);
		flyPlan.setSpecialType(specialType);
		flyPlan.setStrategy(strategy);
		flyPlan.setCreateBy(personId);
		flyPlan.setCycle(gapCycle);
		flyPlan.setCycleUnit(gapType);
		flyPlan.setDeptId(deptId);
		flyPlan.setTimeStart(beginDateTime);
		flyPlan.setTimeEnd(endDateTime);
		flyPlan.setReformId(reformId);
		if(uuid!=null && uuid!="") {
			flyPlan.setUuid(uuid);
		}
		flyPlanService.saveOrUpdate(flyPlan);
		//生成飞行计划-房屋关系表
		if (rangeList!=null && (strategy.equals("3") || strategy.equals("4"))) {
			for (LinkedHashMap  building : rangeList) {
				PlanBuilding planBuilding = new PlanBuilding();
				if(planRangId!=null && planRangId!="") {
					planBuilding.setUuid(planRangId);
				}
				//若修改后的策略类型与以前不一致，则删除以前的关系
				if(StringUtils.isNotEmpty(oth)&&(oth.equals("1")||oth.equals("2"))) {
					planRangeService.removeById(planRangId);
				}
				planBuilding.setBuildingId((String)building.get("uuid"));
				planBuilding.setPlanId(flyPlan.getUuid());
				planBuildingService.saveOrUpdate(planBuilding);
				// 生成计划时若有计划类型为专项类型的则直接生成生成专项任务(范围专项任务)
//				if(planType!=null && planType!="" && planType.equals("2")) {
//					SpecialBuildTask(flyPlan.getUuid(), planBuilding.getUuid());
//				}
			}
		}
		//生成飞行计划-范围关系表
		if (rangeList!=null && (strategy.equals("1") || strategy.equals("2"))) {
			for (LinkedHashMap range : rangeList) {
				PlanRange planRange = new PlanRange();
				if(planRangId!=null && planRangId!="") {
					planRange.setUuid(planRangId);
				}
				//若修改后的策略类型与以前不一致，则删除以前的关系
				if(StringUtils.isNotEmpty(oth) && (oth.equals("3")||oth.equals("4"))) {
					planBuildingService.removeById(planRangId);
				}
				planRange.setRangeId((String)range.get("uuid"));
				planRange.setPlanId(flyPlan.getUuid());
				bo = planRangeService.saveOrUpdate(planRange);
				// 生成计划时若有计划类型为专项类型的则直接生成生成专项任务(范围专项任务)
//				if(planType!=null && planType!="" && planType.equals("2")) {
//					SpecialRangTask(flyPlan.getUuid(), planRange.getUuid());
//				}
			}
		}
		
		return bo;
	}

	//生成计划时若有计划类型为专项类型的则直接生成生成专项任务(范围专项任务)
	private void SpecialRangTask(String planId,String rangId) {
		FlyTask flyTask = new FlyTask();
		flyTask.setPlanId(planId);
		flyTask.setBuildingId(rangId);
		flyTask.setCreateTime(LocalDateTime.now());
		flyTask.setOperateTime(LocalDateTime.now());
		flyTaskService.save(flyTask);
		
	}
	//生成计划时若有计划类型为专项类型的则直接生成生成专项任务(范围专项任务)
	private void SpecialBuildTask(String planId,String buildId) {
		FlyTask flyTask = new FlyTask();
		flyTask.setPlanId(planId);
		flyTask.setBuildingId(buildId);
		flyTask.setCreateTime(LocalDateTime.now());
		flyTask.setOperateTime(LocalDateTime.now());
		flyTaskService.save(flyTask);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean delFlyPlan(Map<String, Object> param) {
		
		String strategy = (String) param.get("strategy");
		String planRangId = (String) param.get("planRangId");
		String rangId = (String) param.get("rangId");
		String delType = (String) param.get("delType");
		String uuid = (String) param.get("uuid");
		boolean bo = true;
		if(delType.equals("a1")) {//如果类型为a1，则删除计划 不删除计划下的任务
			bo = delPlan(planRangId,strategy);
		}else {//否则删除计划   且删除计划下的任务
			bo = delPlan(planRangId,strategy);//删除计划
			//删除计划下的任务
			if(strategy.equals("1") || strategy.equals("2")) {
				//查询该计划下 的所有区域
				QueryWrapper<FlyArea> queryWrapper = new QueryWrapper();
				queryWrapper.eq("rang_id", rangId);
				queryWrapper.eq("data_status",GlobalConstant.DATA_STATUS_VALID);
				List<FlyArea> list = flyAreaService.list(queryWrapper);
				//删除所有区域相对应的任务
				for (FlyArea flyArea : list) {
					UpdateWrapper update = new UpdateWrapper();
					update.eq("plan_id", uuid);
					update.eq("point_id",flyArea.getUuid());
					update.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
				    bo = flyTaskService.update(update);
				}
				
			}
			if(strategy.equals("3") || strategy.equals("4")) {
				UpdateWrapper update = new UpdateWrapper();
				update.eq("plan_id", uuid);
				update.eq("building_id",rangId);
				update.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
				bo = flyTaskService.update(update);
			}
			
		}
		return bo;
	}

	private boolean delPlan(String planRangId,String strategy) {
		boolean bo = true;
		UpdateWrapper update = new UpdateWrapper();
		update.eq("uuid", planRangId);
		update.set("data_status", GlobalConstant.DATA_STATUS_INVALID);
		if(strategy.equals("1") || strategy.equals("2")) {
			bo = planRangeService.update(update);
		}
		if(strategy.equals("3") || strategy.equals("4")) {
			bo = planBuildingService.update(update);
		}
		return bo;
	}
	
	
	@Override
	public List<Map> getArea(String uuid) {
		return flyPlanMapper.getArea(uuid);
	}

	@Override
	public List<Map> getBuild(String uuid) {
		return flyPlanMapper.getBuild(uuid);
	}

	@Override
	public boolean changeUseStatus(Map<String, Object> param) {
		String uuid = (String) param.get("uuid");
		String useStatus = (String) param.get("useStatus");
		UpdateWrapper<FlyPlan> updateWrapper = new UpdateWrapper<FlyPlan>();
		updateWrapper.eq("uuid", uuid);
		updateWrapper.set("use_status", useStatus);
		if(useStatus.equals("0")) {
			updateWrapper.set("status", "9");
		}else {
			updateWrapper.set("status", "1");
		}
		return flyPlanService.update(updateWrapper);
	}

	@Override
	public List getCenter(Map<String, Object> param) {
		ArrayList<LinkedHashMap> list = (ArrayList) param.get("object");
		List<String> centerList = new ArrayList<String>();
		String strategy = (String) param.get("strategy");
		String type = (String) param.get("type");
		if(type.equals("add")) {//新增时
			if(strategy.equals("1")||strategy.equals("2")) {
				for (LinkedHashMap map : list) {
					//范围地址库id
					String abId = (String) map.get("abId");
					centerList.add(getCenterByAbId(abId));
				}
			}
			if(strategy.equals("3")||strategy.equals("4")) {
				for (LinkedHashMap map : list) {
					//建筑地址库id
					String abId = (String) map.get("absId");
					centerList.add(getCenterByAbId(abId));
				}
			}
		}
		if(type.equals("edit")) {//修改时
			if(strategy.equals("1")||strategy.equals("2")) {
				for (LinkedHashMap map : list) {
					//范围id
					String uuid = (String) map.get("uuid");
					//根据范围id查询范围地址库id
					QueryWrapper<Range> queryWrapper = new QueryWrapper<Range>();
					queryWrapper.eq("uuid", uuid);
					Range range = rangeService.getOne(queryWrapper);
					//获取地址库中的中心坐标点
					centerList.add(getCenterByAbId(range.getAbId()));
				}
			}
			if(strategy.equals("3")||strategy.equals("4")) {
				for (LinkedHashMap map : list) {
					//建筑id
					String uuid = (String) map.get("uuid");
					//根据建筑id查询建筑地址库id
					QueryWrapper<Building> queryWrapper = new QueryWrapper<Building>();
					queryWrapper.eq("uuid", uuid);
					Building building = buildingService.getOne(queryWrapper);
					//获取地址库中的中心坐标点
					//建筑地址库id
					centerList.add(getCenterByAbId(building.getAbsId()));
				}
			}
		}
		
		
		return centerList;
	}

	private String getCenterByAbId(String abId) {
		QueryWrapper<AddressBase> queryWrapper = new QueryWrapper<AddressBase>();
		queryWrapper.eq("uuid", abId).eq("data_status", GlobalConstant.DATA_STATUS_VALID);
		AddressBase addressBase = addressBaseService.getOne(queryWrapper);
		return addressBase.getCenter();
	}
}
