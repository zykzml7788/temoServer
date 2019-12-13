package com.creams.temo.entity.testcase;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.creams.temo.entity.testcase.request.SavesRequest;
import com.creams.temo.entity.testcase.request.VerifyRequest;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "testcase")
public class TestCase {

    @ApiModelProperty("项目主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用例id")
    @TableField(value = "case_id")
    private String caseID;


    @ApiModelProperty("用例描述")
    @TableField(value = "case_desc")
    private String caseDesc;

    @ApiModelProperty("接口地址")
    @TableField(value = "url")
    private String url;

    @ApiModelProperty("请求方式")
    @TableField(value = "method")
    private String method;

    @ApiModelProperty("请求头")
    @TableField(value = "header")
    private String header;


    @ApiModelProperty("请求cookies")
    @TableField(value = "cookies")
    private String cookies;

    @ApiModelProperty("请求参数")
    @TableField(value = "param")
    private String param;

    @ApiModelProperty("请求体")
    @TableField(value = "body")
    private String body;

    @ApiModelProperty("请求文件url")
    @TableField(value = "file")
    private String file;

    @ApiModelProperty("集合id")
    @TableField(value = "set_id")
    private String setId;

    @ApiModelProperty("sql脚本")
    @TableField(value = "sql_script")
    private String sqlScript;

    @ApiModelProperty("数据库id")
    @TableField(value = "db_id")
    private String dbId;

    @ApiModelProperty("用例类型")
    @TableField(value = "case_type")
    private String caseType;

    @ApiModelProperty("全局请求头")
    @TableField(value = "global_headers")
    private String globalHeaders;

    @ApiModelProperty("全局Cookies")
    @TableField(value = "global_cookies")
    private String globalCookies;

    @ApiModelProperty("延迟时间")
    @TableField(value = "delay_time")
    private String delayTime;

    @ApiModelProperty("JSON断言")
    @TableField(value = "json_assert")
    private String  jsonAssert;

    @ApiModelProperty("排序")
    @TableField(value = "sorting")
    private Integer sorting;

    private List<VerifyRequest> verify;

    private List<SavesRequest> saves;

}
