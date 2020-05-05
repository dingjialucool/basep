package com.chinobot.aiuas.bot_collect.task.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;

import com.chinobot.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 防治救单位
 * </p>
 *
 * @author huangw
 * @since 2020-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_collect_unit")
public class Unit extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 所属区域级联code;
     */
    @TableField(fill = FieldFill.INSERT)
    private String areaCascadeCode;

    /**
     * 所属部门级联code
     */
    @TableField(fill = FieldFill.INSERT)
    private String deptCascadeCode;

    /**
     * 是否删除： 0-未删除 1-已删除
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
     * 采查任务外键
     */
    private String taskUuid;

    /**
     * 负责人类型（1-网格  2-片区 3-指定）
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String unitPersonType;

    /**
     * 负责人
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String unitPersonUuid;

    /**
     * 单位类型 （1-防范，2-治理，3-救援救灾）
     */
    private String unitType;

    /**
     * 机构(码值bot_collect_organization)
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String organization;


}
