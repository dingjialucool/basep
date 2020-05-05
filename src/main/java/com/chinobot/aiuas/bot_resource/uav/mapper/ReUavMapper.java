package com.chinobot.aiuas.bot_resource.uav.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_resource.uav.entity.Uav;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavInfoVo;
import com.chinobot.aiuas.bot_resource.uav.entity.vo.UavListVo;
import com.chinobot.framework.web.mapper.IBaseMapper;
import com.chinobot.plep.home.homepage.entity.vo.HomePageUavVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 无人机 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
public interface ReUavMapper extends IBaseMapper<Uav> {

    /**
     * 无人机 列表
     * @Param: [param]
     * @Return: java.util.List<com.chinobot.aiuas.bot_resource.uav.entity.vo.UavListVo>
     * @Author: shizt
     * @Date: 2020/1/16 10:35
     */
    IPage<UavListVo> getUavList(Page page, @Param("p") Map<String, Object> param);

    /**
     * 无人机 详细
     * @Param: [param]
     * @Return: com.chinobot.aiuas.bot_resource.uav.entity.vo.UavInfoVo
     * @Author: shizt
     * @Date: 2020/1/16 11:32
     */
    UavInfoVo getUavInfo(@Param("p") Map<String, Object> param);

    /**
     * 无人机列表，对应cle_uav字段
     * @Author: shizt
     * @Date: 2020/3/11 9:50
     */
    List<Map> getReUavList(@Param("p") Map<String, Object> param);

    /**
     * 无人机列表，对应cle_uav字段
     * @Author: shizt
     * @Date: 2020/3/11 9:50
     */
    IPage<HomePageUavVo> getReUavPage(Page page, @Param("p") Map<String, Object> param);

    /**
     * 无人机列表，对应cle_uav字段
     * @Author: shizt
     * @Date: 2020/3/11 9:50
     */
    List<HomePageUavVo> getReUavPage(@Param("p") Map<String, Object> param);
}
