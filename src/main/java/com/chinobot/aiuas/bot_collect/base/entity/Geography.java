package com.chinobot.aiuas.bot_collect.base.entity;

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
 * 地理信息表
 * </p>
 *
 * @author huangw
 * @since 2020-01-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_collect_geography")
public class Geography extends BaseEntity {

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
     * 业务主键
     */
    private String busiId;

    /**
     * 业务类型
     */
    private String busiType;

    /**
     * 类型:point-点  polygon-多边形  line-折线
     */
    private String geoType;

    /**
     * 经纬度集合：;分隔
     */
    private String lnglats;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 中心点经度
     */
    private Double lng;

    /**
     * 中心点纬度
     */
    private Double lat;

    /**
     * 面积
     */
    private double area;
    
}
