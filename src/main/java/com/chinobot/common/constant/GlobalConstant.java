package com.chinobot.common.constant;

public interface GlobalConstant {
    /**
     * 数据状态
     */
    String DATA_STATUS_VALID = "1"; // 有效
    String DATA_STATUS_INVALID = "0"; // 无效
    
    /**
     * 图片url前缀
     */
    String IO_READ_IMAGE_PREFIX = "/api/file/ioimage?fileId=";
    /**
     * 系统访问ip
     */
    String SYSTEM_IP = "http://localhost";
    /**
     * 用户默认密码
     */
    String DEFAULT_PASSWORD = "123456";
    /**
     * 超级管理员Id
     */
    String SUPER_ADMIN_ID = "01";
    
    /**
     * 部门id
     */
    String SUPER_DEPT_ID = "8ff207f2698311e9881d0242ac110005";
    /**
     * 部门name
     */
    String SUPER_DEPT_NAME = "深圳市";

    /**
     * 是否删除 0-未删除 1-已删除
     */
    boolean IS_DELETED_NO = false;
    /**
     * 是否删除 0-未删除 1-已删除
     */
    boolean IS_DELETED_YES = true;
}
