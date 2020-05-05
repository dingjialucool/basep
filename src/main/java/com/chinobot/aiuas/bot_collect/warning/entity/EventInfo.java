package com.chinobot.aiuas.bot_collect.warning.entity;

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
 * 预警事件
 * </p>
 *
 * @author djl
 * @since 2020-02-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_event_info")
public class EventInfo extends BaseEntity {

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
     * 事件流水号
     */
    private String eventNo;

    /**
     * 事件名称
     */
    private String eventName;

    /**
     * 事件类型   1-事件 2-线索
     */
    private String eventType;

    /**
     * 事件来源   1-算法 2-人工
     */
    private String eventSource;

    /**
     * 事件等级   A、 B、 C、
     */
    private String eventLevel;

    /**
     * 业务状态： 10-待确认 11-已确认 20-待治理 30-待核查 40-待办结 90-已办结 99-已撤销
     */
    private String businessStatus;

    /**
     * 主办人(防范)
     */
    private String hostBy;
    
    /**
     * 主办机构(防范)
     */
    private String hostUnit;
    
    /**
     * 主办人(治理)
     */
    private String hostByGovern;
    
    /**
     * 主办机构(治理)
     */
    private String hostUnitGovern;
    
    /**
     * 主办人(救援)
     */
    private String hostByRescue;
    
    /**
     * 主办机构(救援)
     */
    private String hostUnitRescue;
    
    /**
     *网格(防范)
     */
    private String grid;
    
    /**
     *网格(治理)
     */
    private String gridGovern;
    
    /**
     *网格(救援)
     */
    private String gridRescue;

}
