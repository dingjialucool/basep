package com.chinobot.plep.home.meeting.entity.vo;


import java.util.List;
import java.util.Map;

import com.chinobot.cityle.base.entity.Person;

import lombok.Data;

/**
 * 
 * @ClassName: MeetingModelPerson   
 * @Description: 会议模板与参与人
 * @author: djl  
 * @date:2019年12月12日 上午10:31:49
 */

@Data
public class MeetingModelPerson {

	private List<Person> personsList;
	
	private Map<String,Object> maps;
}
