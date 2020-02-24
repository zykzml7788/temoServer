package com.creams.temo.entity.task.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 执行记录
 */
@Data
public class ExecutedRowResponse {

    /**
     * 执行日志
     */
    @ApiModelProperty("执行日志")
    private String logs;

    /**
     * 执行状态
     */
    @ApiModelProperty("执行状态")
    private Integer status;

    /**
     * 用例id
     */
    @ApiModelProperty("用例id")
    private String caseId;


    /**
     * 用例名称
     */
    @ApiModelProperty("用例名称")
    private String caseName;

    /**
     * 执行顺序
     */
    @ApiModelProperty("执行顺序")
    private Integer index;

    /**
     * 集合id
     */
    @ApiModelProperty("集合id")
    private String setId;


    /**
     * 执行的唯一标识(执行一次用例集就加1)
     */
    @ApiModelProperty("执行的唯一标识(执行一次用例集就加1)")
    private Integer indexOfExecuted;
}
