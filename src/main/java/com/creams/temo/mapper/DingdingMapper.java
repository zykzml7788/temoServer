package com.creams.temo.mapper;


import com.creams.temo.entity.dingding.DingdingEntity;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DingdingMapper {

    @Select("select * from dingding")
    List<DingdingEntity> queryAllDingding();

    @Select("select * from dingding where desc_id= #{desc_id}")
    DingdingEntity queryDingdingBydescId(@Param("desc_id") String descId);

    List<DingdingEntity> queryDingding(String desc);

    boolean addDingding(DingdingEntity dingdingEntity);

    boolean updateDingding(DingdingEntity dingdingEntity);

    @Delete("delete from dingding where desc_id = #{desc_id}")
    boolean deleteDingding(@Param("desc_id") String descId);
}
