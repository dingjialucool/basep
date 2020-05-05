package com.chinobot.framework.web.constant;

/**
 * Created by huangw on 2019/4/15.
 */
public interface KafkaConstant {
    /**
     * 轨迹数据管道
     */
    String PIPE_TRAIL = "robot_trail";
    /**
     * 飞行数据管道
     */
    String PIPE_FLIGHT = "robot_flight";
    /**
     * 任务预警管道
     */
    String PIPE_WARNING = "task_warning";
    /**
     * 机器状态管道
     */
    String PIPE_ROBOT_STATUS = "robot_status";
    /**
     * 任务状态管道
     */
    String PIPE_TASK_STATUS = "task_status";
    /**
     * 任务采集管道
     */
    String PIPE_GATHER_DATA = "gather_data";

    /**
     * 历史飞行数据
     */
    String PIPE_HISTORY_DATA = "history_data";

    /**
     * 飞行文件数据
     */
    String PIPE_TASK_FILE = "task_file";

    /**
     * 存量变换预警
     */
    String PIPE_PUSH_WARNING_STOCK = "push_warning_stock";

    /**
     * 楼顶加建预警
     */
    String PIPE_PUSH_WARNING_ADDITION = "push_warning_addition";
    
    /**
     * 人员行动轨迹
     */
    String PIPE_PERSON_TRAJECTORY = "p_person_trajectory";
    
    /**
     * 人员离线
     */
    String PIPE_PERSON_ONLINE = "p_person_online";
    
    /**
     * 预警
     */
    String PIPE_PUSH_WARNING = "early_warning";
    
    /**
     * 作业任务应答
     */
    String TASK_CONFIRM = "task_confirm";
    
    /**
     * 数量监测推送
     */
    String NUMBER_POST = "number_post";
    
    /**
     * 结果类型：字符串
     */
    String STRING_RS_TYPE = "string";
    
    /**
     * 结果类型：整型
     */
    String INT_RS_TYPE = "int";
    
    /**
     * 结果类型：浮点
     */
    String FLOAT_RS_TYPE = "float";
    
    /**
     * 结果类型：双精度
     */
    String DOUBLE_RS_TYPE = "double";
    
    /**
     * 结果类型：日期
     */
    String DATE_RS_TYPE = "date";
    
    /**
     * 结果类型：时间戳
     */
    String TIME_RS_TYPE = "time";
    
}
