package com.creams.temo.mapper.datastatistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TasksInfoMapper {

    @Select("SELECT count(1) FROM task")
    Integer queryTaskNum();

    @Select("SELECT count(1) FROM task where is_open=1")
    Integer queryTaskIsTimingNum();

    @Select("SELECT COUNT(task_id) FROM task_result WHERE `status`=1")
    Integer queryTaskStatusIsEnd();

    @Select("SELECT COUNT(task_id) FROM task_result WHERE `status`=2")
    Integer queryTaskStatusIsStart();

}
