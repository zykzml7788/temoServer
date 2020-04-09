package com.creams.temo.service.datastatistics;


import com.creams.temo.entity.datastatistics.response.TaskInfoResponse;
import com.creams.temo.mapper.datastatistics.ExecuteTodayMapper;
import com.creams.temo.mapper.datastatistics.TasksInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TasksInfoService {
    @Autowired
    TasksInfoMapper tasksInfoMapper;

    /**
     * 查询任务库信息
     * @return
     */
    public TaskInfoResponse queryTasksInfo(){
        TaskInfoResponse taskInfoResponse = new TaskInfoResponse();
        taskInfoResponse.setTaskNum(tasksInfoMapper.queryTaskNum());
        taskInfoResponse.setTaskIsTimingNum(tasksInfoMapper.queryTaskIsTimingNum());
        return taskInfoResponse;
    }


    /**
     * 查询任务执行信息
     * @return
     */
    public TaskInfoResponse queryTasksExecuteInfo(){
        TaskInfoResponse taskInfoResponse = new TaskInfoResponse();
        taskInfoResponse.setTaskIsEndNum(tasksInfoMapper.queryTaskStatusIsEnd());
        taskInfoResponse.setTaskIsStartNum(tasksInfoMapper.queryTaskStatusIsStart());
        return taskInfoResponse;
    }
}
