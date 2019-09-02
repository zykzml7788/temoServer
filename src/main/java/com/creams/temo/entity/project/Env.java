package com.creams.temo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 环境
 */
@Data
@TableName(value = "env")
public class Env {

    @ApiModelProperty("项目主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("环境id,不必传")
    @TableField(value = "env_id")
    private String envId;

    @ApiModelProperty("环境名称")
    @TableField(value = "env_name")
    private String envName;

    @ApiModelProperty("域名")
    private String host;

    @ApiModelProperty("端口号")
    private Integer port;

    @ApiModelProperty("项目id,不必传")
    @TableField(value = "project_id")
    private String projectId;
}
