package com.creams.temo.service.testcase;

import com.creams.temo.entity.testcase.response.SavesResponse;
import com.creams.temo.mapper.testcase.SavesMapper;
import com.creams.temo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavesService {

    @Autowired
    SavesMapper savesMapper;

    /**
     * 新增用例关联参数
     * @param savesResponse
     * @return
     */
    @Transactional
    public String addSaves(SavesResponse savesResponse){
        String savesId = StringUtil.uuid();
        savesResponse.setSaveId(savesId);
        savesMapper.addSaves(savesResponse);
        return savesId;
    }
}
