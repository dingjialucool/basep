package com.chinobot.plep.app.logs.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.chinobot.common.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * app错误日志
 * </p>
 *
 * @author shizt
 * @since 2019-10-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_app_exception_logs")
public class AppExceptionLogs extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time;

    private String versionName;

    private String versionCode;

    /**
     * 手机型号
     */
    private String deviceType;

    /**
     * 安卓版本
     */
    private String osVer;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 错误信息
     */
    private String stackTrace;


}
