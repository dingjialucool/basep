package com.chinobot.plep.home.dataset.entity;

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
 * 规则表
 * </p>
 *
 * @author djl
 * @since 2019-10-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_rule")
public class Rule extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 数据集主键
     */
    private String setId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String decribe;

    /**
     * 提示
     */
    private String tips;
    
    /**
     * 0禁止1启用
     */
    private String status;

    /**
     * 数据状态：1-有效 0作废
     */
    private String dataStatus;
    
    private String isGlobal;

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
     * 场景主键
     */
    private String sceneId;

    /**
     * 优先级，越大优先级越高
     */
    private Integer priority;
    
    /**
     * 任务
     */
    private String taskId;

}
