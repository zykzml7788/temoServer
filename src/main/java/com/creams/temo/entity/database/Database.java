package com.creams.temo.entity.database;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "db")
public class Database {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("数据库id")
    @TableField(value = "db_id")
    private String dbId;

    @ApiModelProperty("数据库名称")
    @TableField(value = "db_name")
    private String dbName;

    @ApiModelProperty("主机Ip")
    @TableField(value = "host")
    private String host;

    @ApiModelProperty("断口号")
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
