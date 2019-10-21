package com.creams.temo.mapper.database;

import org.apache.ibatis.annotations.Mapper;

import java.util.LinkedHashMap;
import java.util.List;

@Mapper
public interface SqlExecuteMapper {
    List<LinkedHashMap<String, Object>> select(String sql);
    int insert(String sql);
    int update(String sql);
    int delete(String sql);

}
