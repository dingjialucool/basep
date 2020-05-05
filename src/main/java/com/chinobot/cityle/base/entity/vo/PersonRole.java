package com.chinobot.cityle.base.entity.vo;

import java.util.List;

import com.chinobot.cityle.base.entity.Person;
import com.chinobot.cityle.base.entity.UserRole;
import com.chinobot.common.file.entity.FileBus;

import lombok.Data;

@Data
public class PersonRole {
	
	private Person person;
	private List<UserRole> roles;
	private FileBus fileBus;
	private String passwordOld;
}
