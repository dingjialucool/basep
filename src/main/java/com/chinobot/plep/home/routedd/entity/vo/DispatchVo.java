package com.chinobot.plep.home.routedd.entity.vo;



import java.util.List;

import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import com.chinobot.plep.home.routedd.entity.DispatchDetailType;
import com.chinobot.plep.home.routedd.entity.UavDispatch;

import lombok.Data;

@Data
public class DispatchVo {

	private UavDispatch uavDispatch;
	
	private DispatchDetail dispatchDetail;
	
	private List<DispatchDetailType> type;
}
