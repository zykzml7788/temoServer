package com.creams.temo.entity.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.security.Timestamp;
import java.util.List;

/**
 *
 * @Description:  任务测试结果
 * @Author: Kevin
 * @Date: Created in 2020-03-06 09:40
 * @Modify By:
 */
@Data
public class TaskResult {

    @ApiModelProperty("任务id")
    private String taskId;

    @ApiModelProperty("任务名称")
    private String taskName;

    @ApiModelProperty("成功用例集数")
    private Integer successNum;

    @ApiModelProperty("总用例集数")
    private Integer totalNum;

    @ApiModelProperty("成功率")
    private Double successRate;

    @ApiModelProperty("轮询次数")
    private Integer times;

    @ApiModelProperty("开始时间")
    private Timestamp startTime;

    @ApiModelProperty("结束时间")
    private Timestamp endTime;

    @ApiModelProperty("执行人员")
    private String person;

    @ApiModelProperty("用例集结果列表")
    private List<SetResult> testsets;
}
