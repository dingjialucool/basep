package com.chinobot.cityle.ddjk.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chinobot.cityle.ddjk.entity.TaskXc;
import com.chinobot.framework.web.mapper.IBaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author huangw
 * @since 2019-04-02
 */
public interface TaskXcMapper extends IBaseMapper<TaskXc> {

    /**
     *日志页面获取场景列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getSceneForJournal(Page page, @Param("param") Map param);

    /**
     * 日志获取任务列表
     * @param page
     * @param param
     * @return
     */
    public IPage<Map> getTaskForJournal(Page page, @Param("param") Map param);
    
    
    /**
     * 巡查总览
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月16日
     * @company chinobot
     */
    Map getPatrolCount(@Param("p")Map<String, String> param);
    
    /**
     * 出勤(设备、人)
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月16日
     * @company chinobot
     */
    List<Map> getAttendanceCount(@Param("p")Map<String, String> param);
    
    /**
     * 获取任务业务类型数量
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月17日
     * @company chinobot
     */
    List<Map> getBusTypeCount(@Param("p")Map<String, String> param);
    
    /**
     * 任务 列表
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月19日
     * @company chinobot
     */
    List<Map> getTaskXcList(@Param("p")Map<String, Object> param);
    

    /**
     * 获取当前无人机的任务列表
     * @param param
     * @return
     * @author shizt  
     * @date 2019年4月19日
     * @company chinobot
     */
    public IPage<Map> getTaskXcListByEquipment(Page page, @Param("param")Map<String, Object> param);
    
    
    /**
     * 任务总数/完成数/预警数
     * @return
     * @author shizt  
     * @date 2019年5月22日
     * @company chinobot
     */
    Map getTaskMonitorCount(@Param("p")Map<String, Object> param);
    
    /**
     * 任务预警列表
     * @param param
     * @return
     * @author shizt  
     * @date 2019年5月22日
     * @company chinobot
     */
    List<LinkedHashMap> getTaskMonitorList(@Param("p")Map<String, Object> param);
    
    /**
     * 任务预警分页
     * @param param
     * @return
     * @author shizt  
     * @date 2019年5月22日
     * @company chinobot
     */
    IPage<LinkedHashMap> getTaskMonitorList(Page page, @Param("p")Map<String, Object> param);
}
