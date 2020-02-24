package com.creams.temo.entity.task;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Task {

    @ApiModelProperty("项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("任务id")
    @TableField(value = "task_id")
    private String taskId;

    @ApiModelProperty("任务名称")
    @TableField(value = "task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @TableField(value = "task_desc")
    private String taskDesc;

    @ApiModelProperty("是否定时，0为否，1为是")
    @TableField(value = "is_timing")
    private String isTiming;

    @ApiModelProperty("是否并行，0为否，1为是")
    @TableField(value = "is_parallel")
    private String isParallel;

    @ApiModelProperty("cron表达式")
    @TableField(value = "cron")
    private String cron;

    @ApiModelProperty("轮询次数")
    @TableField(value = "times")
    private Integer times;

    @ApiModelProperty("执行状态（0待执行，1执行中，2执行完毕）")
    @TableField(value = "status")
    private String mail;

    @ApiModelProperty("执行状态（0待执行，1执行中，2执行完毕）")
    @TableField(value = "status")
    private String testSets;

    @ApiModelProperty("执行状态（0待执行，1执行中，2执行完毕）")
    @TableField(value = "status")
    private Integer status;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private String createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time")
    private String updateTime;
}
