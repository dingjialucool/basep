package com.chinobot.plep.home.building.entity;
import java.util.List;


import com.chinobot.cityle.base.entity.AddressBase;
import com.chinobot.cityle.base.entity.DeptGrid;
import com.chinobot.common.file.entity.FileBus;
import lombok.Data;

@Data
public class VoDeptAddressBase<T> {
	
	//部门建筑关系
	private DeptGrid deptGrid ;
	
	private T entity;
	// 地址库
	private AddressBase addressBase;
	// 文件业务关联
	private List<FileBus> fileBus;
}
