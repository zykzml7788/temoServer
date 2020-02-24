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
     * 用例id
     */
    private String caseId;


    /**
     * 用例名称
     */
    private String caseName;

    /**
     * 执行顺序
     */
    private Integer index;

    /**
     * 集合id
     */
    private String setId;


    /**
     * 执行的唯一标识(执行一次用例集就加1)
     */
    private Integer indexOfExecuted;

    public ExecutedRow(String setId,String caseId,Integer indexOfExecuted,Integer index, String caseName, Integer status, String logs) {
        this.setId = setId;
        this.indexOfExecuted = indexOfExecuted;
        this.logs = logs;
        this.status = status;
        this.caseName = caseName;
        this.index = index;
        this.caseId = caseId;
    }


}
