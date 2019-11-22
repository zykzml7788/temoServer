package com.creams.temo.entity.testcase.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SavesRequest {

    @ApiModelProperty("关联id")
    @TableField(value = "save_id")
    private String saveId;

    @ApiModelProperty("jsonpath表达式")
    @TableField(value = "jexpression")
    private String jexpression;

    @ApiModelProperty("自定义key")
    @TableField(value = "param_key")
    private String paramKey;

    @ApiModelProperty("关联取值来源（响应头，响应体")
    @TableField(value = "save_form")
    private String saveFrom;

    @ApiModelProperty("用例id")
    @TableField(value = "case_id")
    private String caseId;
}
