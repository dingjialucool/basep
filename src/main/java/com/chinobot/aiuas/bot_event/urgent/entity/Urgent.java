package com.chinobot.aiuas.bot_event.urgent.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.chinobot.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * <p>
 * 紧急调度表
 * </p>
 *
 * @author shizt
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_event_urgent")
public class Urgent extends BaseEntity {

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
     * 调度名称
     */
    private String urgentName;

    /**
     * 目标坐标
     */
    private String targetLnglat;

    /**
     * 目标地址
     */
    private String targetAddress;

    /**
     * 无人机
     */
    private String uavId;

    /**
     * 飞手
     */
    private String personId;

    /**
     * 飞行距离
     */
    private String flyDistance;

    /**
     * 预计最快耗时
     */
    private String speedSecond;

    /**
     * 最快到达时间
     */
    private String arrivalTime;

    /**
     * 飞手坐标
     */
    private String personLnglat;

    /**
     * 简要说明
     */
    private String remark;

    /**
     * 航线文件ID
     */
    private String routeFileId;

    /**
     * 1:已下达 2: 待完成 3：已完成 
     */
    private String workStatus;

    /**
     * 飞行速度
     */
    private Float flySpeed;

    /**
     * 飞行高度
     */
    private Float flyHeight;


}
