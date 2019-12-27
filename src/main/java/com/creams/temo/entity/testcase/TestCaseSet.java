package com.creams.temo.entity.testcase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "testcase_set")
public class TestCaseSet {

    @ApiModelProperty("项目主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用例集id")
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

    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private String createTime;

    @ApiModelProperty("用例集状态(0-待完成、1待维护、2-已完成)")
    @TableField(value = "set_status")
    private String setStatus;


    @ApiModelProperty("是否启用")
    @TableField(value = "valid")
    private String valid;


}
