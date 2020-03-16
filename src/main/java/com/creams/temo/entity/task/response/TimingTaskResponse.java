package com.creams.temo.entity.task.response;

import com.baomidou.mybatisplus.annotation.TableField;
import com.creams.temo.entity.task.TestSet;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class TimingTaskResponse {

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
    @TableField(value = "is_parallel")
    private String isParallel;

    @ApiModelProperty("cron表达式")
    @TableField(value = "cron")
    private String cron;

    @ApiModelProperty("轮询次数")
    @TableField(value = "times")
    private Integer times;

    @ApiModelProperty("开启/关闭")
    @TableField(value = "is_open")
    private Integer isOpen;

    @ApiModelProperty("邮件开关")
    @TableField(value = "is_mail")
    private Integer isMail;

    @ApiModelProperty("邮件")
    @TableField(value = "mail")
    private String mail;

    @ApiModelProperty("用例集")
    @TableField(value = "testSets")
    private String testSets;

    @ApiModelProperty("用例列表")
    @TableField(value = "testSetList")
    private List<TestSet> testSetList;

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private String createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time")
    private String updateTime;
}
