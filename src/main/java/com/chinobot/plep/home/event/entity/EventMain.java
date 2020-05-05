package com.chinobot.plep.home.event.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chinobot.common.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 事件主表
 * </p>
 *
 * @author huangw
 * @since 2019-06-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_event_main")
public class EventMain extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 编号
     */
    private String code;

    /**
     * 重要程度
     */
    @TableField(value="important")
    private String important;

    /**
     * 风险预警主键
     */
    private String warningId;

    /**
     * 来源:1-风险平台 2-手动录入
     */
    private String source;

    /**
     * 建筑主键
     */
    private String buildingId;

    /**
     * 小区主键
     */
    private String villageId;
    
    /**
     * 手动录入违章事实
     */
    private String truthWrite;

    /**
     * 预警处理意见
     */
    private String warningOpinion;

    /**
     * 当前环节：1-预警 2-巡查 3-整改
     */
    private String linkNow;

    /**
     * 巡查指定人
     */
    private String peronPatrol;

    /**
     * 巡查截至时间
     */
    private LocalDate timePatrol;

    /**
     * 整改指定人
     */
    private String personReform;

    /**
     * 上次流转时间 
     */
    private LocalDateTime timePre;

    /**
     * 状态: 1-进行中 2-整改完成 3-撤销
     */
    private String status;

    /**
     * 数据状态：1-有效 0作废
     */
    private String dataStatus;
    
    /**
     * 违章类型主键
     */   
    private String illegalId;

    /**
     * 部门id
     */   
    private String deptId;
    
    /**
     * 经度
     */   
    private Double longitude;

    /**
     * 纬度
     */   
    private Double latitude;

    /**
     * 事件类别
     */   
    private String eventCategory;

    /**
     * 事件名称
     */   
    private String eventName;

    /**
     * 事件描述
     */   
    private String eventDescription;

    /**
     * 描述
     */   
    private String description;

    /**
     * 是否室内
     */   
    private String isIndoor;

    /**
     * 补充地址
     */   
    private String suppleAddress;

    /**
     * 搭建类型
     */   
    private String constructionType;

    /**
     * 搭建材料
     */   
    private String buildingMaterials;

    /**
     * 占地面积
     */      
    private float floorArea;

    /**
     * 层数
     */   
    private int layersNumber;

    /**
     * 现场情况
     */   
    private String liveSituation;

    /**
     * 预警路线id
     */   
    private String pathId;

    /**
     * 预警定点id
     */   
    private String pointId;
    
    
    /**
     * 创建人ID
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 操作人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operateBy;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime operateTime;
    
    /**
     * 预警类型（0存量动工1楼顶加建）
     */
    private String earlyType;
    
    /**
     * 算法版本
     */
    private String version;

    /**
     * 准确率
     */
    private Float accuracy;
    
    /**
     * 场景类型
     */
    private String sceneType;
    
    /**
     * 预警地址
     */
    private String address;

    /**
     * 分派人
     */
    private String disPerson;
    
    /**
     * 分派时间
     */
    private LocalDateTime disTime;
    
    /**
     * 是否加入白名单
     */
    private String white;
    
    /**
     * 确认时间或撤销时间
     */
    private LocalDateTime resetTime;
    
    /**
     * 任务主键
     */
    private String taskId;
}
