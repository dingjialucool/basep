package com.chinobot.plep.home.point.entity.vo;

import java.util.List;

import com.chinobot.plep.home.point.entity.RoutePoint;
import com.chinobot.plep.home.route.entity.CheckPoint;
import com.chinobot.plep.home.route.entity.Route;

import lombok.Data;

@Data
public class VoRoute {

	private CheckPoint checkPoint;
	
	private Route route;
	
	private List<RoutePoint> line;
}
