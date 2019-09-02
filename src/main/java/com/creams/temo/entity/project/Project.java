package com.creams.temo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

/**
 * 项目
 * @author zhangyingkai
 */
@Data
@TableName(value = "project")
public class Project {

    @ApiModelProperty("项目主键")
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("项目名称")
    @TableField(value = "pname")
    private String pname;

    @ApiModelProperty("项目id")
    @TableField(value = "pid")
    private String pid;

    @ApiModelProperty("项目环境")
    @TableField(exist = false)
    private List<Env> envs;
}
