package com.creams.temo.entity.datastatistics;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DataStatistics {

//    @ApiModelProperty("任务库名")
//    private Integer taskLibraryName;

    @ApiModelProperty("任务数量")
    private Integer taskNum;

    @ApiModelProperty("开启定时任务数量")
    private Integer taskIsTimingNum;


    @ApiModelProperty("测试用例集数量")
    private Integer testCaseSetNum;

    @ApiModelProperty("测试用例数量")
    private Integer testCaseNum;

    @ApiModelProperty("今日执行用例数量")
    private Integer executeCaseTodayNum;

    @ApiModelProperty("成功率")
    private String successRate;

    @ApiModelProperty("平台运行时间")
    private String executeTime;

    @ApiModelProperty("正在执行任务数量")
    private Integer executeTaskNumNow;




}
