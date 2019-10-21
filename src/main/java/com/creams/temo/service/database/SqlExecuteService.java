package com.creams.temo.service.database;

import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.mapper.database.SqlExecuteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SqlExecuteService {

    @Autowired
    private SqlExecuteMapper sqlExecuteMapper;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * String的sql脚本，用$符号进行分割
     * @param scriptRequest
     * @return
     */
    public boolean sqlExecute(ScriptRequest scriptRequest) {
        String result = scriptRequest.getSqlScript();
        System.out.println("打印result" + result);
        if (result != null) {
            String[] resultList = result.split("\\$");
            for (String sql : resultList) {
                sqlExecuteMapper.select(sql);
            }
            return true;
        }else {
            return false;
        }

    }
}
