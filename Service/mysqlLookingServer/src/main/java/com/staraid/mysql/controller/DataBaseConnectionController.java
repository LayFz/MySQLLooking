package com.staraid.mysql.controller;

import com.staraid.mysql.config.DynamicDataSourceConfig;
import com.staraid.mysql.service.DatabaseService;
import com.staraid.mysql.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/databases")
public class DataBaseConnectionController {

    @Autowired
    private DatabaseService databaseService;

    @PostMapping("/connect/{dbId}")
    public Result addDatabase(@PathVariable Long dbId) {
        return databaseService.addDataSource(dbId);
    }

    @PostMapping("/disconnect/{dbId}")
    public Result removeDatabase(@PathVariable Long dbId) {
        return databaseService.removeDataSource(dbId);
    }
}
