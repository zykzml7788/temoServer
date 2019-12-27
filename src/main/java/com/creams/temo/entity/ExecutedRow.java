package com.creams.temo.entity;

import lombok.Data;

/**
 * 执行记录实体
 */
@Data
public class ExecutedRow {

    /**
     * 执行日志
     */
    private String logs;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 用例名称
     */
    private String caseName;
    /**
     * 用例id
     */
    private String caseId;

    /**
     * 执行顺序
     */
    private Integer index;



    public ExecutedRow(Integer index,String caseId, String caseName, Integer status, String logs) {
        this.logs = logs;
        this.status = status;
        this.caseId = caseId;
        this.caseName = caseName;
        this.index = index;
    }
}
