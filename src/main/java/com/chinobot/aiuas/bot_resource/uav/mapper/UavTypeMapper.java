package com.chinobot.aiuas.bot_resource.uav.mapper;

import com.chinobot.aiuas.bot_resource.uav.entity.UavType;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeListVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机型 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
public interface UavTypeMapper extends IBaseMapper<UavType> {

    /**
     * 机型 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeListVo>
     * @Author: shizt
     * @Date: 2020/1/15 17:10
     */
    List<UavTypeListVo> getUavTypeList(@Param("p") Map<String, Object> param);

    /**
     * 机型 详细
     * @Param: [param]
     * @Return: com.chinobot.aiuas.bot_resource.uav.entity.UavType
     * @Author: shizt
     * @Date: 2020/1/16 9:49
     */
    UavTypeInfoVo getUavType(@Param("p") Map<String, Object> param);
}
