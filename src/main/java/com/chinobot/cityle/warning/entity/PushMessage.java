package com.chinobot.cityle.warning.entity;

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
 * 推送信息表
 * </p>
 *
 * @author dingjl
 * @since 2019-03-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_push_message")
public class PushMessage extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 0未推送1已推送
     */
    private String pushStatus;

    /**
     * 推送时间
     */
    private LocalDateTime pushTime;

    /**
     * 确认状态(0未确认,1已确认)
     */
    private String confireStatus;

    /**
     * 确认时间
     */
    private LocalDateTime confireTime;

    /**
     * 推送人ID
     */
    private String pusherId;

    /**
     * 推送给ID
     */
    private String acceptorId;

    /**
     * 推送渠道id
     */
    private String pushWay;

    /**
     * 确认渠道0手机,1邮箱2,微信小程序
     */
    private String confireWay;

    /**
     * 推送次数
     */
    private String pushNumber;

    /**
     * 返回信息
     */
    private String returnMessage;

    /**
     * 操作人ID
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String operateBy;

    /**
     * 数据状态：1-有效 0作废
     */
    private String dataStatus;

    /**
     * 操作时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime operateTime;

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
     * 预警表主键
     */
    private String warningId;

}
