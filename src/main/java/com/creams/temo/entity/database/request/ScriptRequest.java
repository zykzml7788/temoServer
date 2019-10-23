package com.creams.temo.entity.database.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ScriptRequest {


    @ApiModelProperty(value = "脚本id")
    @TableField(value = "script_id")
    private String scriptId;

    @ApiModelProperty("脚本名称")
    @TableField(value = "script_name")
    private String scriptName;

    @ApiModelProperty(value = "数据库id")
    @TableField(value = "db_id")
    private String dbId;

    @ApiModelProperty("sql脚本")
    @TableField(value = "sql_script")
    private String sqlScript;


}
