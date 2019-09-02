package com.creams.temo.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JsonResult {

    @ApiModelProperty("信息")
    private String msg;
    @ApiModelProperty("code码")
    private Integer code;
    @ApiModelProperty("数据")
    private Object data;
    @ApiModelProperty("是否成功")
    private Boolean success;


    public JsonResult(String msg, Integer code, Object data, Boolean success){
        this.msg = msg;
        this.code = code;
        this.data = data;
        this.success = success;
    }

}
