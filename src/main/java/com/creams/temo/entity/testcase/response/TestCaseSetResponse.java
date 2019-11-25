package com.creams.temo.entity.testcase.response;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TestCaseSetResponse {

    @ApiModelProperty(value = "用例集id")
    @TableField(value = "set_id")
    private String setId;

    @ApiModelProperty("用例集名字")
    @TableField(value = "set_name")
    private String setName;

    @ApiModelProperty("用例集描述")
    @TableField(value = "set_desc")
    private String setDesc;

    @ApiModelProperty("项目id")
    @TableField(value = "project_id")
    private String projectId;

    @ApiModelProperty(value = "创建时间", hidden = true)
    @TableField(value = "create_time")
    private String createTime;

}