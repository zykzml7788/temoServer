package com.creams.temo.entity.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @Description: 用例集测试结果
 * @Author: Kevin
 * @Date: Created in 2020-03-06 09:41
 * @Modify By:
 */
@Data
class SetResult {

    @ApiModelProperty("用例集名称")
    private String setName;

    @ApiModelProperty("成功用例数")
    private Integer successNum;

    @ApiModelProperty("总用例数")
    private Integer totalNum;

    @ApiModelProperty("用例结果列表")
    private String caseResults;
}