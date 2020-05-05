package com.chinobot.cityle.base.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chinobot.common.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 巡查内容模板
 * </p>
 *
 * @author shizt
 * @since 2019-04-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_scene_task_templet")
public class SceneTaskTemplet extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 场景类别
     */
    private String sceneType;

    /**
     * 巡查内容
     */
    private String tname;

    /**
     * 备注
     */
    private String content;

//    /**
//     * 巡查标准
//     */
//    private String tindex;
//
//    /**
//     * 标准值
//     */
//    private String tvalue;

    /**
     * 推送渠道
     */
    private String pushWay;
    
    /**
     * 预判处置部门
     */
    private String deptId;

    /**
     * 任务等级
     */
    private String level;

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
