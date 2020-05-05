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
@TableName("p_output_paramter")
public class OutputParamter extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 航线主键
     */
    private String pathId;

    /**
     * 总航向长度
     */
    private Float flightTotalLong;

    /**
     * 面积
     */
    private Float area;

    /**
     * 总航点数
     */
    private Integer pointTotal;

    /**
     * 预计总飞行时间(秒)
     */
    private Float expectFlyTime;

    /**
     * 预计总拍照数
     */
    private Integer expectPhotoTime;

    /**
     * 拍摄间隔(秒)
     */
    private Float playSpace;
    
    /**
     * 从起飞点出发再回到起飞点的时间（分钟）
     */
    private Float timeIncludePoint;

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
