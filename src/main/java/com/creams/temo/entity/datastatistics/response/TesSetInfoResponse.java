package com.creams.temo.entity.datastatistics.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TesSetInfoResponse {

    @ApiModelProperty("测试用例集数量")
    private Integer testCaseSetNum;

    @ApiModelProperty("测试用例数量")
    private Integer testCaseNum;
}
