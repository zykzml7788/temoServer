package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.request.TestCaseRequest;
import com.creams.temo.entity.testcase.response.TestCaseResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TestCaseMapper {

    List<TestCaseResponse> queryTestCase(@Param("case_id") String caseId,
                                         @Param("env_id") String envId,
                                         @Param("set_id")  String setId,
                                         @Param("case_desc")  String caseDesc,
                                         @Param("db_id") String dbId,
                                         @Param("case_type") String caseType);

    boolean addTestCase(TestCaseRequest testCaseRequest);

    boolean updateTestCaseById(TestCaseRequest testCaseRequest);

    @Delete("delete from testcase where case_id = #{case_id}")
    boolean deleteTestCase(@Param("case_id") String caseId);


}