package com.creams.temo.service.project;

import com.creams.temo.entity.project.Database;
import com.creams.temo.entity.project.request.DatabaseRequest;
import com.creams.temo.entity.project.response.DatabaseResponse;
import com.creams.temo.mapper.project.DatabaseMapper;
import com.creams.temo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private DatabaseMapper databaseMapper;

    /**
     * 查询所有Database
     * @return
     */
    public List<DatabaseResponse> queryAllDatabase(){
        List<DatabaseResponse> databaseResponses = databaseMapper.queryDatabase();
        return databaseResponses;
    }

    /**
     * 查询database详情
     * @param dbId
     * @return
     */
    public Database queryDatabaseById(int dbId){

        Database database = databaseMapper.queryDatabaseById(dbId);
        return database;
    }

    /**
     * 新增database
     * @param databaseRequest
     * @return
     */
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
    public boolean deleteDabaseById(int dbId){
        boolean result = true;
        databaseMapper.deteleDatabaseById(dbId);
        return result;
    }

}
