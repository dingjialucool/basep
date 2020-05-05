package com.chinobot.plep.home.setting.entity;




import java.util.List;

import com.chinobot.cityle.base.entity.Role;

import lombok.Data;

@Data
public class RoleMenuVo {
	
	private Role role;
	
	private List<MenuRole> roleMenu;
}
