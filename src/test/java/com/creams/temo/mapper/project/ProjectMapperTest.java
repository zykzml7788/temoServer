package com.creams.temo.mapper.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProjectMapperTest {

    @Autowired
    private ProjectMapper projectMapper;

//    @Test
//    public void queryProjectById() {
//        projectMapper.queryProjectById("123");
//    }
//
//    @Test
//    public void queryProjectByName() {
//        projectMapper.deleteById(1);
//    }
}