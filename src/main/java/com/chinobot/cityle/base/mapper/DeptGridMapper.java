package com.chinobot.cityle.base.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.cityle.base.entity.DeptGrid;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 部门网格关联 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-04-02
 */
public interface DeptGridMapper extends IBaseMapper<DeptGrid> {

	/**
	 *  获取已关联网格部门
	 * @param paramDept
	 * @return
	 */
	List<Map> getDeptGrid( @Param("p")Map paramDept);

}
