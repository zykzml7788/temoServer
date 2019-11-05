package com.creams.temo.entity.database.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author jelly
 * @since 1.0
 */
@Data
@TableName(value = "db")
public class DatabaseResponse {

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

    @ApiModelProperty("更新时间")
    @TableField(value = "updatetime")
    private String updatetime;

    @ApiModelProperty("创建时间")
    @TableField(value = "createtime")
    private String createtime;
}
