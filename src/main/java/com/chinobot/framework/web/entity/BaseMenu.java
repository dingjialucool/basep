package com.chinobot.framework.web.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.chinobot.common.domain.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("cle_menu")
public class BaseMenu extends BaseEntity{
	 private static final long serialVersionUID = 1L;

	    /**
	     * uuid
	     */
	    @TableId(value = "uuid", type = IdType.UUID)
	    private String uuid;

	    /**
	     * title对应的值
	     */
	    private String titleValue;

	    /**
	     * key对应的值
	     */
	    private String keyValue;

	    /**
	     * icon对应的值
	     */
	    private String iconValue;
	    
	    /**
	     * path对应的值
	     */
	    private String pathValue;


	    /**
	     *  排序
	     */
	    private Integer orderBy;

	    /**
	     * 菜单类型(1菜单2目录3标签)
	     */
	    private String menuType;
	    
	    /**
	     * 父节点
	     */
	    private String parentId;
	    
	    /**
	     * 数据状态：1-有效 0作废
	     */
	    private String dataStatus;
	    
	    @TableField(exist = false)
	    private List<BaseMenu> children;

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
