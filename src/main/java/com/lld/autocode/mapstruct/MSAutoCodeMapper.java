package com.lld.autocode.mapstruct;


import com.lld.autocode.entity.TableInfo;
import com.lld.autocode.entity.Vo.TableInfoVo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface MSAutoCodeMapper {
    // 使用工厂方法获取对象实例
    MSAutoCodeMapper INSTANCE = Mappers.getMapper(MSAutoCodeMapper.class);

    TableInfoVo tableInfoTotableInfoVo(TableInfo tableInfo);
    List<TableInfoVo> tableInfoListTotableInfoVoList(List<TableInfo> alltables);
}
