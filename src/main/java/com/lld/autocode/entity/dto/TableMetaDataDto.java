package com.lld.autocode.entity.dto;

import lombok.Data;

@Data
public class TableMetaDataDto {
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
     * package
     * **/
    private String packageUrl;
    /**
     *  字段是否需要导包
     * **/
    private String needImport;
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
    /**
     *  表备注
     * **/
    private String  tableComment;
}
