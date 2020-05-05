package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.base.entity.Dept;
import com.chinobot.cityle.base.entity.vo.VoAddressBase;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 部门 Mapper 接口
 * </p>
 *
 * @author laihb
 * @since 2019-03-16
 */
public interface DeptMapper extends IBaseMapper<Dept> {
	
	/**
	 * 根据id获取部门/场景
	 * @param id
	 * @return
	 * @author shizt  
	 * @date 2019年3月25日
	 * @company chinobot
	 */
	VoAddressBase<Map> getDeptAddr(@Param("p") Map<String, String> param);

	/**
	 * 部门 分页
	 * @param page
	 * @param param
	 * @return
	 * @author shizt  
	 * @date 2019年4月1日
	 * @company chinobot
	 */
	IPage<VoAddressBase<Map>> getDeptAddr(Page page, @Param("p") Map<String, String> param);

	/**
	 * 获取同一机构和责任人类型下不唯一的部门
	 * @Author: shizt
	 * @Date: 2020/3/4 15:15
	 */
	List<String> getDeptNotUnique(@Param("unitPersonType")String unitPersonType, @Param("organization")String organization);
}
