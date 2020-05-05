package com.chinobot.plep.home.plan.entity.vo;

import java.util.List;

import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.plep.home.plan.entity.RangeDept;
import com.chinobot.plep.home.plan.entity.SceneRange;

import lombok.Data;

@Data
public class VoAddressBaseRangeGrids<T> {
	private VoAddressBase<T> voAddressBase;
	private List<RangeDept> deptGrids;
	private List<SceneRange> scenes;
}
