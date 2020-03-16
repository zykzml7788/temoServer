package com.creams.temo.entity.task.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TimingTaskRequest {

    @ApiModelProperty("任务名称")
    @TableField(value = "task_id")
    private String taskId;

    @ApiModelProperty("任务名称")
    @TableField(value = "task_name")
    private String taskName;

    @ApiModelProperty("任务描述")
    @TableField(value = "task_desc")
    private String taskDesc;

    @ApiModelProperty("是否并行，0为否，1为是")
    @TableField(value = "is_parallel")
    private String isParallel;

    @ApiModelProperty("cron表达式")
    @TableField(value = "cron")
    private String cron;

    @ApiModelProperty("是否并行，0为否，1为是")
    @TableField(value = "is_timing")
    private String isTiming;

    @ApiModelProperty("邮件开关")
    @TableField(value = "is_mail")
    private Integer isMail;

    @ApiModelProperty("开启/关闭")
    @TableField(value = "is_open")
    private String isOpen;

    @ApiModelProperty("接受邮箱")
    @TableField(value = "mail")
    private String mail;

    @ApiModelProperty("轮询次数")
    @TableField(value = "times")
    private Integer times;

    @ApiModelProperty("用例集")
    @TableField(value = "testSets")
    private String testSets;
}
