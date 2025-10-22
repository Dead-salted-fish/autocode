package com.lld.autocode.controller;

import com.lld.autocode.entity.dto.TableInfoDto;
import com.lld.autocode.service.AutoCodeService;
import com.lld.saltedfishutils.web.result.ReturnResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/autocode")
@RestController
public class AutoCodeController {

    private final AutoCodeService autoCodeService;

    public AutoCodeController(AutoCodeService autoCodeService) {
        this.autoCodeService = autoCodeService;
    }


    @GetMapping ("/getAlltables")
    public ReturnResult getAlltables(TableInfoDto tableInfoDto){
        return autoCodeService.getAlltables(tableInfoDto);
    }

    @GetMapping ("/getTableMetaDate")
    public ReturnResult getTableMetaDate(TableInfoDto tableInfoDto){
        return autoCodeService.getTableMetaDate(tableInfoDto);
    }

    @GetMapping ("/getJavaTypes")
    public ReturnResult getJavaTypes(TableInfoDto getJavaTypes){
        return autoCodeService.getJavaTypes();
    }
}
