package com.creams.temo.service.database;

import com.creams.temo.entity.database.Database;
import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.DatabaseResponse;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.mapper.database.DatabaseMapper;
import com.creams.temo.mapper.database.SqlExecuteMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class SqlExecuteService {
    private static Logger logger = LoggerFactory.getLogger("fileInfoLog");

    @Autowired
    private  DatabaseMapper databaseMapper;

    private  DriverManagerDataSource getDataSource(String dbId){
        DatabaseResponse databaseInfo = databaseMapper.queryDatabaseById(dbId);
        // 构建数据库实例
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl(String.format("jdbc:mysql://%s:%d/%s", databaseInfo.getHost(),databaseInfo.getPort(),
                databaseInfo.getDbLibraryName()));
        dataSource.setUsername(databaseInfo.getUser());
        dataSource.setPassword(databaseInfo.getPwd());
        return dataSource;
    }


    /**
     * String的sql脚本，用$符号进行分割
     * @param scriptRequest
     * @return
     */
    public Map sqlExecute(ScriptRequest scriptRequest) {
        //1.拿到数据库实例
        DriverManagerDataSource dataSource = getDataSource(scriptRequest.getDbId());
        //2. 创建jdbctemplate 实例
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //3. 遍历sql列表，执行sql
        Integer total = scriptRequest.getSqlScript().split("\\$").length;
        int error = 0;
        List<Map<String,Object>> errorList = new LinkedList<>();

        for(String sql:scriptRequest.getSqlScript().split("\\$")){
            // 捕获sql执行，发生异常时，error数+1
            try{
                jdbcTemplate.execute(sql);
                logger.info("sql=====>"+sql+" 执行成功");
            }catch (Exception e){
                logger.error("sql=====>"+sql+" 执行异常！错误原因："+e);
                error = error+1;
                Map<String,Object> errorDetail = new HashMap<>();
                errorDetail.put("sql", sql);
                errorDetail.put("errMsg", e.getMessage());
                errorList.add(errorDetail);
            }
        }
        Map<String,Object> executeResult = new HashMap<>();
        executeResult.put("total", total);
        executeResult.put("success", total-error);
        executeResult.put("error",error);
        executeResult.put("errorList", errorList);
        return executeResult;
    }
}
