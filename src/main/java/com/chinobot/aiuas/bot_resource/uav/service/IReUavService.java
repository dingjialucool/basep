package com.chinobot.aiuas.bot_resource.uav.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavListVo;
import com.chinobot.common.domain.Result;
import com.chinobot.framework.web.service.IBaseService;
import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 无人机 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
public interface IReUavService extends IBaseService<Uav> {


    /**
     * 无人机 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_resource.uav.entity.vo.UavListVo>
     * @Author: shizt
     * @Date: 2020/1/16 10:35
     */
    IPage<UavListVo> getUavList(Page page, Map<String, Object> param);

    /**
     * 无人机 详细
     * @Param: [param]
     * @Return: com.chinobot.aiuas.bot_resource.uav.entity.vo.UavInfoVo
     * @Author: shizt
     * @Date: 2020/1/16 11:32
     */
    UavInfoVo getUavInfo(Map<String, Object> param);

    /**
     * 无人机 编辑
     * @Param: [uavInfoVo]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/17 10:10
     */
    String editUav(UavInfoVo uavInfoVo);

    /**
     * 无人机 删除
     * @Param: [uavInfoVo]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/17 10:10
     */
    void delUav(String uuid);

    /**
     * 根据机型删除无人机
     * @Param: [uavTypeId]
     * @Return: com.chinobot.common.domain.Result
     * @Author: shizt
     * @Date: 2020/1/16 10:15
     */
    void delUavByUavTypeId(String uavTypeId);

    /**
     * 无人机列表，对应cle_uav字段
     * @Author: shizt
     * @Date: 2020/3/11 9:49
     */
    List<Map> getReUavList(Map<String, Object> param);

    /**
     * 无人机列表，对应cle_uav字段
     * @Author: shizt
     * @Date: 2020/3/11 9:49
     */
    IPage<HomePageUavVo> getReUavPage(Page page, Map<String, Object> param);

    List<HomePageUavVo> getReUavPage(Map<String, Object> param);
}
