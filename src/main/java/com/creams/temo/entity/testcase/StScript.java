package com.creams.temo.entity.testcase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "st_script")
public class StScript {

    @ApiModelProperty("项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("前后置脚本id")
    @TableField(value = "st_script_id")
    private String stScriptId;


    @ApiModelProperty("前后置脚本类别")
    @TableField(value = "st_script_type")
    private String stScriptType;

    @ApiModelProperty("用例集id")
    @TableField(value = "set_id")
    private String setId;

    @ApiModelProperty("关联脚本id")
    @TableField(value = "ex_script_id")
    private String exScriptId;

    @ApiModelProperty("关联脚本类型(脚本0，集合1)")
    @TableField(value = "ex_script_type")
    private  String exScriptType;
}
