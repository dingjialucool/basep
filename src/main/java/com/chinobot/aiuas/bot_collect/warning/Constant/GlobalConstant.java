package com.chinobot.aiuas.bot_collect.warning.Constant;

public interface GlobalConstant {
    /**
     * 业务常量模块
     */
	 String QRMODULE_IMG = "qrmodule_img"; // 图片确认模块 
	    String FKMODULE_IMG = "fkmodule_img"; // 图片反馈模块 反馈表bot_event_feedback ID
	    String DBMODULE_IMG = "dbmodule_img"; // 图片督办模块 督办表bot_event_supervise ID
	    String HCMODULE_IMG = "hcmodule_img"; // 图片核查模块  
	    String BJMODULE_IMG = "bjmodule_img"; // 图片办结模块
	    String YJMODULE_IMG = "yjmodule_img"; // 图片预警模块
	    
	    String QRMODULE_FILE = "qrmodule_file"; // 文件确认模块
	    String FKMODULE_FILE = "fkmodule_file"; // 文件反馈模块
	    String DBMODULE_FILE = "dbmodule_file"; // 文件督办模块
	    String HCMODULE_FILE = "hcmodule_file"; // 文件核查模块
	    String BJMODULE_FILE = "bjmodule_file"; // 文件办结模块
	    
	    String YJMODULE_VEDIO = "yjmodule_vedio"; // 视频预警模块
	    
	    String ZLMODULE_IMG = "zlmodule_img"; // 图片治理模块
	    String ZLMODULE_FILE = "zlmodule_file"; // 文件治理模块
	    
	    String WARN_IMG = "warning_img";//预警消息图片
	    String WARN_VED = "warn_ved";//预警消息视频
	    String BASE_IMG_WARN = "base_img_warn";//预警基准图
	    /**
	     * 采查作业推流视频
	     */
	    String STREAN_VEDIO = "strean_vedio";
	    
	    /**
	     * 采查作业日志
	     */
	    String LOG_WORK  = "log_work";
	    
	    String OBJECT_MEDIA = "object_media";

    /**
     * 预警事件状态码
     */
    
    /**
    * 待确认 10 
    */
   String TO_BE_CONFIRMED = "10";
   /**
    * 已确认 11
    */
   String CONFIRMED = "11";
   /**
    * 待治理 20 
    */
   String TO_BE_GOVERNED = "20";
   /**
    *  待核查 30
    */
   String TO_BE_CHECKED = "30";
   
   /**
    * 待办结 40
    */
   String TO_BE_COMPLETED = "40";
   
   /**
    * 已办结 90
    */
   String COMPLETED = "90";
   
   /**
    * 已撤销 99
    */
   String REVOKED = "99";
   
   /**
    * 白名单 88
    */
   String WHITE_LIST = "88";
   
   /**
    * 负债人类型：区划分
    */
   String AREA_UNIT_PERSON = "2";
   
   /**
    * 负债人类型：街道划分
    */
   String STREET_UNIT_PERSON = "3";
   
   /**
    * 负债人类型：社区划分
    */
   String COMMUNITY_UNIT_PERSON = "4";
   
   /**
    * 负债人类型：网格划分
    */
   String GRID_UNIT_PERSON = "5";
   
   /**
    * 负债人类型：指定人划分
    */
   String ASSIGN_UNIT_PERSON = "10";
   
   /**
    * 单位类型：防范
    */
   String GUARD_UNIT_TYPE = "1";
   
   /**
    * 单位类型：治理
    */
   String GOVERN_UNIT_TYPE = "2";
   
   /**
    * 单位类型：救援
    */
   String RESCUE_UNIT_TYPE = "3";
   
   /**
    * 事件来源：算法
    */
   String SF_SOURCE = "1";
   
   /**
    * 航班模式：拍照
    */
   String PHOTO_MODE = "photo";
   /**
    * 预警状态：失败
    */
   String FAIL_TYPE = "4";
   /**
    * 预警状态：已预警
    */
   String WARN_TYPE = "1";
   /**
    * 采查数据 类型bot_collect_data
    */
   String FILE_TYPE_IMAGE = "img";
   
   /**
    * 采查数据 类型bot_collect_data
    */
   String FILE_TYPE_VEDIO = "vedio";
   
   /**
    * 数据状态 有效1
    */
   String DATA_STATUS_VALID = "1"; // 有效
   /**
    * 数据状态 无效0
    */
   String DATA_STATUS_INVALID = "0"; // 无效
   
   /**
    * 是否删除 0-未删除 1-已删除
    */
   boolean IS_DELETED_NO = false;
   /**
    * 是否删除 0-未删除 1-已删除
    */
   boolean IS_DELETED_YES = true;
   
}
