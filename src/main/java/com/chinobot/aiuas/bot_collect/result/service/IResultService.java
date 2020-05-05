package com.chinobot.aiuas.bot_collect.result.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.aiuas.bot_collect.result.entity.Result;
import com.chinobot.framework.web.service.IBaseService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作业采集结果表 服务类
 * </p>
 *
 * @author shizt
 * @since 2020-03-17
 */
public interface IResultService extends IBaseService<Result> {

    /**
     * 进度检测总况 - 工程列表
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    List<Map> getProgressCollectOptions();

    /**
     * 进度检测总况 - 工程列表
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    Map getProgressCollectData(Page page, String[] collectIds);

    /**
     * 进度检测总况 - 飞行作业情况
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    IPage<Map> getFlightWork(Page page, String[] collectIds);

    /**
     * 进度检测总况 - 工程列表
     * @Author: shizt
     * @Date: 2020/3/16 17:21
     */
    Map getProgressCollectInfo(String collectId);
}
