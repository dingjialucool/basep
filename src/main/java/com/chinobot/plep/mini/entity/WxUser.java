package com.chinobot.plep.mini.entity;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chinobot.common.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 宝安区楼栋信息融合_微信用户信息表
 * </p>
 *
 * @author shizt
 * @since 2019-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("bif_wx_user")
public class WxUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * uuid
     */
    @TableId("uuid")
    private String uuid;

    /**
     * 用户类型：1普通用户2网格员3安格员4管理员
     */
    private String userType;

    /**
     * 刷脸照片文件id
     */
    private String fileId;
    
    /**
     * 微信昵称
     */
    private String nickName;

    /**
     * 性别 -- 0：未知；1：男；2：女
     */
    private String gender;

    /**
     * 微信头像
     */
    private String avatarUrl;

    /**
     * 用户手机
     */
    private String phone;

    /**
     * 小程序用户唯一标识
     */
    private String openId;

    /**
     * 统一用户标识，用于多个小程序识别支付时判断用户。
     */
    private String unionId;

    /**
     * 国家
     */
    private String country;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 语言
     */
    private String language;

    /**
     * 小程序用户session标识
     */
    private String sessionKey;

    /**
     * 用户最后一次登录时间
     */
    private LocalDateTime lastLoginTime;

    /**
     * 用户访问微信服务随机token
     */
    private String accessToken;

    /**
     * token生成时间
     */
    private LocalDateTime tokenGenTime;

    /**
     * 人员ID
     */
    private String personId;

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
