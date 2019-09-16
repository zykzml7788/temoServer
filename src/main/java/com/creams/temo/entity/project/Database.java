package com.creams.temo.entity.project;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import java.sql.Timestamp;

@Data
@TableName(value = "db")
public class Database {


    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(value = "db_id")
    private String dbId;

    @TableField(value = "db_name")
    private String dbName;

    @TableField(value = "host")
    private String host;

    @TableField(value = "port")
    private Integer port;

    @TableField(value = "user")
    private String user;

    @TableField(value = "pwd")
    private String pwd;

    @TableField(value = "db_library_name")
    private String dbLibraryName;

    @TableField(value = "modifier")
    private String modifier;

    @TableField(value = "modify_time")
    private Timestamp modifyTime;

    @TableField(value = "creator")
    private String creator;

    @TableField(value = "create_time")
    private Timestamp createTime;

    public static void main(String[] args) {
        Database database = new Database();
        database.setDbId("123");
        System.out.println(database.getDbId());
    }

}
