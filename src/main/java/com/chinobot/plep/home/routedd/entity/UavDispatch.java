package com.chinobot.plep.home.routedd.entity;

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
 * 无人机调度表
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_uav_dispatch")
public class UavDispatch extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;
    
    /**
     * 路线名称
     */
    private String routeName;

    /**
     * 无人机主键
     */
    private String uavId;

    /**
     * 巡查日期
     */
    private LocalDate time;
    
    /**
     * 总耗时
     */
    private Float timeAll;
    
    /**
     * 事件数量
     */
    private Integer eventNum;
    
    /**
     * 区域数
     */
    private Integer areaNum;
    
    /**
     * 飞行员
     */
    private String flyPerson;
    
    /**
     * 部门主键
     */
    private String deptId;
    
    /**
     * 是否下达（1-已下达 0-未下达）
     */
    private String isAssign;

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
     * 周期主键
     */
    private String cycleId;
}
