package com.chinobot.framework.web.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chinobot.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 行政区划表（2018）
 * </p>
 *
 * @author shizt
 * @since 2019-05-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cb_regions")
public class Regions extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 区划代码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 区划级别
     */
    private String level;

    /**
     * 上级代码
     */
    private String parent;


}
