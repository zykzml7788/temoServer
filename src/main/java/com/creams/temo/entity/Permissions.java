package com.creams.temo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName(value = "permissions")
public class Permissions {

    @ApiModelProperty("项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("角色id")
    @TableField(value = "role_id")
    private String roleId;

    @ApiModelProperty("权限id")
    @TableField(value = "permissions_id")
    private String permissionsId;

    @ApiModelProperty("权限名称")
    @TableField(value = "permissions_name")
    private String permissionsName;
}
