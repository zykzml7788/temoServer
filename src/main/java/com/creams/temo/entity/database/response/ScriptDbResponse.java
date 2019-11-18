package com.creams.temo.entity.database.response;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.creams.temo.entity.database.Database;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ScriptDbResponse {

    private Integer id;

    private String scriptId;

    private String scriptName;

    private DatabaseResponse Db;

    private String sqlScript;

    private String createTime;

    private String updateTime;

}
