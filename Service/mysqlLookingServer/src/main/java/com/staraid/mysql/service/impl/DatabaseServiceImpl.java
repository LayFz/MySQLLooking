package com.staraid.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.staraid.mysql.Enum.ResultCode;
import com.staraid.mysql.mapper.DatabaseMapper;
import com.staraid.mysql.pojo.Database;
import com.staraid.mysql.service.DatabaseService;
import com.staraid.mysql.utils.Result;
import org.springframework.stereotype.Service;

@Service
public class DatabaseServiceImpl extends ServiceImpl<DatabaseMapper, Database> implements DatabaseService{
    @Override
    public Result getAllData() {
        return Result.success(list());
    }

    @Override
    public Result addDataBase(Database database) {
        if(baseMapper.insert(database) == 1){
            return Result.success();
        }else {
            return Result.error(ResultCode.BAD_REQUEST);
        }
    }

    @Override
    public Result delDataBase(String id) {
        baseMapper.deleteById(id);
        return Result.success();
    }
}
