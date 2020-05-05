package com.chinobot.aiuas.bot_collect.result.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.result.entity.Result;
import com.chinobot.framework.web.mapper.IBaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作业采集结果表 Mapper 接口
 * </p>
 *
 * @author shizt
 * @since 2020-03-17
 */
public interface ResultMapper extends IBaseMapper<Result> {

    /**
     * 进度检测总况 - 工程下拉框列表
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    List<Map> getProgressCollectOptions();

    /**
     * 进度检测总况 - 工程列表
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    IPage<Map> getProgressCollectList(Page page, @Param("collectIds") String[] collectIds);
    
    /**
     * 进度检测总况 - 统计
     * @Author: shizt
     * @Date: 2020/3/17 10:41
     */
    Map getStatistics(@Param("collectIds") String[] collectIds);

    /**
     * 进度检测总况 - 工程经纬度范围
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    List<Map> getProgressCollectLnglats(@Param("collectIds") String[] collectIds);
    
    /**
     * 数量监测总况 - 根据不同的任务，查询车辆次数，工程人数
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    List<Map> getResultNumberInfo(@Param("p") Map<String,Object> param);

    /**
     * 进度检测总况 - 饼图数据
     * @Author: shizt
     * @Date: 2020/3/17 20:09
     */
    List<Map> getProgressPieData(@Param("collectIds") String[] collectIds);

    /**
     * 进度检测总况 - 飞行作业情况
     * @Author: shizt
     * @Date: 2020/3/18 14:09
     */
    IPage<Map> getFlightWork(Page page, @Param("collectIds") String[] collectIds);

    /**
     *
     * @Author: shizt
     * @Date: 2020/3/18 15:15
     */
    List<Map> getProgressCollectInfo(@Param("collectId") String collectId);
}
