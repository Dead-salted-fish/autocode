package com.lld.autocode.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lld.autocode.entity.TableInfo;
import com.lld.autocode.entity.TableMetaData;
import com.lld.autocode.entity.Vo.TableInfoVo;
import com.lld.autocode.entity.dto.TableInfoDto;
import com.lld.autocode.mapstruct.MSAutoCodeMapper;
import com.lld.autocode.repository.AutoCodeRepository;
import com.lld.autocode.service.AutoCodeService;
import com.lld.autocode.utils.DatabaseTypeMapping;
import com.lld.saltedfishutils.utils.MPPageConverter;
import com.lld.saltedfishutils.web.result.ReturnResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AutoCodeServiceImpl implements AutoCodeService {

    private AutoCodeRepository autoCodeRepository;

    private MSAutoCodeMapper msAutoCodeMapper = MSAutoCodeMapper.INSTANCE;

    public AutoCodeServiceImpl(AutoCodeRepository autoCodeRepository) {
        this.autoCodeRepository = autoCodeRepository;
    }
    /**
     *  获取所有表
     * **/
    @Override
    public ReturnResult getAlltables(TableInfoDto tableInfoDto) {
        Long current = tableInfoDto.getCurrent();
        Long pageSize = tableInfoDto.getPageSize();

        Page<TableInfo> page = new Page<>(current, pageSize);
        Page<TableInfo> pageTables = autoCodeRepository.getAlltables(page,tableInfoDto);

        List<TableInfo> records = pageTables.getRecords();
        List<TableInfoVo>  alltablesVo =   msAutoCodeMapper.tableInfoListTotableInfoVoList(records);
        Page<TableInfoVo> convert = MPPageConverter.convert(pageTables, TableInfoVo.class);

        convert.setRecords(alltablesVo);


        return ReturnResult.OK(convert);
    }



    /**
     *  获取表结构
     * **/
    @Override
    public ReturnResult getTableMetaDate(TableInfoDto tableInfoDto) {
        String tableName = tableInfoDto.getTableName();
        List<TableMetaData> list = autoCodeRepository.getTableMetaDate(tableName);
        SupplyField(list);
        return ReturnResult.OK(list);
    }
  /**
     *  获取Java类型
     * **/
    @Override
    public ReturnResult getJavaTypes() {
        DatabaseTypeMapping[] values = DatabaseTypeMapping.values();
        Set<String> javaTypeslist = new HashSet<>();
        for (DatabaseTypeMapping value : values) {
            javaTypeslist.add(value.getJavaType());
        }
        return ReturnResult.OK(javaTypeslist);
    }

    private void SupplyField(List<TableMetaData> list) {
        for (TableMetaData tableMetaData : list) {
            tableMetaData.setJavaType(DatabaseTypeMapping.getJavaTypeByDatabaseType(tableMetaData.getDataType()));
        }
    }
}
