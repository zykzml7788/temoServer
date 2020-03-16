package com.creams.temo.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class UserEntity implements Serializable {

    @ApiModelProperty("项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
