package com.chinobot.cityle.base.entity.vo;

import java.util.List;

import com.chinobot.cityle.base.entity.DeptGrid;

import lombok.Data;
@Data
public class VoAddressBaseBuildingDept<T> {
	
	private VoAddressBase<T> voAddressBase;
	private List<DeptGrid> deptGrids;
}
