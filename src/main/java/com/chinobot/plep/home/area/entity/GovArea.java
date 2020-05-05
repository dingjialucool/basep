package com.chinobot.plep.home.area.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
 * @since 2019-09-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_gov_area")
public class GovArea extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 自增主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父节点主键
     */
    private Integer parentId;
    
    /**
     * 名称
     */
    private String name;

    /**
     * 自动维护层级关系code
     */
    private String code;

    /**
     * 源边界坐标
     */
    private String sourceBoundary;

    /**
     * 源坐标系：WGS84，GCJ02，BD09
     */
    private String sourceLnglatSystem;

    /**
     * 目标边界坐标
     */
    private String targetBoundary;

    /**
     * 目标坐标系：WGS84，GCJ02，BD09
     */
    private String targetLnglatSystem;

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
     * 层级，顶级节点为1
     */
    private Integer level;
    
    /**
     * 目标边界坐标格式化
     */
    private String targetBoundaryFomat;
}
