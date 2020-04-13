package com.creams.temo.entity.testcase;

import com.creams.temo.enums.ScriptType;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 后置脚本
 */
@Data
public class TeardownScript {

    @ApiModelProperty("脚本id")
    private String scriptId;

    @ApiModelProperty("脚本类型(SET,DB)")
    private ScriptType scriptType;
}
