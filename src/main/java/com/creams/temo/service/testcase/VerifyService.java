package com.creams.temo.service.testcase;


import com.creams.temo.entity.testcase.response.VerifyResponse;
import com.creams.temo.mapper.testcase.VerifyMapper;
import com.creams.temo.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VerifyService {

    @Autowired
    VerifyMapper verifyMapper;

    /**
     * 新增用例断言
     * @param verifyResponse
     * @return
     */
    @Transactional
    public String addVerify(VerifyResponse verifyResponse){
        String verifyId = StringUtil.uuid();
        verifyResponse.setVerifyId(verifyId);
        verifyMapper.addVerify(verifyResponse);
        return verifyId;
    }
}
