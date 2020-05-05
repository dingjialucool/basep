package com.chinobot.plep.home.point.entity.vo;



import java.util.List;

import com.chinobot.common.file.entity.FileBus;
import com.chinobot.plep.home.point.entity.FixedFlyPoint;
import com.chinobot.plep.home.point.entity.FixedPoint;
import com.chinobot.plep.home.point.entity.ScenePoint;

import lombok.Data;

@Data
public class VoPoint {

	private FixedPoint point;
	private List<FixedFlyPoint> flypoints;
	private List<ScenePoint> scenes;
	// 文件业务关联
	private List<FileBus> fileBus;
}
