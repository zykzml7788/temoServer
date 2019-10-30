package com.creams.temo.entity.database;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "script")
public class Script {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("脚本id")
    @TableField(value = "script_id")
    private String scriptId;

    @ApiModelProperty("脚本名称")
    @TableField(value = "script_name")
    private String scriptName;

    @ApiModelProperty("数据库id")
    @TableField(value = "db_id")
    private String dbId;

    @ApiModelProperty("sql脚本")
    @TableField(value = "sql_script")
    private String sqlScript;


    @ApiModelProperty("创建时间")
    @TableField(value = "create_time")
    private String createTime;

    @ApiModelProperty("修改时间")
    @TableField(value = "update_time")
    private String updateTime;





}
