package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Uav;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 设备 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-03-27
 */
public interface UavMapper extends IBaseMapper<Uav> {

	/**
	 * 设备 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月2日
	 * @company chinobot
	 */
	IPage<Map> getUav(Page page, @Param("p") Map<String, Object> param);
	
	
	/**
	 * 根据id获取设备
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月3日
	 * @company chinobot
	 */
	List<Map> getUav(@Param("p") Map<String, String> param);


	IPage<Map> getUavList(Page page, @Param("p")Map<String, Object> param);

	List<Map> getUavList(@Param("p")Map<String, Object> param);

	IPage<Map> getUavTask(Page page,  @Param("p")Map<String, Object> param);


	IPage<Map> getHistoryUavList(Page page, @Param("p")Map<String, Object> param);


	List<Map> getHistoryGui(@Param("p")Map<String, Object> param);


	IPage<Map> getAllUav(Page page,@Param("p")Map<String, Object> param);
}
