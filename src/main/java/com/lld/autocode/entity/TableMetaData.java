package com.lld.autocode.entity;

import lombok.Data;

@Data
public class TableMetaData {
    /**
     *  表字段信息
     * **/
    private String columnName;
    /**
     *  字段类型
     * **/
    private String dataType;
    /**
     * javaType
     * **/
    private String javaType;
    /**
     *  字段最大或者最小长度
     * **/
    private String characterMaximumLength;
    /**
     *  字段是否为空
     * **/
    private String nullAble;
    /**
     *  字段默认值
     * **/
    private String columnDefault;
    /**
     *  字段注释
     * **/
    private String columnComment;
    /**
     *  字段扩展信息
     * **/
    private String extra;
    /**
     *  字段索引
     * **/
    private String columnKey;
}
