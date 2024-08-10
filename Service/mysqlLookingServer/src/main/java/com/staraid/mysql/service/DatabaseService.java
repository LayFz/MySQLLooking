package com.staraid.mysql.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.staraid.mysql.mapper.DatabaseMapper;
import com.staraid.mysql.pojo.Database;
import com.staraid.mysql.utils.Result;


public interface DatabaseService extends IService<Database> {


    // 获取所有数据库信息
    Result getAllData();

    // 添加数据连接
    Result addDataBase(Database database);

    // 数据库删除
    Result delDataBase(String id);
}
