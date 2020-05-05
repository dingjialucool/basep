package com.chinobot.aiuas.bot_resource.uav.service;

import com.chinobot.aiuas.bot_resource.uav.entity.FittingMessage;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.FittingVo;
import com.chinobot.framework.web.service.IBaseService;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 配件信息表 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
public interface IFittingMessageService extends IBaseService<FittingMessage> {

    /**
     * 配件信息 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_resource.uav.entity.vo.FittingVo>
     * @Author: shizt
     * @Date: 2020/1/17 9:58
     */
    List<FittingVo> getFittingList(Map<String, Object> param);

    /**
     * 配件信息 编辑列表
     * @Param: [fittingVos, uavUuid]
     * @Return: void
     * @Author: shizt
     * @Date: 2020/1/17 10:21
     */
    void editFittingList(List<FittingVo> fittingVos, String uavUuid);

    /**
     * 根据无人机主键删除配件信息
     * @Param: [uavUuid]
     * @Return: void
     * @Author: shizt
     * @Date: 2020/1/17 10:23
     */
    void delFittingByUavUuid(String uavUuid);
}
