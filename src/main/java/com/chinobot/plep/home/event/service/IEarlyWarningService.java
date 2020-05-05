package com.chinobot.plep.home.event.service;

import com.chinobot.plep.home.event.entity.EarlyWarning;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.common.file.entity.FileBus;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 风险预警表 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-06-26
 */
public interface IEarlyWarningService extends IBaseService<EarlyWarning> {

	List<Map> uploadEarlyWarning(List<FileBus> fileBus) throws Exception;

	List<Double> printImageTags(InputStream inputStream) throws Exception;

	String getBindVillageId(Double lng, Double lat);

	String getBindDeptId(Double lng, Double lat) throws Exception;

	IPage<Map> getEvents(Page page, Map<String, Object> param);

	List<Map> getEvents2(Map<String, Object> param);

	Map getDispath(Map<String, Object> param);

}
