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
 * 机型
 * </p>
 *
 * @author shizt
 * @since 2020-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_resource_uav_type")
public class UavType extends BaseEntity {

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
     * 型号名称
     */
    private String moduleName;

    /**
     * 类型（1.固定翼、2.多旋翼）
     */
    private String type;

    /**
     * 最大尺寸-长（mm）
     */
    private Float maxLength;

    /**
     * 最大尺寸-宽（mm）
     */
    private Float maxWidth;

    /**
     * 最大尺寸-高（mm）
     */
    private Float maxHeight;

    /**
     * 最大重量（kg）
     */
    private Float maxWeight;

    /**
     * 描述
     */
    private String uavDescription;

    /**
     * 最大载重（kg）
     */
    private Float maxLoad;

    /**
     * 最大可承受风速（m/s）
     */
    private Integer maxWindSpeed;

    /**
     * 最大飞行时间（min）
     */
    private Float maxFlyTimeMin;

    /**
     * 工作环境最低温度（。C）
     */
    private Float jobEnvironmentLowerTemperature;

    /**
     * 工作环境最高温度（。C）
     */
    private Float jobEnvironmentHigherTemperature;

    /**
     * 厂商名称
     */
    private String firmName;

    /**
     * 联系人
     */
    private String linkPerson;

    /**
     * 联系电话
     */
    private String linkPhone;


}
