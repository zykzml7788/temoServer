package com.creams.temo.service.database;


import com.creams.temo.entity.database.request.ScriptRequest;
import com.creams.temo.entity.database.response.ScriptResponse;
import com.creams.temo.entity.project.response.ProjectResponse;
import com.creams.temo.mapper.database.ScriptMapper;
import com.creams.temo.util.StringUtil;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.github.pagehelper.PageInfo;

import java.util.List;

@Service
public class ScriptService {

    @Autowired
    private ScriptMapper scriptMapper;

    /**
     * 查询所有Script
     * @return
     */
    @Transactional
    public PageInfo<ScriptResponse> queryScriptByNameAndDbId(Integer page, String scriptName){
        //设置分页数据
        PageHelper.startPage(page, 10);
        List<ScriptResponse> scriptResponsesList = scriptMapper.queryAllScript(scriptName);
        PageInfo<ScriptResponse> pageInfo = new PageInfo<>(scriptResponsesList);
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
