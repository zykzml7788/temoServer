package com.creams.temo.entity.testcase;

import lombok.Data;

/**
 * 用例执行记录实体
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
     * 执行顺序
     */
    private Integer index;

    /**
     *  类型
     */
    private String  type;





    public ExecutedRow(String type,Integer index, String caseName, Integer status, String logs) {
        this.type = type;
        this.logs = logs;
        this.status = status;
        this.caseName = caseName;
        this.index = index;
    }


}
