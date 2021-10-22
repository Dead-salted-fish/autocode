package com.lld.autocode.codegenerator.generatecode.fh;



import com.lld.autocode.codegenerator.entity.GenerateDate;
import com.lld.autocode.codegenerator.entity.JavaTypeDo;
import com.lld.autocode.codegenerator.generatecode.CurrencyMethods;
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

        String upClassName = CurrencyMethods.firstLetterUppercaseAfterSplit(date.getTableName(),"_");
        String lowerClassName = CurrencyMethods.firstLetterLowercaseAfterSplit(date.getTableName(),"_");

           new GenerateRepository().generate(upClassName, lowerClassName);
           new GenerateDomainQuery().generate(upClassName, lowerClassName,date,typeMap);
           new GenerateDomain().generate(upClassName, lowerClassName);
           new GenerateAssembler().generate(upClassName, lowerClassName);
           new GenerateService().generate(upClassName, lowerClassName);
           new GenerateServiceImpl().generate(upClassName, lowerClassName, date);
           new GenerateController().generate(upClassName, lowerClassName,date);
           new GenerateDo().generate(upClassName, lowerClassName,date,typeMap);

    }


}
