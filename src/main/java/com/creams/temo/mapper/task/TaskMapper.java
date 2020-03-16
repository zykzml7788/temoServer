package com.creams.temo.mapper.task;

import com.creams.temo.entity.task.request.TaskRequest;
import com.creams.temo.entity.task.request.TimingTaskRequest;
import com.creams.temo.entity.task.response.TaskResponse;

import com.creams.temo.entity.task.response.TimingTaskResponse;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskMapper {


    List<TaskResponse> queryTasks(String taskName,String isParallel);

    @Select("select * from task where task_id = #{taskId}")
    TaskResponse queryTaskDetail(String taskId);

    boolean addTask(TaskRequest taskRequest);

    boolean updateTaskById(TaskRequest taskRequest);

    @Delete("delete from task where task_id = #{task_id}")
    boolean deleteTask(@Param("task_id") String taskId);

    boolean addTimingTask(TimingTaskRequest timingTaskRequest);

    boolean updateTimingTask(TimingTaskRequest timingTaskRequest);

    List<TimingTaskResponse> queryTimingTasks(String taskName, String isParallel);

    @Update("update task set is_open = #{isOpen} where task_id = #{taskId}")
    boolean updateTimingTaskStatus(String taskId,Integer isOpen);

    @Select("select * from task where task_id = #{taskId}")
    TimingTaskResponse queryTimingTaskDetail(String taskId);
}
