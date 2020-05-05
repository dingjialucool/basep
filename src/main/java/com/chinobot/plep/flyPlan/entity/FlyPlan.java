package com.chinobot.plep.flyPlan.entity;

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
 * 飞行计划表
 * </p>
 *
 * @author djl
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_fly_plan")
public class FlyPlan extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 名称
     */
    private String name;

    /**
     * 计划类型：1周期 2专项
     */
    private String planType;

    /**
     * 专项类型：1一般专项 2整改复查
     */
    private String specialType;
    
    /**
     * 策略：1地毯覆盖式 2目标跟踪式
     */
    private String strategy;
    /**
     * 整改记录主键
     */
    private String reformId;

    /**
     * 时间起
     */
    private LocalDate timeStart;

    /**
     * 时间止
     */
    private LocalDate timeEnd;

    /**
     *上一次任务生成的时间 
     */
    private LocalDate last;
    
    /**
     * 启用状态
     */
    private String useStatus;
    
    /**
     * 部门
     */
    private String deptId;
    
    /**
     * 周期
     */
    private Float cycle;

    /**
     * 周期单位：1小时 2天
     */
    private String cycleUnit;

    /**
     * 状态：1正在运行 2已完成 9已停止
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
