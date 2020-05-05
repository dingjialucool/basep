package com.chinobot.plep.app.dispatch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chinobot.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * app调度预警文件
 * </p>
 *
 * @author shizt
 * @since 2019-08-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_app_dispatch_file")
public class AppDispatchFile extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 文件表主键
     */
    private String fileId;

    /**
     * 无人机id
     */
    private String uavId;

    /**
     * 路线建筑id
     */
    private String routeBuildingId;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;

    /**
     * 文件大小
     */
    private Integer fileSize;

    /**
     * 数据状态：1-有效 0作废
     */
    private String dataStatus;

    /**
     * 定点ID
     */
    private String routePointId;

    /**
     * 区域航线主键
     */
    private String pathId;
}
