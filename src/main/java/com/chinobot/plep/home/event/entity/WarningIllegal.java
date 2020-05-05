package com.chinobot.plep.home.event.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.chinobot.common.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 预警违章类型表
 * </p>
 *
 * @author djl
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_warning_illegal")
public class WarningIllegal extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 事件主键
     */
    @TableId(value = "event_id", type = IdType.UUID)
    private String eventId;

    /**
     * 违章类型主键
     */
    private String illegalId;


}
