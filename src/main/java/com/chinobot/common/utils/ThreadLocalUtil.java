package com.chinobot.common.utils;

import java.util.List;
import java.util.Map;

import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.Person;
import com.chinobot.common.constant.GlobalConstant;

public class ThreadLocalUtil {
	private static Person defaultPerson = new Person();

	private static final InheritableThreadLocal<Person> RESOURCES = new InheritableThreadLocal<Person>();

	public static Person getResources() {
		return RESOURCES != null ? RESOURCES.get() : null;
	}

	public static void setResources(Person newResources) {
		if (CommonUtils.isObjEmpty(newResources)) {
			return;
		}
		RESOURCES.remove();
		RESOURCES.set(newResources);
	}

	public static void remove() {
		RESOURCES.remove();
	}

	public static Person getDefaultResources() {
		defaultPerson.setUuid("0c7c30c6a7f562954e06738d70eb501d");
		Dept dept = new Dept();
		dept.setAreaCode("7");
		dept.setDeptCode("8ff207f2698311e9881d0242ac110005");
		defaultPerson.setDept(dept);
		return defaultPerson;
	}
	
	/**
	 * 判断当前用户有没有管理员权限
	 * @return
	 */
	public static boolean isSystemRole() {
		List<Map> roleList = RESOURCES.get().getRoleList();
		if(roleList == null || roleList.size() <= 0) {
			return false;
		}
		for(Map map : roleList) {
			String roleId = (String) map.get("roleId");
			if(roleId.equals("01")) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断当前用户是否属于深圳市
	 * @return
	 */
	public static boolean isShenz() {
		Dept dept = RESOURCES.get().getDept();
		if(CommonUtils.isObjEmpty(dept)) {
			return false;
		}
		if(dept.getUuid().equals(GlobalConstant.SUPER_DEPT_ID) && dept.getDataStatus().equals("1")) {
			return true;
		}
		return false;
	}
}
