package com.chinobot.cityle.ddjk.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
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
 * 
 * </p>
 *
 * @author huangw
 * @since 2019-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_task_xc")
public class TaskXc extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 任务编号
     */
    private String taskCode;

    /**
     * 场景
     */
    private String refSlience;

    /**
     * 任务状态
     */
    private String taskStatus;

    /**
     * 完成时间
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private LocalDateTime completeTime;

    /**
     * 巡查时间
     */
    private LocalDate xcTime;
    
    /**
     * 提前提醒天数
     */
    private Integer earlyDay;
    
    /**
     * 判断是日常任务还是专项任务
     */
    private String normalPro;
    
    /**
     * 任务状态变化说明(记录最后一条任务说明)
     */
    private String taskExplain;
    
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
