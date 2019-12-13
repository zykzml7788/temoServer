package com.creams.temo.entity.testcase.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StScriptResponse {

    @ApiModelProperty("前后置脚本id")
    @TableField(value = "st_script_id")
    private String stScriptId;


    @ApiModelProperty("前后置脚本名称")
    @TableField(value = "st_script_name")
    private String stScriptName;


    @ApiModelProperty("前后置脚本类别")
    @TableField(value = "st_script_type")
    private String stScriptType;

    @ApiModelProperty("用例集id")
    @TableField(value = "set_id")
    private String setId;

    @ApiModelProperty("关联脚本id")
    @TableField(value = "ex_script_id")
    private String exScriptId;
}
