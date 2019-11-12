package com.creams.temo.service.database;


import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.DatabaseResponse;
import com.creams.temo.entity.database.response.ScriptDbResponse;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.entity.project.response.ProjectResponse;
import com.creams.temo.mapper.database.DatabaseMapper;
import com.creams.temo.mapper.database.ScriptMapper;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageInfo;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScriptService {

    @Autowired
    private ScriptMapper scriptMapper;

    @Autowired
    private DatabaseMapper databaseMapper;

    /**
     * 查询所有Script
     * @return
     */
    @Transactional
    public PageInfo<ScriptResponse> queryScriptByNameAndDbId(Integer page,String dbId, String scriptName){
        //设置分页数据
        PageHelper.startPage(page, 10);
        List<ScriptResponse> scriptResponsesList = scriptMapper.queryAllScript(dbId, scriptName);
        PageInfo<ScriptResponse> pageInfo = new PageInfo<>(scriptResponsesList);
        return pageInfo;
    }

    /***
     * 根据dbId和scriptName获取对应脚本所属数据库
     * @param dbId
     * @param scriptName
     * @return
     */

    @Transactional
    public PageInfo<ScriptDbResponse> queryScriptDbByNameAndDbId(Integer page, String dbId, String scriptName){
        PageHelper.startPage(page, 10);
        List<ScriptDbResponse> scriptDbResponses = new ArrayList<>();
        List<ScriptResponse> scriptResponse = scriptMapper.queryAllScript(dbId, scriptName);

        if (scriptResponse.size()>0){
            for (int i=0; i<scriptResponse.size(); i++){
                DatabaseResponse databaseResponse = databaseMapper.queryDatabaseById(scriptResponse.get(0).getDbId());
                ScriptDbResponse scriptDbResponse = new ScriptDbResponse();
                scriptDbResponse.setSqlScript(scriptResponse.get(i).getSqlScript());
                scriptDbResponse.setScriptId(scriptResponse.get(i).getScriptId());
                scriptDbResponse.setScriptName(scriptResponse.get(i).getScriptName());
                scriptDbResponse.setId(scriptResponse.get(i).getId());
                scriptDbResponse.setCreateTime(scriptResponse.get(i).getCreateTime());
                scriptDbResponse.setUpdateTime(scriptResponse.get(i).getUpdateTime());
                scriptDbResponse.setDb(databaseResponse);
                scriptDbResponses.add(scriptDbResponse);
            }
        }
        PageInfo<ScriptDbResponse> pageInfo = new PageInfo<>(scriptDbResponses);
        return pageInfo;
    }

    /**
     * 查询Script详情
     * @param scriptId
     * @return
     */
    @Transactional
    public ScriptResponse queryScriptById(String scriptId){

        ScriptResponse scriptResponse = scriptMapper.queryScriptById(scriptId);
        return scriptResponse;
    }

    /**
     * 获取envId下的所有脚本
     * @param envId
     * @return
     */
    @Transactional
    public PageInfo<ScriptResponse> queryScriptByEnvId(Integer page, String envId){
        //设置分页数据
        PageHelper.startPage(page, 10);
        List<ScriptResponse> scriptResponseList = scriptMapper.queryScriptByEnvId(envId);
        PageInfo<ScriptResponse> pageInfo = new PageInfo<>(scriptResponseList);
        return pageInfo;
    }

    /**
     * 新增Script
     * @param scriptRequest
     * @return
     */
    @Transactional
    public String addScript(ScriptRequest scriptRequest){

        String scriptId = StringUtil.uuid();
        scriptRequest.setScriptId(scriptId);
        scriptMapper.addScript(scriptRequest);
        return scriptId;
    }

    /**
     * 修改Script
     * @param scriptRequest
     * @return
     */
    @Transactional
    public boolean updateScriptById(ScriptRequest scriptRequest){
        boolean result = true;
        scriptMapper.updateScriptById(scriptRequest);
        return result;

    }

    /**
     * 删除Script信息
     * @param scriptId
     * @return
     */
    @Transactional
    public boolean deleteScriptById(String scriptId){
        boolean result = true;
        scriptMapper.deleteScriptById(scriptId);
        return result;
    }

}
