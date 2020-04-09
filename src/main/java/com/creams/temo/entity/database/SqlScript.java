package com.creams.temo.entity.database;

import lombok.Data;

/**
 * sql实体
 */
@Data
public class SqlScript {

    private String script;

    private Boolean saveParam;
}
