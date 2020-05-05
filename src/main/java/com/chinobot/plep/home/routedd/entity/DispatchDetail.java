package com.chinobot.plep.home.routedd.entity;

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
 * 调度明细
 * </p>
 *
 * @author huangw
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_dispatch_detail")
public class DispatchDetail extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 无人机调度主键
     */
    private String uavDspId;

    /**
     * 起飞点主键
     */
    private String pointId;

    /**
     * 到达路程（公里）
     */
    private Float distance;

    /**
     * 到达时间（分钟）
     */
    private Float getTime;
    /**
     * 到达路线
     */
    private String getLine;

    /**
     * 巡查耗时（分钟）
     */
    private Float checkTime;
    
    /**
     * 序号
     */
    private Integer sort;
    
    /**
     * 航线数
     */
    private Integer routeNum;

    /**
     * 事件数
     */
    private Integer eventNum;
    
    /**
     * 区域数
     */
    private Integer areaNum;

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
