package com.chinobot.plep.flyPlan.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chinobot.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 计划建筑关系表
 * </p>
 *
 * @author djl
 * @since 2019-06-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_plan_building")
public class PlanBuilding extends BaseEntity {

    private static final long serialVersionUID = 1L;
    
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;
    
    @TableId(value = "plan_id", type = IdType.UUID)
    private String planId;

    private String buildingId;

    /**
     * 数据状态
     */
    private String dataStatus;
}
