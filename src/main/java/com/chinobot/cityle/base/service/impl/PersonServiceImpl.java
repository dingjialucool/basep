package com.chinobot.cityle.base.service.impl;

import java.util.List;
import java.util.Map;

import com.chinobot.cityle.base.entity.vo.PersonOrganizationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.UserRole;
import com.chinobot.cityle.base.entity.vo.PersonRole;
import com.chinobot.cityle.base.mapper.PersonMapper;
import com.chinobot.cityle.base.service.IPersonService;
import com.chinobot.cityle.base.service.IUserRoleService;
import com.chinobot.common.constant.GlobalConstant;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.common.file.service.IFileBusService;
import com.chinobot.common.utils.CommonUtils;
import com.chinobot.common.utils.MD5Util;
import com.chinobot.framework.web.service.impl.BaseService;

/**
 * <p>
 * 人员 服务实现类
 * </p>
 *
 * @author shizt
 * @since 2019-03-18
 */
@Service
public class PersonServiceImpl extends BaseService<PersonMapper, Person> implements IPersonService {
	@Autowired
	private PersonMapper personMapper;
	@Autowired
	private IUserRoleService userRoleService;
	@Autowired
	private IFileBusService fileBusService;
	
	@Override
	public IPage<Person> getPersonPage(Page page, Map<String, String> param) {
//        QueryWrapper<Person> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("data_status", GlobalConstant.DATA_STATUS_VALID);
//        if(CommonUtils.isNotEmpty(param.get("deptId"))) {
//        	queryWrapper.eq("dept_id", param.get("deptId"));
//        }
//        if(CommonUtils.isNotEmpty(param.get("pname"))) {
//        	queryWrapper.like("pname", param.get("pname"));
//        }
//        if(CommonUtils.isNotEmpty(param.get("pcode"))) {
//        	queryWrapper.like("pcode", param.get("pcode"));
//        }
//        if(CommonUtils.isNotEmpty(param.get("duties"))) {
//        	queryWrapper.like("duties", param.get("duties"));
//        }
//        if(CommonUtils.isNotEmpty(param.get("phone"))) {
//        	queryWrapper.like("phone", param.get("phone"));
//        }
//        
//		return personMapper.selectPage(page, queryWrapper);
		return personMapper.getPersonPage(page,param);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void saveOrUpdatePersonRole(PersonRole personRole) {
		Person person = personRole.getPerson();
		//是否存在人员
		boolean exists = true;
		if(CommonUtils.isObjEmpty(person.getUuid())) {
			exists = false;
			// 设置默认密码
			person.setPassword(MD5Util.getMD5(GlobalConstant.DEFAULT_PASSWORD));
		}
		//保存人员信息
		saveOrUpdate(person);
		
		List<UserRole> roles = personRole.getRoles();
		if(CommonUtils.objNotEmpty(roles)) {
			if(!exists) {
				String uuid = person.getUuid();
				//新增人员角色
				for (UserRole role : roles) {
					role.setPersonId(uuid);
				}
			}else {
				//删除人员所有角色
				QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
				queryWrapper.eq("person_id", person.getUuid());
				userRoleService.remove(queryWrapper);
			}
			//保存人员角色
			userRoleService.saveBatch(roles);
		}
		
		// 保存人员头像
		FileBus fileBus = personRole.getFileBus();
		if(CommonUtils.objNotEmpty(fileBus)) {
			fileBus.setBusId(person.getUuid())
			.setSort(1)
			.setModule("person_face");
			fileBusService.saveOrUpdate(personRole.getFileBus());
		}
	}

	@Override
	public Map getPersonByParam(Map param) {
		
		return personMapper.getPersonByParam(param);
	}

	@Override
	public boolean passWordJudge(PersonRole personRole) {
		Person person = personRole.getPerson();
		String passwordOld =personRole.getPasswordOld();
		//输入密码是否存在
		boolean passwordStatus = true;
		
		Person personVo = (Person) personMapper.selectById(person.getUuid());
		if(MD5Util.getMD5(passwordOld).equals(personVo.getPassword())) {
			passwordStatus = true;
		}else {
			passwordStatus = false;
		}
		
		if(passwordStatus) {
			if(!person.getPassword().equals(personVo.getPassword())) {
				person.setPassword(MD5Util.getMD5(person.getPassword()));
			}
			//保存人员信息
			saveOrUpdate(person);
		}
		return passwordStatus;
	
	}

	@Override
	public List<PersonOrganizationVo> getPersonByOrganization(String organization) {
		return personMapper.getPersonByOrganization(organization);
	}
}
