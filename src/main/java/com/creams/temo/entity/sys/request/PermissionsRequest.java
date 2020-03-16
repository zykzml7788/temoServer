package com.creams.temo.entity.sys.request;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PermissionsRequest {

    @ApiModelProperty("角色id")
    @TableField(value = "role_id")
    private String roleId;

    @ApiModelProperty("权限id")
    @TableField(value = "permissions_id")
    private String permissionsId;

    @ApiModelProperty("权限名称")
    @TableField(value = "permissions_name")
    private String permissionsName;


    @ApiModelProperty("权限状态")
    @TableField(value = "status")
    private Integer status;
}
