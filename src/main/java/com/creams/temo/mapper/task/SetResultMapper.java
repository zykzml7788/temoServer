package com.creams.temo.mapper.task;

import com.creams.temo.entity.task.SetResult;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SetResultMapper {

    boolean addSetResult(SetResult setResult);

    List<SetResult> querySetResultsByTaskResultId(String taskResultId);
}
