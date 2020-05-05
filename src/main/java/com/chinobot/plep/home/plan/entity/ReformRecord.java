package com.chinobot.plep.home.plan.entity;

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
 * 整改记录表
 * </p>
 *
 * @author huangw
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_reform_record")
public class ReformRecord extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 事件主键
     */
    private String eventId;

    /**
     * 开始整改时间
     */
    private LocalDateTime timeStart;

    /**
     * 结束时间
     */
    private LocalDateTime timeEnd;

    /**
     * 整改类型:1一般 2复查
     */
    private String type;

    /**
     * 人工结论：1-整改完成 2-尚未整改完成
     */
    private String conclusion;

    /**
     * 整改事实
     */
    private String truthReform;

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
