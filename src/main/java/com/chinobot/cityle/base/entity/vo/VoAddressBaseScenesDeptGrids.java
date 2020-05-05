package com.chinobot.cityle.base.entity.vo;

import java.util.List;

import com.chinobot.cityle.base.entity.DeptGrid;
import com.chinobot.cityle.base.entity.Scene;

import lombok.Data;

@Data
public class VoAddressBaseScenesDeptGrids<T> {

	private VoAddressBase<T> voAddressBase;
	private List<Scene> scenes;
	private List<DeptGrid> deptGrids;
}
