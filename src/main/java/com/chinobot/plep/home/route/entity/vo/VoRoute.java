package com.chinobot.plep.home.route.entity.vo;



import java.util.List;

import com.chinobot.plep.home.route.entity.CheckPoint;
import com.chinobot.plep.home.route.entity.Route;
import com.chinobot.plep.home.route.entity.RouteBuilding;

import lombok.Data;

@Data
public class VoRoute {

	private CheckPoint checkPoint;
	
	private Route route;
	
	private List<RouteBuilding> line;
	
}
