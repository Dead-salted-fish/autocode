package com.lld.autocode.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class GenerateCodeDto {
    private String tableName;
    private List<TableMetaDataDto> tableMetaDatas;
    private List<String> serverGenerateOptions;
    private List<String> webGenerateOptions;
}
