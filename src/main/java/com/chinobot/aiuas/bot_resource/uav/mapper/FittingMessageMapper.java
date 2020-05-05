package com.chinobot.aiuas.bot_resource.uav.mapper;

import com.chinobot.aiuas.bot_resource.uav.entity.FittingMessage;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.FittingVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 配件信息表 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
public interface FittingMessageMapper extends IBaseMapper<FittingMessage> {

    /**
     * 配件信息 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_resource.uav.entity.vo.FittingVo>
     * @Author: shizt
     * @Date: 2020/1/17 9:58
     */
    List<FittingVo> getFittingList(@Param("p") Map<String, Object> param);
}
