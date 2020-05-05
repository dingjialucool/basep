package com.chinobot.common.domain;

import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import lombok.Data;

/**
 * 分页请求
 * @author shizt
 * @date 2019年3月18日
 */
@Data
public class RequestPage {

	private Page page;
	
	private Map<String, String> param;
}
