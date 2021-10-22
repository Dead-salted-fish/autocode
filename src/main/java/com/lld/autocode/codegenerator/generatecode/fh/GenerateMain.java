package com.lld.autocode.codegenerator.generatecode.fh;



import com.lld.autocode.codegenerator.entity.GenerateDate;
import com.lld.autocode.codegenerator.entity.JavaTypeDo;
import com.lld.autocode.codegenerator.mapper.TableMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description:
 * @author: wzl
 * @date 2021/8/18 15:41
 */
public class GenerateMain {



    public  void generate(GenerateDate date, TableMapper tableMapper) throws Exception {


        List<JavaTypeDo> allJavaType = tableMapper.getAllJavaType();
        Map<String,JavaTypeDo> typeMap= new HashMap<>();
        for(JavaTypeDo item:allJavaType){
            typeMap.put(item.getJavaTypePackage(),item);
        }

        String upClassName = getUpClassName(date.getTableName());
        String lowerClassName = getLowerClassName(date.getTableName());

           new GenerateRepository().generate(upClassName, lowerClassName);
           new GenerateDomainQuery().generate(upClassName, lowerClassName,date,typeMap);
           new GenerateDomain().generate(upClassName, lowerClassName);
           new GenerateAssembler().generate(upClassName, lowerClassName);
           new GenerateService().generate(upClassName, lowerClassName);
           new GenerateServiceImpl().generate(upClassName, lowerClassName, date);
           new GenerateController().generate(upClassName, lowerClassName,date);
           new GenerateDo().generate(upClassName, lowerClassName,date,typeMap);

    }

    private String getUpClassName(String tableName){
        String[]tableNameArr = tableName.split("_");
        StringBuffer upClassName = new StringBuffer();
        for(String item:tableNameArr){
            upClassName.append(captureName(item));
        }
        return upClassName.toString();
    }

    private String getLowerClassName(String tableName){
        String[]tableNameArr = tableName.split("_");
        StringBuffer lowerClassName = new StringBuffer();
        for(int i =0,j=tableNameArr.length;i<j;i++){
            if(i>0){
                lowerClassName.append(captureName(tableNameArr[i]));
            }else {
                lowerClassName.append(tableNameArr[i]);
            }
        }
        return lowerClassName.toString();
    }

    public  String captureName(String attributeName) {
        char[] cs = attributeName.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }
}
