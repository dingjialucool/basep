package com.chinobot.cityle.ddjk.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * 任务设备调度表
 * </p>
 *
 * @author huangw
 * @since 2019-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_task_equipment_dispatch")
public class TaskEquipmentDispatch extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 任务主键
     */
    private String taskId;

    /**
     * 设备主键
     */
    private String equipmentId;

    /**
     * 巡查时长(分钟)
     */
    private Float duration;

    /**
     * uuid
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

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

    /**
     * 备注
     */
    private String remarks;


}
