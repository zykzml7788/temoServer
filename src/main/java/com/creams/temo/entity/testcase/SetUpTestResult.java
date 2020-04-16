package com.creams.temo.entity.testcase;

import lombok.Builder;
import lombok.Data;

/**
 * 前置脚本结果
 */

@Data
@Builder
public class SetUpTestResult {

    /**
     * 类型
     */
    private String type;

    /**
     * 脚本id
     */
    private String scriptId;
    /**
     * 脚本名称
     */
    private String scriptName;

    /**
     * 是否成功
     */
    private Boolean success;


}
