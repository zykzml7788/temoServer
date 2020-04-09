package com.creams.temo.enums;


public enum ScriptType {

    // 用例集脚本
    SET_SCRIPT("SET"),

    // 数据库脚本
    DB_SCRIPT("DB"),
    ;

    private String type;

    ScriptType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
