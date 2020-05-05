package com.chinobot.plep.home.point.entity.vo;



import java.util.List;

import com.chinobot.plep.home.point.entity.ScenePoint;

import lombok.Data;

@Data
public class VoPldr {

	private String assignId;
	private List<ScenePoint> scenes;
	//private String fileId;
	private List<String> fileIds;
}
