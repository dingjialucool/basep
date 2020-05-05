package com.chinobot.common.file.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chinobot.common.file.entity.FileBus;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 * 业务文件关联 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2019-04-08
 */
public interface FileBusMapper extends IBaseMapper<FileBus> {
	
    /**
     * 根据业务id获取文件id列表
     * @param busId
     * @return
     * @author shizt  
     * @date 2019年4月8日
     * @company chinobot
     */
    List<Map> getFileIdByBusId(@Param("p")Map param);
}
