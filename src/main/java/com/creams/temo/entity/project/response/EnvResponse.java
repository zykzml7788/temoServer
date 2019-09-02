package com.creams.temo.entity.project.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * 环境返回
 */
@Data
public class EnvResponse {


    @TableField(value = "env_id")
    private String envId;

    @TableField(value = "env_name")
    private String envName;

    private String host;

    private Integer port;

}
