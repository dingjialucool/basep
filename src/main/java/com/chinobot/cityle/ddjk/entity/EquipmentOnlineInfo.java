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
 * 
 * </p>
 *
 * @author huangw
 * @since 2019-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_equipment_online_info")
public class EquipmentOnlineInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 设备编码
     */
    private String equipmentCode;

    /**
     * 设备主键（和编码不确定哪个字段关联）
     */
    private String equipmentId;

    /**
     * 已飞行时长（分钟）
     */
    private Float consumeLong;

    /**
     * 预计还可飞行时长
     */
    private Float remainderLong;

    /**
     * 高度
     */
    private Float height;

    /**
     * 速度
     */
    private Float speed;

    /**
     * 机身温度
     */
    private Float temperature;

    /**
     * 位置
     */
    private String coordinate;

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
