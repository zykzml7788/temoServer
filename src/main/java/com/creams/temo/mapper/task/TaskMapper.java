package com.creams.temo.mapper.task;

import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TaskMapper {


    List<TaskResponse> queryTasks(String taskName,Integer status);

    @Select("select * from task where task_id = #{taskId}")
    TaskResponse queryTaskDetail(String taskId);

    boolean addTask(TaskRequest taskRequest);

    boolean updateTaskById(TaskRequest taskRequest);

    @Delete("delete from task where task_id = #{task_id}")
    boolean deleteTask(@Param("task_id") String taskId);
}
