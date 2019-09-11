package com.creams.temo.service.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creams.temo.entity.project.Env;
import com.creams.temo.entity.project.Project;
import com.creams.temo.entity.project.response.EnvResponse;
import com.creams.temo.entity.project.response.ProjectResponse;
import com.creams.temo.mapper.project.EnvMapper;
import com.creams.temo.mapper.project.ProjectMapper;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目业务层
 */

@Service
public class ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EnvMapper envMapper;

    /**
     * 新增项目及其环境
     * @param project
     * @return
     */
    @Transactional
    public String addProject(Project project){
        String projectId = StringUtil.uuid();
        project.setPid(projectId);
        projectMapper.insert(project);
        // 判断环境是否为空
        if (project.getEnvs()!=null) {
            project.getEnvs().forEach(e -> {
                e.setEnvId(StringUtil.uuid());
                e.setProjectId(project.getPid());
                e.setProjectId(projectId);
                envMapper.insert(e);
            });
        }
        return projectId;
    }

    /**
     * 模糊查询项目
     * @param name
     * @return
     */
    @Transactional
    public PageInfo<ProjectResponse> queryByName(Integer page, String name){
        PageHelper.startPage(page,10);
        List<ProjectResponse> projects = projectMapper.queryProjectByName(name);
        PageInfo<ProjectResponse> pageInfo = new PageInfo<>(projects);
        pageInfo.getList().forEach(p->p.setEnvs(envMapper.queryEnvByProjectId(p.getPid())));
        return pageInfo;
    }

    /**
     * 查看项目详情
     * @param projectId
     * @return
     */
    @Transactional
    public ProjectResponse queryDetailById(String projectId){
        ProjectResponse projectResponse = projectMapper.queryProjectById(projectId);
        projectResponse.setEnvs(envMapper.queryEnvByProjectId(projectId));
        return projectResponse;
    }

    /**
     * 删除项目
     * @param projectId
     * @return
     */
    @Transactional
    public Integer delProjectById(String projectId){
        int i = projectMapper.delete(new QueryWrapper<Project>().lambda().eq(Project::getPid, projectId));
        envMapper.delete(new QueryWrapper<Env>().lambda().eq(Env::getProjectId,projectId));
        return i;
    }

    /**
     * 编辑项目
     * @param projectId
     * @param project
     * @return
     */
    @Transactional
    public String updateProjectById(String projectId,Project project){
        String updateId = StringUtil.uuid();
        project.setPid(updateId);
        projectMapper.update(project,new QueryWrapper<Project>().lambda().eq(Project::getPid,projectId));
        List<String> envIds = new ArrayList<>();
        List<String> requestEnvIds = new ArrayList<>();
        List<EnvResponse> envResponses =  envMapper.queryEnvByProjectId(projectId);
        envResponses.forEach(n->envIds.add(n.getEnvId()));
        project.getEnvs().forEach(e->requestEnvIds.add(e.getEnvId()));
        for (Env env : project.getEnvs()){
            env.setProjectId(project.getPid());
            // 更新项目下的环境
            if (env.getEnvId() == null){
                env.setEnvId(StringUtil.uuid());
                envMapper.insert(env);
            }else {
                envMapper.update(env, new QueryWrapper<Env>().lambda().eq(Env::getEnvId, env.getEnvId()));
            }
        }
        for (String envId: envIds){
            if (requestEnvIds.indexOf(envId) < 0 ){
                envMapper.delete(new QueryWrapper<Env>().lambda().eq(Env::getEnvId, envId));
            }
        }

        return updateId;
    }

}
