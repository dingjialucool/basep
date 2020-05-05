package com.chinobot.plep.home.plan.entity.vo;



import java.util.List;

import com.chinobot.plep.home.plan.entity.FlyArea;
import com.chinobot.plep.home.plan.entity.FlyPath;
import com.chinobot.plep.home.plan.entity.OutputParamter;

import lombok.Data;

@Data
public class VoAreaPathParm {

	private FlyArea flyArea;
	
	private List<FlyPath> flyPathList;
	
	private List<OutputParamter> outputParamterList;
}
