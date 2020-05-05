package com.chinobot.plep.home.dataset.service;

import java.util.List;
import java.util.Map;

import com.chinobot.plep.home.dataset.entity.vo.MetadataAndResultVo;

public interface IJdbcUtilService {

	Object test();

	/**
	 * 获取sql中的参数
	 * @param sql
	 * @return
	 */
	List<String> getSqlParam(String sql);

	
	/***
	 * 获取不带参数的sql元数据和预览数据集
	 * @param sql
	 * @return
	 */
	MetadataAndResultVo getMetadataAndResult(String sql);

	/**
	 * 获取带参数的sql元数据和预览数据集
	 * @param sql
	 * @param param
	 * @return
	 */
	MetadataAndResultVo getMetadataAndResult(String sql, Map<String, Object> param);

	/**
	 * 带参数的执行结果
	 * @param sql
	 * @param param
	 * @return
	 */
	List<Map<String, Object>> getResult(String sql, Map<String, Object> param);
}
