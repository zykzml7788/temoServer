package com.creams.temo.mapper.datastatistics;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestCaseSetInfoMaper {

    /**
     * 查询用例集启用数量
     * @return
     */
    @Select("SELECT count(1) FROM testcase_set where valid=1")
    Integer queryTestCaseSetNum();

    /**
     * 查询用例集启用关联用例数量
     * @return
     */
    @Select("SELECT COUNT(1) FROM testcase_set m LEFT JOIN testcase n on m.set_id=n.set_id WHERE m.valid=1")
    Integer queryTestCaseNum();

}
