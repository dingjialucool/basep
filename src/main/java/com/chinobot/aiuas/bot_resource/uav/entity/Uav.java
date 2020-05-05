package com.chinobot.aiuas.bot_resource.uav.entity;

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
 * 无人机
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_resource_uav")
public class Uav extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 所属区域级联code
     */
    @TableField(fill = FieldFill.INSERT)
    private String areaCascadeCode;

    /**
     * 所属部门级联code
     */
    @TableField(fill = FieldFill.INSERT)
    private String deptCascadeCode;

    /**
     * 是否删除(0未删除1已删除)
     */
    private Boolean isDeleted;

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
     * 无人机名称
     */
    private String ename;

    /**
     * 设备序列号
     */
    private String serialNumber;

    /**
     * 机型外键
     */
    private String uavTypeUuid;

    /**
     * 机型外键
     */
    private String personUuid;

    /**
     * 运行状态1：已运行，0：未运行
     */
    private String runStatus;
}
