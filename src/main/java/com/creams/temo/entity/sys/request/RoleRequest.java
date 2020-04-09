package com.creams.temo.entity.sys.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleRequest {

    @ApiModelProperty("用户id")
    @TableField(value = "user_id")
    private String userId;

    @ApiModelProperty("角色id")
    @TableField(value = "role_id")
    private String roleId;


    @ApiModelProperty("角色名称")
    @TableField(value = "role_name")
    private String roleName;

    @ApiModelProperty("角色")
    @TableField(value = "status")
    private Integer status;
}
