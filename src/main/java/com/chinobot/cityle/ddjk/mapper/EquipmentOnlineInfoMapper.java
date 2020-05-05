package com.chinobot.cityle.ddjk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.EquipmentOnlineInfo;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-03-16
 */
public interface EquipmentOnlineInfoMapper extends IBaseMapper<EquipmentOnlineInfo> {
    /**
     * 任务列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getTaskList(Page page, @Param("param") Map param);

    /**
     * 获取可调度人员的无人机列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getUavList(Page page, @Param("param") Map param);

    public void insertUavDispatch();
    /**
     * 获取任务调度的无人机列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getUavListForTask(Page page, @Param("param") Map param);

    /**
     * 获取已分配的无人机Id集合
     * @param param
     * @return
     */
    public List<String> getUavIdtList(@Param("param") Map param);

    /**
     * 监控页面获取场景列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getSceneListForMonitor(Page page,@Param("param") Map param);
    /**
     * 监控页面获取设备列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getUavListForMonitor(Page page,@Param("param") Map param);

    /**
     * 获取设备上挂载的任务列表
     * @param param
     * @return
     */
    public List<Map> getTaskListForEquipment(@Param("param") Map param);

    /**
     * 获取监控页面场景预警信息
     * @param param
     * @return
     */
    public List<Map> getWarningListForMonitor(@Param("param") Map param);

    /**
     * 查询巡查时间为今日的特定任务状态的任务数量
     * @param param
     * @return
     */
    public Integer getTaskCountByForMonitor(@Param("param") Map param);

    /**
     * 获取任务的执法人员
     * @param taskId
     * @return
     */
    public List<Map> getTaskUser(@Param("task_id") String taskId);

    /**
     * 获取设备的人员
     * @param e_id
     * @return
     */
    public List<Map> getEquipmentPerson(@Param("e_id") String e_id);

    /**
     * 获取设备路径
     * @param param
     * @return
     */
    public List<String> getUavRoad(@Param("param") Map param);

    /**
     * 获取任务路径
     * @param param
     * @return
     */
    public List<String> getTaskRoad(@Param("param") Map param);

    /**
     * 获取任务的场景巡查任务
     * @param uuid
     * @return
     */
    public List<Map> getTaskPoints(@Param("uuid")String uuid);
    
    
    /**
     * 获取设备任务完成比例
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月15日
     * @company chinobot
     */
    List<Map> getUavTaskFinishPercent(@Param("p")Map param);

    /**
     * 获取楼栋边界及楼层用于绘制3d
     * @return
     */
    List<Map> getSceneShape();
}
