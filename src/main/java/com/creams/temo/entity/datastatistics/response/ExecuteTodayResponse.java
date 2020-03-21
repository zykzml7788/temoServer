package com.creams.temo.entity.datastatistics.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExecuteTodayResponse {


    @ApiModelProperty("今日执行用例数量")
    private Integer executeCaseTodayNum;

    @ApiModelProperty("成功数")
    private String successNum;

    @ApiModelProperty("失败数")
    private String falseNum;

    @ApiModelProperty("成功率")
    private String successRate;
}
