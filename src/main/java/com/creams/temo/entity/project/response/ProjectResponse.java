package com.creams.temo.entity.project.response;

import com.creams.temo.entity.project.Env;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author Kevin
 * @since 1.0
 */
@Data
public class ProjectResponse {

    @ApiParam("项目名称")
    private String pname;

    @ApiParam("项目id")
    private String pid;

    @ApiParam("环境")
    private List<EnvResponse> envs;

    @ApiParam("创建时间")
    private Timestamp createTime;

    @ApiParam("修改时间")
    private Timestamp updateTime;
}
