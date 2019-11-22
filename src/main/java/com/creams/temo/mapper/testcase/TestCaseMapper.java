package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.request.TestCaseRequest;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestCaseMapper {

    List<TestCaseResponse> queryTestCase();

    boolean addTestCase(TestCaseRequest testCaseRequest);

    boolean updateTestCaseById(TestCaseRequest testCaseRequest);

    @Delete("delete from testcase_set where case_id = #{case_id}")
    boolean deleteTestCase(@Param("case_id") String caseId);


}