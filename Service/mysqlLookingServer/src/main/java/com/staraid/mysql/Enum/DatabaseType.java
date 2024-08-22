package com.staraid.mysql.Enum;

/**
 * 数据库类型
 */
public enum DatabaseType {
    MYSQL("mysql"),
    POSTGRESQL("postgresql"),
    SQLITE("sqlite");

    private final String driverPrefix;

    DatabaseType(String driverPrefix) {
        this.driverPrefix = driverPrefix;
    }

    public String getDriverPrefix() {
        return driverPrefix;
    }
}
