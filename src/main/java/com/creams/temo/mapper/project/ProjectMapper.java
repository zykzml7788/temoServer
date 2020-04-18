package com.creams.temo.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.creams.temo.entity.project.Project;
import com.creams.temo.entity.project.response.ProjectResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProjectMapper extends BaseMapper<Project> {


    @Select("select *from project where pid = #{pid}")
    ProjectResponse queryProjectById(@Param("pid") String pid);

    @Select("select * from project")
    List<ProjectResponse> queryAllProject();

    List<ProjectResponse> queryProjectByName(@Param("pname") String name);


}
