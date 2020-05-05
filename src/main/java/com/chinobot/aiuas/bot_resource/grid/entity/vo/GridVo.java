package com.chinobot.aiuas.bot_resource.grid.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @program: chinobot
 * @description:
 * @author: shizt
 * @create: 2020-02-26 11:24
 */
@ApiModel("网格")
@Data
public class GridVo {
    @ApiModelProperty("网格id")
    private String uuid;

    @ApiModelProperty("网格名称")
    private String name;

    @ApiModelProperty("网格描述")
    private String content;

    @ApiModelProperty("部门id")
    private String deptId;

    @ApiModelProperty("创建人ID")
    private String createBy;

    @ApiModelProperty("创建人名称")
    private String createName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("操作人ID")
    private String operateBy;

    @ApiModelProperty("操作人名称")
    private String operateName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty("操作时间")
    private LocalDateTime operateTime;

    @ApiModelProperty("是否删除(0未删除1已删除)")
    private Boolean isDeleted;

    @ApiModelProperty("地理信息id")
    private String geographyId;
}
