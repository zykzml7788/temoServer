package com.creams.temo.service.database;


import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.DatabaseResponse;
import com.creams.temo.entity.database.response.ScriptDbResponse;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.entity.project.response.ProjectResponse;
import com.creams.temo.mapper.database.DatabaseMapper;
import com.creams.temo.mapper.database.ScriptMapper;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.Page;
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
    public List<ScriptResponse> queryAllScript(){
        return scriptMapper.queryAllScript();
    }

    /***
     * 根据dbId和scriptName获取对应脚本所属数据库
     * @param dbId
     * @param scriptName
     * @return
     */

    public PageInfo<ScriptDbResponse>   queryScriptDbByNameAndDbId(Integer page, String dbId, String scriptName){
        PageHelper.startPage(page, 10);
        List<ScriptDbResponse> scriptResponses = scriptMapper.queryScriptDb(dbId, scriptName);
        PageInfo<ScriptDbResponse> pageInfo= new PageInfo<>(scriptResponses);
        pageInfo.getList().forEach(n->n.setDb(databaseMapper.queryDatabaseById(n.getDbId())));
        return new PageInfo<>(scriptResponses);
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
    @Transactional(rollbackFor = Exception.class)
    public boolean updateScriptById(ScriptRequest scriptRequest){
        boolean result;
        result = scriptMapper.updateScriptById(scriptRequest);
        if (result){
            return true;
        }
        return false;
    }

    /**
     * 删除Script信息
     * @param scriptId
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteScriptById(String scriptId){
        boolean result;
        result = scriptMapper.deleteScriptById(scriptId);
        if (result){
            return true;
        }
        return false;
    }
}
