package com.creams.temo.entity.testcase.request;

import lombok.Data;

import java.util.List;

/**
 * 新增前后置脚本实体类
 */
@Data
public class StScriptRequests {

    String setId;

    List<StScriptRequest> stScriptRequests;
}
