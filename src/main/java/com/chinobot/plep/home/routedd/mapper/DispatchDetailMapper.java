package com.chinobot.plep.home.routedd.mapper;

import com.chinobot.plep.home.routedd.entity.DispatchDetail;
import org.apache.ibatis.annotations.Param;
import java.util.Map;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 调度明细 Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
public interface DispatchDetailMapper extends IBaseMapper<DispatchDetail> {

	Map getDispatchUav(@Param("p") Map<String, Object> p);
}
