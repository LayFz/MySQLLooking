package com.staraid.mysql.controller;


import com.staraid.mysql.service.DatabaseService;
import com.staraid.mysql.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据展示获取
 */
@RestController
@RequestMapping("/api/databases")
public class DataBaseOperateController {
    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/showSchemas/{dbId}")
    public Result showSchemasByTableId(@PathVariable Long dbId){
        return databaseService.showSchemasByTableId(dbId);
    }
    @GetMapping("/showTables/{dbId}/{dbName}")
    public Result showTables(@PathVariable Long dbId, @PathVariable String dbName){
        return databaseService.showTables(dbId, dbName);
    }

}
