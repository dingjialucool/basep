package com.chinobot.plep.home.dataset.entity;

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
 * 预警信息表
 * </p>
 *
 * @author djl
 * @since 2019-10-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_warning_info")
public class WarningInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "uuid", type = IdType.UUID)
    private String uuid;

    /**
     * 预警类型（01存量动工02楼顶加建）
     */
    private String earlyType;

    /**
     * 算法版本
     */
    private String version;

    /**
     * 准确率
     */
    private Float accuracy;

    /**
     * 场景类型
     */
    private String sceneType;

    /**
     * 预警地址
     */
    private String address;

    /**
     * 分派人
     */
    private String disPerson;

    /**
     * 预警定点id
     */
    private String pointId;

    /**
     * 预警航线id
     */
    private String pathId;

    /**
     * 转换后经度，系统采用坐标系高德
     */
    private Double longitude;

    /**
     * 转换后纬度，系统采用坐标系高德
     */
    private Double latitude;

    /**
     * 预警图片原始经度
     */
    private Double longitudeN;

    /**
     * 预警图片原始纬度
     */
    private Double latitudeN;

    /**
     * 预警图片gps高度
     */
    private Float heightN;

    /**
     * 预警图片语义类别
     */
    private String objTypeN;

    /**
     * 预警图片标记框横坐标
     */
    private Float labelXN;

    /**
     * 预警图片标记框纵坐标
     */
    private Float labelYN;

    /**
     * 预警图片标记框宽度
     */
    private Float labelWN;

    /**
     * 预警图片标记框高度
     */
    private Float labelHN;

    /**
     * json文件Id
     */
    private String jsonId;

    /**
     * 部门id
     */
    private String deptId;

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

    /**
     * 基准图片原始经度
     */
    private Double longitudeB;

    /**
     * 基准图片原始纬度
     */
    private Double latitudeB;

    /**
     * 基准图片gps高度
     */
    private Float heightB;

    /**
     * 基准图片语义类别
     */
    private String objTypeB;

    /**
     * 基准图片标记框横坐标
     */
    private Float labelXB;

    /**
     * 基准图片标记框纵坐标
     */
    private Float labelYB;

    /**
     * 基准图片标记框宽度
     */
    private Float labelWB;

    /**
     * 基准图片标记框高度
     */
    private Float labelHB;

    /**
     * 规则过滤类型：1-有效；2-规则；3-白名单）
     */
    private String filterType;
    
    /**
     * 规则主键
     */
    private String filterRule;
    
    /**
     * 过滤移除：1-是，0-否
     */
    private String filterRemove;

    /**
     * 任务主键
     */
    private String taskId;
}
