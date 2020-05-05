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
 * 部门
 * </p>
 *
 * @author shizt
 * @since 2019-03-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_dept")
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 地址库id
     */
    private String abId;

    /**
     * 上级部门id
     */
    private String parentId;

    /**
     * 名称
     */
    private String dname;

    /**
     * 编号
     */
    private String dcode;

    /**
     * 地址
     */
    private String address;

    /**
     * 是否可以删除：1是 0否
     */
    private String isDel;
    
    /**
     * 数据状态：1-有效 0作废
     */
    private String dataStatus;


    /**
     * 部门地图行政区划文件
     */
    private String mapPath;

    /**
     * 部门编码
     */
    private String deptCode;
    
    /**
     * 行政区划主键
     */
    private Integer areaId;
    
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

    @TableField(exist = false)
    private String areaCode;

    /**
     * 机构(码值bot_collect_organization)
     */
    private String organization;
}
