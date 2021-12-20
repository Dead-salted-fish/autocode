package com.lld.autocode.codegenerator.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.lld.autocode.codegenerator.entity.JavaTypeDo;
import com.lld.autocode.codegenerator.entity.TableMetaData;
import com.lld.autocode.codegenerator.entity.TypeMapping;
import net.sf.jsqlparser.schema.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:13
 */
@Mapper
public interface TableMapper extends BaseMapper<Table> {

    IPage<Table> getAllTableName(Page<Table> page,@Param("tableName") String tableName);

    List<TableMetaData> getTableMetaData(@Param("tableName") String tableName);

    List<TypeMapping> getAllTypeMapping();

    List<String> getAllJavaTypePackage();

    List<JavaTypeDo> getAllJavaType();


}
