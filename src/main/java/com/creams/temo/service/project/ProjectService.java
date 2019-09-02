package com.creams.temo.service.project;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creams.temo.entity.project.Env;
import com.creams.temo.entity.project.Project;
import com.creams.temo.entity.project.response.ProjectResponse;
import com.creams.temo.mapper.project.EnvMapper;
import com.creams.temo.mapper.project.ProjectMapper;
import com.creams.temo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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
    public List<ProjectResponse> queryByName(String name){

        List<ProjectResponse> projects = projectMapper.queryProjectByName(name);
        projects.forEach(p->p.setEnvs(envMapper.queryEnvByProjectId(p.getPid())));
        return projects;
    }

    /**
     * 查看项目详情
     * @param projectId
     * @return
     */
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
    public Integer updateProjectById(String projectId,Project project){
        int i = projectMapper.update(project,new QueryWrapper<Project>().lambda().eq(Project::getPid,projectId));
        return i;
    }

}
