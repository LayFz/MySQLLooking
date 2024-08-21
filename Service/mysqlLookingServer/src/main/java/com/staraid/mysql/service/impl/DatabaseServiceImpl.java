package com.staraid.mysql.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.staraid.mysql.Enum.ResultCode;
import com.staraid.mysql.config.DynamicDataSourceConfig;
import com.staraid.mysql.mapper.DatabaseMapper;
import com.staraid.mysql.pojo.Database;
import com.staraid.mysql.service.DatabaseService;
import com.staraid.mysql.utils.Result;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DatabaseServiceImpl extends ServiceImpl<DatabaseMapper, Database> implements DatabaseService{
    @Resource
    DynamicDataSourceConfig dynamicDataSourceConfig;

    @Override
    public Result getAllData() {
        return Result.success(list());
    }

    @Override
    public Result addDataBase(Database database) {
        if(baseMapper.insert(database) == 1){
            return Result.success();
        }else {
            return Result.error(ResultCode.FAIL);
        }
    }

    @Override
    public Result delDataBase(String id) {
        baseMapper.deleteById(id);
        return Result.success();
    }

    @Override
    public Database findDataById(Long dbId) {
        return getById(dbId);
    }

    @Override
    public Result addDataSource(Long dbId) {
        try {
            Database database = getById(dbId);
            String re = dynamicDataSourceConfig.addDataSource(String.valueOf(dbId), database);
            // 测试连接是否成功
            switch (re){
                case "SUCCESS":
                    return Result.success();
                case "EXISTS":
                    return Result.error(ResultCode.DATA_HAS_BEEN_CONNECTION);
                case "NET_WORK_BAD":
                    return Result.error(ResultCode.DATA_HAS_BEEN_CONNECTION);
                case "UN_AUTH":
                    return Result.error(ResultCode.DATA_HAS_BEEN_CONNECTION);
                default:
                    return Result.error(ResultCode.ERROR_DONT_KNOW);
            }
        } catch (Exception e) {
            // 添加数据源失败
            return Result.error(ResultCode.DATANOTFIND);
        }
    }

    @Override
    public Result removeDataSource(Long dbId) {
        if(dynamicDataSourceConfig.hasDataSource(String.valueOf(dbId))){
            dynamicDataSourceConfig.removeDataSource(String.valueOf(dbId));
            return Result.success();
        }else {
            return Result.error(ResultCode.DATANOTFIND);
        }
    }

    @Override
    public Result showSchemasByTableId(Long dbId) {
        DataSource dataSource = dynamicDataSourceConfig.getDataSource(String.valueOf(dbId));
        if (dataSource == null) {
            return Result.error(ResultCode.DATANOTFIND);
        }
        try (Connection connection = dataSource.getConnection()) {
            return Result.success(listCatalogs(connection));
        } catch (SQLException e) {
            System.err.println("Failed to get connection or list database info, Error: " + e.getMessage());
        }
        // 如果没有返回默认异常
        return Result.error(ResultCode.ERROR);
    }


    /**
     * 根据实例获取所有数据库
     * @param connection 数据实例
     * @return 数据库中的数据库Schemas
     */
    private Map<String, Object> listCatalogs(Connection connection) {
        Map<String, Object> result = new HashMap<>();
        List<String> catList = new ArrayList<>();
        try {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getCatalogs();
            while (resultSet.next()) {
                String catalogName = resultSet.getString("TABLE_CAT");
                catList.add(catalogName);
            }
            result.put("result", catList);
            result.put("total", catList.size());
        } catch (SQLException e) {
            System.err.println("Failed to list catalogs, Error: " + e.getMessage());
        }
        return result;
    }


}
