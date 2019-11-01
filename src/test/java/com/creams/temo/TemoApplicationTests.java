package com.creams.temo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.entity.project.Env;
import com.creams.temo.entity.project.Project;
import com.creams.temo.entity.project.response.EnvResponse;
import com.creams.temo.mapper.project.EnvMapper;
import com.creams.temo.mapper.project.ProjectMapper;
import com.creams.temo.mapper.database.SqlExecuteMapper;
import com.creams.temo.service.project.ProjectService;
import com.creams.temo.service.database.SqlExecuteService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TemoApplicationTests {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EnvMapper envMapper;

    @Autowired
    private SqlExecuteMapper SqlExecuteMapper;

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

   @Test
    public void test10(){

        String select ="SELECT * FROM `temo-server`.script LIMIT 5;$UPDATE " +
                "`temo-server`.`script` SET `db_id`='db_id_testaaaaa' WHERE (`id`='30');" +
                "$UPDATE `temo-server`.`script` SET `db_id`='db_id_testbbbbb' WHERE (`id`='29');";
        String[] resultList = select.split("\\$");
        for (String sql : resultList) {
            List<LinkedHashMap<String, Object>> result = SqlExecuteMapper.select(sql);
            System.out.println(result);
       }

//        String insert = "INSERT INTO `temo-server`.`script` (`script_id`, `script_name`, `db_id`, `sql_script`," +
//                " `env_id`, `create_time`, `update_time`) VALUES ('script_id_test', 'script_name_test', 'db_id_test', " +
//                "'11222333444', 'a9ec1db6-e906-4db8-900f-b30b3ffbea99', NULL, NULL);";
//        SqlExecuteMapper.insert(insert);
//        String update = "UPDATE `temo-server`.`script` SET  `script_id`='script_id_test',`sql_script`='ceshi+sql_script'=NULL WHERE (`id`='30');";
//        SqlExecuteMapper.update(update);
//        String delete = "DELETE FROM `temo-server`.script WHERE id = '20';";
//        SqlExecuteMapper.delete(delete);
   }
}
