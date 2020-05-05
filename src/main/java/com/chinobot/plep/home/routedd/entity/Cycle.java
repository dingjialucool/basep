package com.chinobot.plep.home.routedd.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDate;
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
 * 周期计划表
 * </p>
 *
 * @author huangw
 * @since 2019-10-23
 */
@ApiModel(description = "周期信息")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_cycle")
public class Cycle extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;


    /**
     * 时间止
     */
    @ApiModelProperty(value = "时间止")
    private LocalDate timeEnd;

    /**
     * 周期
     */
    @ApiModelProperty(value = "周期")
    private Integer cycle;


    @ApiModelProperty(value = "周期单位")
    private String cycleUnit;

    /**
     * 周期计划名称
     */
    @ApiModelProperty(value = "周期名称")
    private String cycleName;

    /**
     * 0关闭1开启
     */
    @ApiModelProperty(value = "状态")
    private String useStatus;

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
     * 上一次任务生成的时间
     */
    @ApiModelProperty(value = "上一次任务生成的时间")
    private LocalDate last;
    
    /**
     * 时间起
     */
    @ApiModelProperty(value = "时间起")
    private LocalDate timeStart;

    /**
     * 部门主键
     */
    private String deptId;
    
    /**
     * 提前天数
     */
    @ApiModelProperty(value = "提前天数")
    private Integer earlyDay;
}
