package com.chinobot.plep.home.report.entity;

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
 * @author djl
 * @since 2019-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_report")
public class Report extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 区域
     */
    private String areaId;

    /**
     * 频率(2周 3月 4季度 5年)
     */
    private String frequency;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
    
    /**
     * 1 自动生成 2.自定义生成
     */
    private String type;
    
    /**
     * 根据频率判断属于哪周，哪月，哪个季度，哪年
     */
    private Integer cycle;
    
    /**
     * 年份
     */
    private Integer years;

    private String dataStatus;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime operateTime;


}
