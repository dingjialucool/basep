package com.chinobot.plep.home.point.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.chinobot.common.domain.BaseEntity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 航点表
 * </p>
 *
 * @author djl
 * @since 2019-11-14
 */
@ApiModel(description = "航点信息")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_fixed_fly_point")
public class FixedFlyPoint extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.UUID)
    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "航点名称")
    private String name;

    @ApiModelProperty(value = "定点外键")
    private String fixedId;

    @ApiModelProperty(value = "经纬度")
    private String center;

	@ApiModelProperty(value = "航点高度")
    private Float height;

	@ApiModelProperty(value = "航向角")
    private Float flyAngle;

	@ApiModelProperty(value = "云台俯仰角")
    private Float cameraAngle;

	@ApiModelProperty(value = "航点排序")
    private Integer sort;

	@ApiModelProperty(value = "照片导入的照片主键")
	private String fileId;
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
