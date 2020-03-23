package com.creams.temo.entity.datastatistics.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ExecuteSevenDaysResponse {

    @ApiModelProperty("成功用例数量")
    private Integer successNum;

    @ApiModelProperty("失败用例数量")
    private Integer falseNum;

    @ApiModelProperty("成功率")
    private String successRate;

    @ApiModelProperty("日期")
    private String days;


}
