package com.chinobot.cityle.ddjk.entity;

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
 * 设备采集飞行数据
 * </p>
 *
 * @author huangw
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_robot_flight")
public class RobotFlight extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 场景主键
     */
    private String sceneUuid;

    /**
     * 任务主键
     */
    private String taskUuid;

    /**
     * 设备编号
     */
    private String robotNo;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * 海拔
     */
    private Float altitude;

    /**
     * 剩余电量
     */
    private Float dumpEnergy;

    /**
     * 位置类型（1 起点，2 终点，3 其它）
     */
    private String locationType;

    /**
     * 飞行速度
     */
    private Float flightSpeed;

    /**
     * 飞行高度
     */
    private Float flyingHeight;

    /**
     * 飞行朝向
     */
    private Float flightOrientation;

    /**
     * 相机角度
     */
    private Float cameraAngle;

    /**
     * 操作人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operateBy;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime operateTime;


}
