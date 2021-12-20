package com.lld.autocode.codegenerator.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.autocode.codegenerator.entity.GenerateDate;
import com.lld.autocode.codegenerator.service.impl.GenerateCodeServiceImpl;
import com.lld.autocode.utils.ReturnMessage;
import net.sf.jsqlparser.schema.Table;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:29
 */
@RestController
public class GenerateCodeController {
    @Resource
    private GenerateCodeServiceImpl generateCodeService;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/getAllTableName")
    public ReturnMessage getAllTableName(Long page, Long pageSize,String tableName){
        Page<Table> tablePage = new Page<>(page,pageSize);


        return generateCodeService.getAllTableName("core", tablePage,tableName);
    }

    @GetMapping("/getTableMetaData")
    public ReturnMessage getTableMetaData(String tableName){


        return generateCodeService.getTableMetaData(tableName);
    }

    @GetMapping("/getAllJavaType")
    public ReturnMessage getAllJavaType(){


        return generateCodeService.getAllJavaTypePackage();
    }

    @PostMapping("/generateCode")
    public ReturnMessage generateCode(@RequestBody GenerateDate date) throws Exception {

        return  generateCodeService.generateCode(date);
    }


}
