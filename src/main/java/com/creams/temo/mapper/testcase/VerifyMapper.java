package com.creams.temo.mapper.testcase;

import com.creams.temo.entity.testcase.request.VerifyRequest;
import com.creams.temo.entity.testcase.response.VerifyResponse;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VerifyMapper {

    List<VerifyResponse> queryVerify(@Param("case_id") String caseId);

    boolean addVerify(VerifyRequest verifyRequest);

    boolean updateVerifyById(VerifyRequest verifyRequest);

    @Delete("delete from verify where case_id = #{case_id}")
    boolean deleteVerify(@Param("case_id") String caseId);
}
