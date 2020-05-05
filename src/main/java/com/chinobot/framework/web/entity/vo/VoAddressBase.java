package com.chinobot.framework.web.entity.vo;

import java.util.List;

import com.chinobot.common.file.entity.FileBus;
import com.chinobot.framework.web.entity.AddressBase;

import lombok.Data;

@Data
public class VoAddressBase<T> {

	private T entity;
	// 地址库
	private AddressBase addressBase;
	// 文件业务关联
	private List<FileBus> fileBus;
}
