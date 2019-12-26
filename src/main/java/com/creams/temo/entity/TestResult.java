package com.creams.temo.entity;

import lombok.Data;

@Data
public class TestResult {
    // 已执行用例数
    private Integer executedNum;
    // 成功数
    private Integer success;
    // 失败数
    private Integer error;
    // 用例总数
    private Integer caseNum;
    // 成功率
    private String successRate;
    // 执行进度百分比
    private String executedRate;

    public TestResult(Integer executedNum, Integer success, Integer error, Integer caseNum, String successRate, String executedRate) {
        this.executedNum = executedNum;
        this.success = success;
        this.error = error;
        this.caseNum = caseNum;
        this.successRate = successRate;
        this.executedRate = executedRate;
    }
}
