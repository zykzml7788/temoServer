package com.creams.temo.mapper.task;

import com.creams.temo.entity.task.TaskResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TaskResultMapper {


    boolean addTaskResult(TaskResult taskResult);

    boolean updateTaskResult(TaskResult taskResult);

    List<TaskResult>  queryTaskResults(String taskName,@Param(value = "status") Integer status);

    TaskResult queryTaskResult(String taskResultId);

}
