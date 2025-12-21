package com.lld.autocode.service;

import com.lld.autocode.entity.dto.FileInfoDto;
import com.lld.autocode.entity.dto.GenerateCodeDto;
import com.lld.autocode.entity.dto.TableInfoDto;
import com.lld.saltedfishutils.web.result.ReturnResult;

public interface AutoCodeService {
    /**
     *  获取所有表
     * **/
    ReturnResult getAlltables(TableInfoDto tableInfoDto);
    /**
     *  获取表元数据
     * **/
    ReturnResult getTableMetaDate(TableInfoDto tableInfoDto);
      /**
       * 获取所有java类型
       * **/
    ReturnResult getJavaTypes();
     /**
     * 生成代码
     * **/
    ReturnResult generateCode(GenerateCodeDto generateCodeDto);

    ReturnResult getWebComponents();

    ReturnResult getGenerateFiles();

    ReturnResult getGenerateFileContent(FileInfoDto fileInfoDto);
}
