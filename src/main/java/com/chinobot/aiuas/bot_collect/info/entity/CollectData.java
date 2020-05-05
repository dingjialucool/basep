package com.chinobot.aiuas.bot_collect.info.entity;

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
 * 采查数据
 * </p>
 *
 * @author huangw
 * @since 2020-03-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bot_collect_data")
public class CollectData extends BaseEntity {

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
     * 采查对象外键
     */
    private String collectUuid;

    /**
     * 任务外键（bot_prospect_flight_work主键）
     */
    private String workUuid;

    /**
     * 附件表外键
     */
    private String fileUuid;

    /**
     * 文件类型: img-图片  vedio-视频
     */
    private String fileType;
    
    /**
     * 排序
     */
    private Integer sort;


}
