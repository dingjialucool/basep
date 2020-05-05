package com.chinobot.cityle.base.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Parts;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 * 部件 服务类
 * </p>
 *
 * @author shizt
 * @since 2019-04-04
 */
public interface IPartsService extends IBaseService<Parts> {
	
	/**
	 * 部件 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	IPage<Map> getPartsPage(Page page, Map<String, Object> param);
	
	/**
	 * 根据id获取部件
	 * @param uuid
	 * @return
	 * @author shizt  
	 * @date 2019年4月3日
	 * @company chinobot
	 */
	Map getPartsById(String uuid);
	
	/**
	 * 部件 列表
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月4日
	 * @company chinobot
	 */
	List<Map> getPartsList(Map<String, String> param);
	
	/**
	 * 部件 编辑
	 * @param voFile
	 * @return
	 * @author shizt  
	 * @date 2019年4月9日
	 * @company chinobot
	 */
	Result editUav(VoAddressBase<Parts> voAddressBase);
}
