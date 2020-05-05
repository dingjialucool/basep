package com.chinobot.aiuas.bot_collect.resource.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;

import java.time.LocalDate;
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
 * 天气预报表
 * </p>
 *
 * @author huangw
 * @since 2020-02-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_resource_weather")
public class Weather extends BaseEntity {

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
     * 省份
     */
    private String province;

    /**
     * 区域
     */
    private String city;

    /**
     * 区域编码
     */
    private String adcode;

    /**
     * 预报发布时间
     */
    private LocalDateTime reportTime;

    /**
     * 日期
     */
    private LocalDate castDate;

    /**
     * 白天天气现象
     */
    private String dayWeather;

    /**
     * 晚上天气现象
     */
    private String nightWeather;

    /**
     * 白天温度
     */
    private String dayTemp;

    /**
     * 晚上温度
     */
    private String nightTemp;

    /**
     * 白天风向
     */
    private String dayWind;

    /**
     * 晚上风向
     */
    private String nightWind;

    /**
     * 白天风力
     */
    private String dayPower;

    /**
     * 晚上风力
     */
    private String nightPower;


}
