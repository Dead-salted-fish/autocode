package com.lld.autocode.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenerateCodeDto {
    private String tableName;
    //表结构
    private List<TableMetaDataDto> tableMetaDatas;
    //服务端生成
    private List<String> serverGenerateOptions;
    //web端生成
    private List<String> webGenerateOptions;
    //服务端方法生成
    private List<String> serverMethodsGenerateOptions;
    //基础路径
    private String basePath;
    //包路径
    private List< String> packagePaths;
}
