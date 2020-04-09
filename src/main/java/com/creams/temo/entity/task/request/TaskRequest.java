package com.creams.temo.entity.task.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskRequest {

    @ApiModelProperty("任务id")
    @TableField(value = "task_id")
    private String taskId;

    @ApiModelProperty("任务名称")
    @TableField(value = "task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @TableField(value = "task_desc")
    private String taskDesc;

    @ApiModelProperty("是否并行，0为否，1为是")
    @TableField(value = "is_timing")
    private String isTiming;

    @ApiModelProperty("是否并行，0为否，1为是")
    @TableField(value = "is_parallel")
    private String isParallel;

    @ApiModelProperty("轮询次数")
    @TableField(value = "times")
    private Integer times;

    @ApiModelProperty("用例集")
    @TableField(value = "testSets")
    private String testSets;
}
