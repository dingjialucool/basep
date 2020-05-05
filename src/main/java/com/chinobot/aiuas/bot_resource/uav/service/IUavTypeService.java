package com.chinobot.aiuas.bot_resource.uav.service;

import com.chinobot.aiuas.bot_collect.task.entity.vo.TaskInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.UavType;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeListVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 机型 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
public interface IUavTypeService extends IBaseService<UavType> {

    /**
     * 机型 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeListVo>
     * @Author: shizt
     * @Date: 2020/1/15 17:10
     */
    List<UavTypeListVo> getUavTypeList(Map<String, Object> param);

    /**
     * 机型 详细
     * @Param: [param]
     * @Return: com.chinobot.aiuas.bot_resource.uav.entity.vo.UavTypeInfoVo
     * @Author: shizt
     * @Date: 2020/1/15 17:55
     */
    UavTypeInfoVo getUavTypeInfo(Map<String, Object> param);

    /**
     * 机型 编辑
     * @Param: [uavTypeInfoVo]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/15 18:29
     */
    String editUavType(UavTypeInfoVo uavTypeInfoVo);

    /**
     * 机型 删除
     * @Param: [uuid]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/16 10:02
     */
    void delUavType(String uuid);
}
