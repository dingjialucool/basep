package com.chinobot.plep.home.plan.mapper;

import com.chinobot.plep.home.plan.entity.RangeDept;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 范围部门关系表 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-06-16
 */
public interface RangeDeptMapper extends IBaseMapper<RangeDept> {

	List<Map> getRangeGrid(@Param("p")Map paramDept);

}
