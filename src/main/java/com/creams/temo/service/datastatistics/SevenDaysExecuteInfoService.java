package com.creams.temo.service.datastatistics;

import com.creams.temo.entity.datastatistics.response.ExecuteSevenDaysResponse;
import com.creams.temo.mapper.datastatistics.SevenDaysExecuteInfoMapper;
import com.creams.temo.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SevenDaysExecuteInfoService {

    @Autowired
    SevenDaysExecuteInfoMapper sevenDaysExecuteInfoMapper;

    /**
     * 查询近7日用例执行情况
     * @return
     */
    public List<ExecuteSevenDaysResponse> querySevenDaysExecuteInfo(){
        List<ExecuteSevenDaysResponse> list = sevenDaysExecuteInfoMapper.querySevenDaysTestCaseSuccessNum();
        if (list.size()<7){
            List<String> lists = new ArrayList<>();
            for (int i=0; i<list.size(); i++){
                lists.add(list.get(i).getDays());
            }
            lists = DateUtil.dateDifference(lists);

            for (int i = 0; i <lists.size() ; i++) {
                ExecuteSevenDaysResponse executeSevenDaysResponse = new ExecuteSevenDaysResponse();
                executeSevenDaysResponse.setDays(lists.get(i));
                executeSevenDaysResponse.setFalseNum(0);
                executeSevenDaysResponse.setSuccessNum(0);
                executeSevenDaysResponse.setSuccessRate("0");
                list.add(executeSevenDaysResponse);
            }
        }
        return list;
    }
}
