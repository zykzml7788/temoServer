package com.creams.temo.entity.dingding;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class DingdingEntity {

    @ApiModelProperty(value = "项目主键", hidden = true)
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "描述id")
    @TableField(value = "desc_id")
    private String descId;

    @ApiModelProperty("描述")
    @TableField(value = "desc")
    private String desc;

    @ApiModelProperty("关键字")
    @TableField(value = "webhook")
    private String webhook;

    @ApiModelProperty("关键字")
    @TableField(value = "keysword")
    private String keysWord;

    @ApiModelProperty(value = "创建人", hidden = true)
    private String creater;

}
