package com.creams.temo.mapper.datastatistics;

import com.creams.temo.entity.task.response.TaskResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ExecuteTimeInfoMapper {

        /**
         * 查询平台运行时间之和：秒
         * @return
         */
        @Select("SELECT SUM(TIMEDIFF(end_time,start_time)) FROM task_result")
        Integer queryExecuteTime();

        /**
         * 正在执行任务
         * @return
         */
        @Select("SELECT COUNT(DISTINCT task_id) FROM task_result WHERE  `status`=1 ")
        Integer queryExecuteTaskNumNow();


}
