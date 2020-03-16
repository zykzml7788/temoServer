package com.creams.temo.entity.sys.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginRequest {


    @ApiModelProperty("用户名称")
    @TableField(value = "user_name")
    private String userName;

    @ApiModelProperty("用户密码")
    @TableField(value = "password")
    private String password;

}
