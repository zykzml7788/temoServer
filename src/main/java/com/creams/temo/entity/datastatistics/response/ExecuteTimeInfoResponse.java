package com.creams.temo.entity.datastatistics.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@Data
public class ExecuteTimeInfoResponse {

    @ApiModelProperty("平台运行时间")
    private Map<String, Integer> executeTime;

    @ApiModelProperty("正在执行任务数量")
    private Integer executeTaskNumNow;
}
