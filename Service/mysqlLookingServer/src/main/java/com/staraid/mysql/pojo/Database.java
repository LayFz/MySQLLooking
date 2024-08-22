package com.staraid.mysql.pojo;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据源实体
 */
@TableName("database")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Database {
    // id
    @TableId
    private Long id;

    // 数据库类型
    private String dbType;
    // 名称
    private String dbName;
    // 用户名
    private String dbUser;
    // 密码
    @JsonIgnore
    private String dbPassword;
    // 服务器地址
    private String dbUrl;
    // 端口
    private Integer dbPort;
    // 驱动程序
    private String dbDriver;
}
