package com.chinobot.cityle.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Uav;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 设备 服务类
 * </p>
 *
 * @author huangw
 * @since 2019-03-27
 */
public interface IUavService extends IBaseService<Uav> {

	/**
	 * 设备 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	IPage<Map> getUavPage(Page page, Map<String, Object> param);
	
	/**
	 * 根据id获取设备
	 * @param uuid
	 * @return
	 * @author shizt  
	 * @date 2019年4月3日
	 * @company chinobot
	 */
	Map getUavById(String uuid);
	
	/**
	 * 设备 编辑
	 * @param voFile
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	Result editUav(VoAddressBase<Uav> voAddressBase);

	IPage<Map> getUavList(Page page, Map<String, Object> param);

	List<Map> getUavList(Map<String, Object> param);

	IPage<Map> getUavTask(Page page, Map<String, Object> param);

	IPage<Map> getHistoryUavList(Page page, Map<String, Object> param);

	List<Map> getHistoryGui(Map<String, Object> param);

	IPage<Map> getAllUav(Page page,Map<String, Object> param);

	boolean isTheSame(String ecode);
}
