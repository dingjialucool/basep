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
 * 
 * </p>
 *
 * @author huangw
 * @since 2019-06-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_fly_config")
public class FlyConfig extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 区域主键
     */
    private String areaId;

    /**
     * 飞行高度
     */
    private Float flyHeight;
        
    /**
     * 飞行速度
     */
    private Float flySpeed;

    /**
     * 航行重叠率
     */
    private Float flightOverplay;
    
    /**
     * 续航（分钟）
     */
    private Float timeAll;
    /**
     * 空余时间（分钟）
     */
    private Float timeLeft;

    /**
     * 旁向重叠率
     */
    private Float lateralOverplay;

    /**
     * 数据状态
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
