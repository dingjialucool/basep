package com.chinobot.plep.home.plan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.chinobot.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 飞行任务表
 * </p>
 *
 * @author huangw
 * @since 2019-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_fly_task")
public class FlyTask extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 飞行计划主键
     */
    private String planId;

    /**
     * 飞行区域主键
     */
    private String pointId;

    /**
     * 建筑主键
     */
    private String buildingId;

    /**
     * 无人机主键
     */
    private String uavId;

    /**
     * 接收人
     */
    private String recieveId;

    /**
     * 接收时间
     */
    private LocalDateTime recieveTime;
    /**
     * 路线主键
     */
    private String routeId;
    
    /**
     * 任务时间
     */
    private LocalDate taskTime;

    /**
     * 状态：0待接收 1执行中 3已完成 5已超期
     */
    private String status;

    /**
     * 数据状态：1-有效 0作废
     */
    private String dataStatus;

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


}
