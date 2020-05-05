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
 * 设备采集场景数据
 * </p>
 *
 * @author huangw
 * @since 2019-04-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_robot_gather_data")
public class RobotGatherData extends BaseEntity {

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
     * 巡查内容主键
     */
    private String roundsUuid;

    /**
     * 设备编号
     */
    private String robotNo;

    /**
     * 挂载设备编号
     */
    private String mountingEquipmentUuid;

    /**
     * 数据格式（1 视频，2 图片，3文本）
     */
    private String trailType;

    /**
     * 采集文件Id
     */
    private String dataFileUuid;

    /**
     * 文本内容
     */
    private String textContent;

    /**
     * 采集人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operateBy;

    /**
     * 采集时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime operateTime;

    /**
     * 是否预警（1 是，0  否）
     */
    private String isWarning;

    /**
     * 预警内容
     */
    private String warnContent;

    /**
     * 经度
     */
    private Double longitude;

    /**
     * 纬度
     */
    private Double latitude;

    /**
     * cle_push_message推送表主键
     */
    private String pushMessageId;


}
