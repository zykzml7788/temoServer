package com.creams.temo.entity.task;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * @Description: 用例测试结果
 * @Author: Kevin
 * @Date: Created in 2020-03-06 09:44
 * @Modify By:
 */
@Data
public class CaseResult {


    @ApiModelProperty("执行日志")
    private String logs;


    @ApiModelProperty("执行状态")
    private Integer status;


    @ApiModelProperty("用例名称")
    private String caseName;


    @ApiModelProperty("执行顺序")
    private Integer index;

}
