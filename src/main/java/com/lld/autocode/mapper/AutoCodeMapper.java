package com.lld.autocode.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lld.autocode.entity.TableInfo;
import com.lld.autocode.entity.TableMetaData;
import com.lld.autocode.entity.dto.TableInfoDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AutoCodeMapper  extends BaseMapper<TableInfo> {
    Page<TableInfo> getAlltables(Page<TableInfo> page,@Param("tableInfoDto") TableInfoDto tableInfoDto);

    List<TableMetaData> getTableMetaDate(@Param("tableName") String tableName);

    String getTableComment(@Param("tableName") String tableName);
}
