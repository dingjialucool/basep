package com.chinobot.cityle.ddjk.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.EquipmentOnlineInfo;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangw
 * @since 2019-03-16
 */
public interface IEquipmentOnlineInfoService extends IBaseService<EquipmentOnlineInfo> {
    /**
     * 获取任务列表
     * @param param
     * @return
     */
    public IPage<Map> getTaskList(Page page, Map param);

    /**
     * 获取无人机调度列表
     * @param param
     * @return
     */
    public IPage<Map> getUavList(Page page, Map param);
    /**
     * 获取任务调度的无人机列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getUavListForTask(Page page, Map param);

    /**
     * 获取已分配的无人机Id集合
     * @param param
     * @return
     */
    public List<String> getUavIdtList(Map param);

    /**
     * 监控页面获取场景列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getSceneListForMonitor(Page page,Map param);
    /**
     * 监控页面获取设备列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getUavListForMonitor(Page page,Map param);
    /**
     * 监控页面获取任务数量
     * @param param
     * @return
     */
    public Map getTaskCountForMonitor(Map param);

    /**
     * 获取路径
     * @param param
     * @return
     */
    public List<String[]> getRoad(Map param);

    /**
     * 新增任务调度
     * @param xcTime
     * @param sid
     * @param uids
     */
    void addTask(LocalDate xcTime, String sid, String[] uids);

    /**
     * 获取场景的任务列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getTaskListForScene(Page page,Map param);

    public Map formatXcData(List<Map> source);
    
    /**
     * 获取设备任务完成比例
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月15日
     * @company chinobot
     */
    List<Map> getUavTaskFinishPercent(Map param);

    /**
     * 获取楼栋边界及楼层用于绘制3d
     * @return
     */
    public Map getSceneShape();
}
