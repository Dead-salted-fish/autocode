package com.lld.autocode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lld.autocode.entity.TableInfo;
import com.lld.autocode.entity.TableMetaData;
import com.lld.autocode.entity.Vo.TableInfoVo;
import com.lld.autocode.entity.dto.GenerateCodeDto;
import com.lld.autocode.entity.dto.TableInfoDto;
import com.lld.autocode.entity.dto.TableMetaDataDto;
import com.lld.autocode.generate.server.ServerGenerate;
import com.lld.autocode.generate.web.WebGenerate;
import com.lld.autocode.mapstruct.MSAutoCodeMapper;
import com.lld.autocode.repository.AutoCodeRepository;
import com.lld.autocode.service.AutoCodeService;
import com.lld.autocode.utils.DatabaseTypeMapping;
import com.lld.saltedfishutils.utils.MPPageConverter;
import com.lld.saltedfishutils.web.result.ReturnResult;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutoCodeServiceImpl implements AutoCodeService {

    @Value("${autocode.generate-path}")
    private String generatePath;

    private AutoCodeRepository autoCodeRepository;

    private MSAutoCodeMapper msAutoCodeMapper = MSAutoCodeMapper.INSTANCE;

    private VelocityEngine velocityEngine;

    private List<ServerGenerate> serverGenerates;

    private List<WebGenerate> webGenerates;

    public AutoCodeServiceImpl(AutoCodeRepository autoCodeRepository, VelocityEngine velocityEngine,
                               List<ServerGenerate> serverGenerates, List<WebGenerate> webGenerates) {
        this.autoCodeRepository = autoCodeRepository;
        this.velocityEngine = velocityEngine;
        this.serverGenerates = serverGenerates;
        this.webGenerates = webGenerates;
    }

    /**
     * 获取所有表
     **/
    @Override
    public ReturnResult getAlltables(TableInfoDto tableInfoDto) {
        Long current = tableInfoDto.getCurrent();
        Long pageSize = tableInfoDto.getPageSize();

        Page<TableInfo> page = new Page<>(current, pageSize);
        Page<TableInfo> pageTables = autoCodeRepository.getAlltables(page, tableInfoDto);

        List<TableInfo> records = pageTables.getRecords();
        List<TableInfoVo> alltablesVo = msAutoCodeMapper.tableInfoListTotableInfoVoList(records);
        Page<TableInfoVo> convert = MPPageConverter.convert(pageTables, TableInfoVo.class);

        convert.setRecords(alltablesVo);


        return ReturnResult.OK(convert);
    }


    /**
     * 获取表结构
     **/
    @Override
    public ReturnResult getTableMetaDate(TableInfoDto tableInfoDto) {
        String tableName = tableInfoDto.getTableName();
        List<TableMetaData> list = autoCodeRepository.getTableMetaDate(tableName);
        List<TableMetaDataDto> tableMetaDataDtos = msAutoCodeMapper.TableMetaDataListToTableMetaDataDtoList(list);
        SupplyField(tableMetaDataDtos);
        return ReturnResult.OK(tableMetaDataDtos);
    }

    /**
     * 获取Java类型
     **/
    @Override
    public ReturnResult getJavaTypes() {
        DatabaseTypeMapping[] values = DatabaseTypeMapping.values();
        Set<String> javaTypeslist = new HashSet<>();
        for (DatabaseTypeMapping value : values) {
            javaTypeslist.add(value.getPackageUrl());
        }
        return ReturnResult.OK(javaTypeslist);
    }

    /**
     * 生成代码
     **/
    @Override
    public ReturnResult generateCode(GenerateCodeDto generateCodeDto) {
        generateServerTemplate(generateCodeDto);
        generateWebTemplate(generateCodeDto);
        return ReturnResult.OK();
    }
   /**
     * 获取web端代码
     **/
    private void generateWebTemplate(GenerateCodeDto generateCodeDto) {
        List<String> webGenerateOptions = generateCodeDto.getWebGenerateOptions();
        for (WebGenerate webGenerate : webGenerates) {
            String generateType = webGenerate.getGenerateType();
            if(!webGenerateOptions.contains(generateType)){
                continue;
            }
            Map<String, Object> stringObjectMap = buildTemplateData(generateCodeDto,generateType );
            webGenerate.doGenerate(stringObjectMap);
        }
    }
   /**
     * 生成服务端代码
     **/
    private void generateServerTemplate(GenerateCodeDto generateCodeDto) {
        List<String> serverGenerateOptions = generateCodeDto.getServerGenerateOptions();
        for (ServerGenerate serverGenerate : serverGenerates) {
            String generateType = serverGenerate.getGenerateType();
            if (!serverGenerateOptions.contains(generateType)) {
                continue;
            }
            Map<String, Object> stringObjectMap = buildTemplateData(generateCodeDto, generateType);
            serverGenerate.doGenerate(stringObjectMap);
        }
    }

    private Map<String, Object> buildTemplateData(GenerateCodeDto generateCodeDto, String type) {
        // 准备模板数据
        Map<String, Object> model = new HashMap<>();
        String lowerCaseType = type.toLowerCase();
        //表名
        String tableName = generateCodeDto.getTableName();
        //类名
        String className = generateCamelCaseName(tableName, true);
        //首字母小写的类名
        String lowerCaseClassName = generateCamelCaseName(tableName, false);
        //额外导包的内容
        Set<String> importPackageList = new HashSet<>();
        Boolean attributePacKageImport = false;
        if ("Entity".toLowerCase().equals(lowerCaseType) ||
                "Dto".toLowerCase().equals(lowerCaseType) ||
                "Vo".toLowerCase().equals(lowerCaseType)) {
            attributePacKageImport = true;
        }
        //属性内容
        List<Map<String, String>> fields = new ArrayList<>();

        //填入属性内容
        List<TableMetaDataDto> tableMetaDatas = generateCodeDto.getTableMetaDatas();
        for (TableMetaDataDto tableMetaDataDto : tableMetaDatas) {
            if ("1".equals(tableMetaDataDto.getNeedImport())) {
                if (attributePacKageImport) {
                    importPackageList.add(tableMetaDataDto.getPackageUrl());
                }
            }

            // 处理字段信息
            Map<String, String> fieldInfo = new HashMap<>();
            fieldInfo.put("javaType", tableMetaDataDto.getJavaType());
            fieldInfo.put("upperCaseDataType", tableMetaDataDto.getDataType().toUpperCase());
            fieldInfo.put("columnName", tableMetaDataDto.getColumnName());
            fieldInfo.put("attributeName", generateCamelCaseName(tableMetaDataDto.getColumnName(), false));
            fieldInfo.put("columnComment", tableMetaDataDto.getColumnComment());
            fields.add(fieldInfo);
        }

        //模板数据
        model.put("tableName", tableName);
        model.put("className", className);
        model.put("lowerCaseClassName", lowerCaseClassName);
        model.put("imports", importPackageList);
        model.put("fields", fields);
        model.put("serverMethods", generateCodeDto.getServerMethodsGenerateOptions());
        model.put("basePath", generateCodeDto.getBasePath());
        return model;
    }

    private void SupplyField(List<TableMetaDataDto> list) {
        for (TableMetaDataDto tableMetaDataDto : list) {
            Map<String, String> map = DatabaseTypeMapping.getJavaTypeAndPackageUrlByDatabaseType(tableMetaDataDto.getDataType());
            tableMetaDataDto.setJavaType(map.get("javaType"));
            tableMetaDataDto.setPackageUrl(map.get("packageUrl"));
            tableMetaDataDto.setNeedImport(map.get("needImport"));
        }
    }

    //驼峰命名
    private String generateCamelCaseName(String originalName, boolean capitalizeFirst) {
        if (originalName == null || originalName.isEmpty()) {
            return "";
        }

        String[] parts = originalName.split("_");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (parts[i] != null && !parts[i].isEmpty()) {
                if (i == 0 && !capitalizeFirst) {
                    result.append(parts[i]);
                } else {
                    result.append(Character.toUpperCase(parts[i].charAt(0)))
                            .append(parts[i].substring(1));
                }
            }
        }
        return result.toString();
    }

}
