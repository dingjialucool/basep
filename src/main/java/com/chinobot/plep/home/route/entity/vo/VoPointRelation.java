package com.chinobot.plep.home.route.entity.vo;

import java.util.List;

import com.chinobot.plep.home.plan.entity.OutputParamter;
import com.chinobot.plep.home.route.entity.CheckPoint;

import lombok.Data;

@Data
public class VoPointRelation {
	
	private CheckPoint point;//起飞点
	
	private List<String> relations; //关联点集合
	
	private List<OutputParamter>  outParams;//输出参数集合
	
}
