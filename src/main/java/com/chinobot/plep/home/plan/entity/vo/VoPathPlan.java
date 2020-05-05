package com.chinobot.plep.home.plan.entity.vo;



import java.util.List;

import com.chinobot.plep.home.plan.entity.CameraConfig;
import com.chinobot.plep.home.plan.entity.FlyConfig;
import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.OutputParamter;

import lombok.Data;

@Data
public class VoPathPlan {
	
	private FlyConfig flyConfig;
	
	private CameraConfig cameraConfig;
	
	private List<OutputParamter> outputParamters;
	
	private List<FlyPath> paths;

}
