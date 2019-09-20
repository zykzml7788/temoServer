package com.creams.temo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creams.temo.entity.project.Env;
import com.creams.temo.entity.project.Project;
import com.creams.temo.entity.project.response.EnvResponse;
import com.creams.temo.mapper.project.EnvMapper;
import com.creams.temo.mapper.project.ProjectMapper;
import com.creams.temo.service.project.ProjectService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TemoApplicationTests {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EnvMapper envMapper;

    @Autowired
    private ProjectService projectService;

    @Test
    public void contextLoads() {
        Project p = new Project();
        p.setPname("项目测试");
        int i =projectMapper.insert(p);
        System.out.println(i);
    }

    @Test
    public void test1() {
        List<Object> projectList = new ArrayList<>();
        QueryWrapper<Project> queryWrapper= new QueryWrapper<>();
        queryWrapper.lambda().eq(Project::getPid,"97523221-9318-426d-95f2-22b3addb0054");
        projectList = projectMapper.selectObjs(queryWrapper);
        System.out.println(projectList);
    }


    @Test
    public void test3(){
        List<EnvResponse> envList = new ArrayList<>();
        envList = envMapper.queryEnvByProjectId("97523221-9318-426d-95f2-22b3addb0054");
        System.out.println(envList);
    }

    @Test
    public void test4(){
        Project p = new Project();
        p.setPname("测试项目");
        Env e = new Env();
        e.setEnvName("测试环境");
        e.setHost("127.0.0.1");
        e.setPort(8080);
        p.setEnvs(Collections.singletonList(e));
        projectService.addProject(p);
    }


    @Test
    public void test5(){
//        System.out.println(new Gson().toJson(projectService.queryByName("测试")));
    }

    @Test
    public void test6(){
        System.out.println(new Gson().toJson(projectService.queryDetailById("97523221-9318-426d-95f2-22b3addb0054")));
    }

    @Test
    public void test7(){
        System.out.println(new Gson().toJson(projectService.delProjectById("97523221-9318-426d-95f2-22b3addb0054")));
    }

    @Test
    public void test8(){
        Project p = new Project();
        p.setPid("145f3340-215f-443f-8ab1-bf6251aaa300");
        p.setPname("我我哈哈哈");
        System.out.println(new Gson().toJson(projectService.updateProjectById("145f3340-215f-443f-8ab1-bf6251aaa300",p)));
    }

    @Test
    public void test9(){
        Project p = new Project();
        p.setPname("我我哈哈哈饿呢吧");
        System.out.println(projectService.addProject(p));
    }
}
