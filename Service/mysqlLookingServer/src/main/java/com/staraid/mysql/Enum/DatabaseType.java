package com.staraid.mysql.Enum;

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
