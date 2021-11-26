package com.lld.autocode.codegenerator.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.lld.autocode.codegenerator.entity.GenerateDate;
import com.lld.autocode.codegenerator.entity.TableMetaData;
import com.lld.autocode.codegenerator.entity.TypeMapping;
import com.lld.autocode.codegenerator.generatecode.fh.GenerateMain;

import com.lld.autocode.codegenerator.mapper.TableMapper;
import com.lld.autocode.codegenerator.service.GenerateCodeService;
import com.lld.autocode.utils.ReturnMessage;
import net.sf.jsqlparser.schema.Table;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:12
 */
@Service
public class GenerateCodeServiceImpl extends ServiceImpl<TableMapper, Table> implements GenerateCodeService {



    @Resource
    private TableMapper tableMapper;

    @Override
    public ReturnMessage getAllTableName(String dataBaseName, Page<Table> page) {
        IPage<Table> allTableName = this.baseMapper.getAllTableName(page);
       return ReturnMessage.ok(allTableName);
    }

    @Override
    public ReturnMessage getTableMetaData(String tableName) {
        List<TableMetaData> tableMetaData = this.baseMapper.getTableMetaData(tableName);
        List<TypeMapping> allTypeMapping = this.baseMapper.getAllTypeMapping();
        Map<String,String> typeMappingMap = new HashMap<>();
        for(TypeMapping item:allTypeMapping){
            typeMappingMap.put(item.getDatabaseType(),item.getJavaTypePackage());
        }
        for(TableMetaData item:tableMetaData){
            item.setJavaType(typeMappingMap.get(item.getDataType()));
        }
        return ReturnMessage.ok(tableMetaData);
    }

    @Override
    public ReturnMessage getAllJavaTypePackage() {

        return ReturnMessage.ok(this.baseMapper.getAllJavaTypePackage()) ;
    }

    @Override
    public ReturnMessage generateCode(GenerateDate date) throws Exception {
             new GenerateMain().generate(date,tableMapper);
           return  ReturnMessage.ok();
    }
}
