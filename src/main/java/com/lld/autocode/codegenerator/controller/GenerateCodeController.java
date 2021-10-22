package com.lld.autocode.codegenerator.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lld.autocode.codegenerator.entity.GenerateDate;
import com.lld.autocode.codegenerator.entity.TableMetaData;
import com.lld.autocode.codegenerator.service.impl.GenerateCodeServiceImpl;
import net.sf.jsqlparser.schema.Table;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/9/22 15:29
 */
@Controller
public class GenerateCodeController {
    @Resource
    private GenerateCodeServiceImpl generateCodeService;

    @Resource
    private ObjectMapper objectMapper;

    @GetMapping("/getAllTableName")
    @ResponseBody
    public String getAllTableName(Long page,Long pageSize){
        Page<Table> tablePage = new Page<>(page,pageSize);
        IPage<Table> core = generateCodeService.getAllTableName("core", tablePage);
        String str = null;
        try {
            str = objectMapper.writeValueAsString(core);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @GetMapping("/getTableMetaData")
    @ResponseBody
    public String getTableMetaData(String tableName){
        List<TableMetaData> tableMetaDataList = generateCodeService.getTableMetaData(tableName);
        String str = null;
        try {
            str = objectMapper.writeValueAsString(tableMetaDataList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @GetMapping("/getAllJavaType")
    @ResponseBody
    public String getAllJavaType(){
        List<String> tableMetaDataList = generateCodeService.getAllJavaTypePackage();
        String str = null;
        try {
            str = objectMapper.writeValueAsString(tableMetaDataList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return str;
    }

    @PostMapping("/generateCode")
    @ResponseBody
    public Object generateCode(@RequestBody GenerateDate date) throws Exception {
        System.out.println(objectMapper.writeValueAsString(date));
        generateCodeService.generateCode(date);
        return "success";
    }


}
