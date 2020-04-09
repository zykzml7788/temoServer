package com.creams.temo.entity.sys.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRequest {

    @ApiModelProperty("用户id")
    @TableField(value = "user_id")
    private String userId;

    @ApiModelProperty("用户名称")
    @TableField(value = "user_name")
    private String userName;

    @ApiModelProperty("用户密码")
    @TableField(value = "password")
    private String password;

    @ApiModelProperty("用户状态")
    @TableField(value = "status")
    private Integer status;
}
