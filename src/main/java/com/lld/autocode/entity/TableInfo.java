package com.lld.autocode.entity;

import lombok.Data;

@Data
public class TableInfo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * 表注释
     */
    private String tableComment;

}
