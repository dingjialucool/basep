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
 * 预警信息
 * </p>
 *
 * @author huangw
 * @since 2020-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_event_warning")
public class Warning extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 业务状态：0-待处理 1-已预警 2-已过滤 3-白名单 4-已失败
     */
    private String businessStatus;

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
     * 任务流水号
     */
    private String taskNo;

    /**
     * 飞行信息外键
     */
    private String flyInfoUuid;

    /**
     * 算法信息外键
     */
    private String algorithmInfoUuid;

    /**
     * 采集对象外键
     */
    private String collectObjectUuid;

    /**
     * 预警时间
     */
    private LocalDateTime warningTime;

    /**
     * 预警类型 （1-事件，2-线索）
     */
    private String warningType;

    /**
     * 任务外键
     */
    private String taskUuid;
    
    /**
     * 预警名称
     */
    private String warningName;

    /**
     * 预警地址
     */
    private String address;

    /**
     * 预警坐标X
     */
    private Float coordinateX;

    /**
     * 预警坐标Y
     */
    private Float coordinateY;

    /**
     * 预警标记W
     */
    private Float indiciaW;

    /**
     * 预警标记H
     */
    private Float indiciaH;

    /**
     * 预警经度
     */
    private Double longitude;

    /**
     * 预警纬度
     */
    private Double latitude;

    /**
     * 准确率
     */
    private Float accuracy;
    
    /**
     * 飞行经度
     */
    private Float flyLongitude;
    /**
     * 飞行纬度
     */
    private Float flyLatitude;
    /**
     * 飞行高度
     */
    private Float flyHeight;
    /**
     * 飞行速度
     */
    private Float flySpeed;
    /**
     * 航班作业外键
     */
    private String flightTaskUuid;
    /**
     * 过滤主键
     */
    private String filterRule;
    /**
     * 过滤移除：1-是，0-否
     */
    private String filterRemove;

    /**
     * 预警图片
     */
    @TableField(exist = false)
    private String warningImg;
    
    /**
     * 基准图片
     */
    @TableField(exist = false)
    private String baseImg;
    
    /**
     * 预警时间字符串
     */
    @TableField(exist = false)
    private String warningTimeStr;
    
    /**
     * 预警经度
     */
    private Double longitudeOrigin;

    /**
     * 预警纬度
     */
    private Double latitudeOrigin;
    
    /**
     * 失败原因
     */
    private String failReason;
}
