package com.lld.autocode.repository;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lld.autocode.entity.TableInfo;
import com.lld.autocode.entity.TableMetaData;
import com.lld.autocode.entity.dto.TableInfoDto;
import com.lld.autocode.mapper.AutoCodeMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoCodeRepository {

    private AutoCodeMapper autoCodeMapper;

    public AutoCodeRepository(AutoCodeMapper autoCodeMapper) {
        this.autoCodeMapper = autoCodeMapper;
    }


    public Page<TableInfo> getAlltables(Page<TableInfo> page, TableInfoDto tableInfoDto) {
        return autoCodeMapper.getAlltables(page, tableInfoDto);
    }

    public List<TableMetaData> getTableMetaDate(String tableName) {
       return autoCodeMapper.getTableMetaDate(tableName);
    }

    public String getTableComment(String tableName) {
        return autoCodeMapper.getTableComment(tableName);
    }
}
