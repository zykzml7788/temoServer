package com.creams.temo.entity.testcase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Value;

@Data
@TableName(value = "verify")
public class Verify {

    @ApiModelProperty("项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("断言id")
    @TableField(value = "verify_id")
    private String verifyId;

    @ApiModelProperty("断言类型（正则 or jsonpath")
    @TableField(value = "verify_type")
    private String verifyType;

    @ApiModelProperty("jsonpath表达式")
    @TableField(value = "jexpression")
    private String jexpression;

    @ApiModelProperty("预期结果")
    @TableField(value = "expect")
    private String expect;

    @ApiModelProperty("断言关系")
    @TableField(value = "relationship")
    private String relationShip;

    @ApiModelProperty("正则表达式")
    @TableField(value = "rexpression")
    private String rexpression;

    @ApiModelProperty("用例id")
    @TableField(value = "case_id")
    private String caseId;
}
