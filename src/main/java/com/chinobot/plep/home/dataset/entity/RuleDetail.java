package com.chinobot.plep.home.dataset.entity;

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
 * 规则明细表
 * </p>
 *
 * @author djl
 * @since 2019-10-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_rule_detail")
public class RuleDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 规则外键
     */
    private String ruleId;

    /**
     * 关系类型（&& 并且 || 或者）
     */
    private String relationType;

    /**
     * 数据集主键
     */
    private String setId;
    
    /**
     * 字段名
     */
    private String field;

    /**
     * 比较类型(>大于 , >= 大于等于,<小于,<=小于等于,==等于, !=不等于, contains包含,not contains不包含, memberof 属于集合, not memberof 不属于集合, matches 匹配, not matches 不匹配  )
     */
    private String compareType;

    /**
     * 比较值
     */
    private String compareValue;

    /**
     * 规则父键
     */
    private String parentId;

    /**
     * 组数
     */
    private Integer groupNum;

    /**
     * 组关系类型(&& 并且， || 或者 )
     */
    private String groupType;

    /**
     * 组内排序
     */
    private String innerSort;
 
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
