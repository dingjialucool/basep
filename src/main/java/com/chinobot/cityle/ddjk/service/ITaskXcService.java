package com.chinobot.cityle.ddjk.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.TaskXc;
import com.chinobot.framework.web.service.IBaseService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author huangw
 * @since 2019-04-02
 */
public interface ITaskXcService extends IBaseService<TaskXc> {

    /**
     *日志页面获取场景列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getSceneForJournal(Page page, Map<String, Object> param);

    /**
     *日志页面获取任务列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getTaskForJournal(Page page, Map<String, Object> param);
    
    
    /**
     * 巡查总览
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月16日
     * @company chinobot
     */
    Map getPatrolCount(Map<String, String> param);
    
    /**
     * 出勤(设备、人)
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月16日
     * @company chinobot
     */
    List<Map> getAttendanceCount(Map<String, String> param);
    
    /**
     * 获取任务业务类型数量
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月17日
     * @company chinobot
     */
    List<Map> getBusTypeCount(Map<String, String> param);
    
    /**
     * 任务 列表
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月19日
     * @company chinobot
     */
    List<Map> getTaskXcList(Map<String, Object> param);
    
    /**
     * 任务总数/完成数/预警数
     * @return
     * @author shizt  
     * @date 2019年5月22日
     * @company chinobot
     */
    Map getTaskMonitorCount(Map<String, Object> param);
    
    /**
     * 任务预警列表
     * @param param
     * @return
     * @author shizt  
     * @date 2019年5月22日
     * @company chinobot
     */
    List<LinkedHashMap> getTaskMonitorList(Map<String, Object> param);
    
    /**
     * 任务预警分页
     * @param param
     * @return
     * @author shizt  
     * @date 2019年5月22日
     * @company chinobot
     */
    IPage<LinkedHashMap> getTaskMonitorPage(Page page, Map<String, Object> param);
}
