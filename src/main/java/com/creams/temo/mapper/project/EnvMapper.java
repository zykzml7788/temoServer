package com.creams.temo.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creams.temo.entity.project.Env;
import com.creams.temo.entity.project.response.EnvResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EnvMapper extends BaseMapper<Env> {

    @Select("select env_id,env_name,host,port from env where project_id = #{projectId}")
    List<EnvResponse> queryEnvByProjectId(String projectId);

    @Select("select env_id,env_name,host,port from env_id = #{envId}")
    EnvResponse queryEnvById(String envId);
}
