package com.lld.autocode.codegenerator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lld.autocode.codegenerator.entity.GenerateDate;
import net.sf.jsqlparser.schema.Table;
import com.lld.autocode.codegenerator.entity.TableMetaData;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:11
 */

public interface GenerateCodeService extends IService<Table> {
    IPage<Table> getAllTableName(String dataBaseName, Page<Table> page);

    List<TableMetaData> getTableMetaData(String tableName);

    List<String> getAllJavaTypePackage();

    void generateCode(GenerateDate date) throws Exception;
}
