package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.request.TestCaseRequest;
import com.creams.temo.entity.testcase.request.TestCaseSetRequest;
import com.creams.temo.entity.testcase.response.TestCaseSetResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestCaseSetMapper {

    List<TestCaseSetResponse> queryTestCaseSetByNameandId(@Param("set_name") String setName,
                                                          @Param("project_id")String projectId);

    List<TestCaseSetResponse> queryTestCaseSet(TestCaseSetRequest testCaseSetRequest);

    boolean addTestCaseSet(TestCaseSetRequest testCaseSetRequest);

    boolean updateTestCaseSetById(TestCaseSetRequest testCaseSetRequest);

    @Delete("delete from testcase_set where set_id = #{set_id}")
    boolean deleteTestCaseSetById(@Param("set_id") String setId);
}
