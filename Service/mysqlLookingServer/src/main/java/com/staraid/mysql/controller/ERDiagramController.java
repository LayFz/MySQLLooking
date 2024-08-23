package com.staraid.mysql.controller;


import com.staraid.mysql.config.DynamicDataSourceConfig;
import com.staraid.mysql.pojo.Database;
import com.staraid.mysql.service.ERDiagramService;
import com.staraid.mysql.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * e-r图controller
 *
 */
@RestController
@RequestMapping("/api/er-diagram")
public class ERDiagramController {

    @Autowired
    ERDiagramService erDiagramService;


    @GetMapping("/data/{dbId}/{dbName}")
    public Result getERDiagramData(@PathVariable Long dbId,@PathVariable String dbName){
        return erDiagramService.getTableDiagram(dbId,dbName);
    }


}
