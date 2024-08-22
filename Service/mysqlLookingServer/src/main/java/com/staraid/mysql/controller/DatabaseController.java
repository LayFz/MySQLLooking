package com.staraid.mysql.controller;


import com.staraid.mysql.pojo.Database;
import com.staraid.mysql.service.DatabaseService;
import com.staraid.mysql.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;


/**
 * 数据处理类controller
 */
@RestController
@RequestMapping("/api/databases")
public class DatabaseController {
    @Autowired
    private DatabaseService databaseService;


    /**
     * 获取所有数据库列表
     * @return
     */
    @GetMapping()
    public Result getAllDatabse(){
        return databaseService.getAllData();
    }

    /**
     * 添加数据连接
     * @param database
     * @return
     */
    @PostMapping("/add")
    public Result addDataBase(@RequestBody Database database){
        return databaseService.addDataBase(database);
    }

    /**
     * 删除数据连接
     * @param id 数据库id
     * @return
     */
    @DeleteMapping ("/del")
    public Result delDataBase(@RequestParam("dataId") String id){
        return databaseService.delDataBase(id);
    }






}
