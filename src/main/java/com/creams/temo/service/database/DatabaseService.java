package com.creams.temo.service.database;

import com.creams.temo.entity.database.request.DatabaseRequest;
import com.creams.temo.entity.database.response.DatabaseResponse;
import com.creams.temo.mapper.database.DatabaseMapper;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private DatabaseMapper databaseMapper;

    /**
     * 分页查询数据库配置信息
     * @return
     */
    public PageInfo<DatabaseResponse> queryDatabaseByName(Integer page, String name){
        PageHelper.startPage(page, 10);
        List<DatabaseResponse> databaseResponses = databaseMapper.queryDatabase(name);
        return new PageInfo<>(databaseResponses);
    }

    /**
     * 查询database详情
     * @param dbId
     * @return
     */
    public DatabaseResponse queryDatabaseById(String dbId){

        DatabaseResponse databaseResponse = databaseMapper.queryDatabaseById(dbId);
        return databaseResponse;
    }

    /**
     * 新增database
     * @param databaseRequest
     * @return
     */
    @Transactional
    public String addDatabase(DatabaseRequest databaseRequest){
        String dbId = StringUtil.uuid();
        databaseRequest.setDbId(dbId);
        databaseMapper.addDatabase(databaseRequest);
        return dbId;
    }

    /**
     * 修改database
     * @param databaseRequest
     * @return
     */
    @Transactional
    public boolean updateDatabaseById(DatabaseRequest databaseRequest){
        boolean result = true;
        databaseMapper.updateDatabaseById(databaseRequest);
        return result;

    }

    /**
     * 删除数据库信息
     * @param dbId
     * @return
     */
    @Transactional
    public boolean deleteDabaseById(String dbId){
        boolean result = true;
        databaseMapper.deteleDatabaseById(dbId);
        return result;
    }

}
