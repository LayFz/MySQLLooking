package com.staraid.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.staraid.mysql.mapper.DatabaseMapper;
import com.staraid.mysql.pojo.Database;
import com.staraid.mysql.utils.Result;

import java.util.Optional;


public interface DatabaseService extends IService<Database> {


    // 获取所有数据库信息
    Result getAllData();

    // 添加数据连接
    Result addDataBase(Database database);

    // 数据库删除
    Result delDataBase(String id);

    // 数据源的查找，用于数据库查找
    Database findDataById(Long dbId);

    // 连接数据库
    Result addDataSource(Long dbId);

    // 断开数据库连接
    Result removeDataSource(Long dbId);

    // 展示数据库表
    Result showSchemasByTableId(Long dbId);

    // 根据表明查看
    Result showTables(Long dbId, String dbName);
}
