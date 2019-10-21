package com.creams.temo.entity.database.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DatabaseRequest {

    @ApiModelProperty(value = "数据库id",hidden = true)
    @TableField(value = "db_id")

    private String dbId;

    @ApiModelProperty("数据库名称")
    @TableField(value = "db_name")
    private String dbName;

    @ApiModelProperty("主机Ip")
    @TableField(value = "host")
    private String host;

    @ApiModelProperty("端口号")
    @TableField(value = "port")
    private Integer port;

    @ApiModelProperty("用户名")
    @TableField(value = "user")
    private String user;

    @ApiModelProperty("密码")
    @TableField(value = "pwd")
    private String pwd;

    @ApiModelProperty("数据库名称")
    @TableField(value = "db_library_name")
    private String dbLibraryName;

}
