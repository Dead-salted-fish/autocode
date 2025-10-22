package com.lld.autocode.entity.dto;

import lombok.Data;

@Data
public class TableInfoDto {
    /**
     * 表名
     */
    private String tableName;

    /**
     * 表注释
     */
    private String tableComment;

    /**
     * 当前页码
     */
    private Long current;

    /**
     * 每页显示条数
     */
    private Long pageSize;

}
