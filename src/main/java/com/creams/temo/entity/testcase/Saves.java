package com.creams.temo.entity.testcase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "saves")
public class Saves {

    @ApiModelProperty("项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("关联id")
    @TableField(value = "save_id")
    private String saveId;

    @ApiModelProperty("jsonpath表达式")
    @TableField(value = "jexpression")
    private String jexpression;

    @ApiModelProperty("自定义key")
    @TableField(value = "key")
    private String key;

    @ApiModelProperty("关联取值来源（响应头，响应体")
    @TableField(value = "save_form")
    private String saveFrom;

    @ApiModelProperty("用例id")
    @TableField(value = "case_id")
    private String caseId;
}
