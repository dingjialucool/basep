package com.chinobot.aiuas.bot_collect.task.entity.vo;

import com.chinobot.common.file.entity.FileBus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @program: aiuas
 * @description:
 * @author: shizt
 * @create: 2020-01-13 16:57
 */
@ApiModel(description = "采查任务详细")
@Data
public class TaskInfoVo {
    @ApiModelProperty(value = "主键")
    private String uuid;

    @ApiModelProperty(value = "任务名称")
    private String tName;

    @ApiModelProperty(value = "预警类型（1-事件，2-线索）")
    private String resultType;

    @ApiModelProperty(value = "预警名称")
    private String resultName;

    @ApiModelProperty(value = "风险等级")
    private String dangerGrade;

    @ApiModelProperty(value = "采查领域主键")
    private String domainUuid;

    @ApiModelProperty(value = "采查领域名称")
    private String domainName;

    @ApiModelProperty(value = "采查场景主键")
    private String sceneUuid;

    @ApiModelProperty(value = "采查场景名称")
    private String sceneName;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "是否删除")
    private Boolean isDeleted;

    @ApiModelProperty(value = "文件业务关联")
    private List<FileBus> files;

    @ApiModelProperty(value = "防治救单位")
    private UnitVo unitInfo;

    @ApiModelProperty(value = "算法主键")
    private String algorithmUuid;

    @ApiModelProperty(value = "算法名称")
    private String algorithmName;

    @ApiModelProperty(value = "采查结果")
    private String collectResult;

}
