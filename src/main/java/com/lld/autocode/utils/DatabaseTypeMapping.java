package com.lld.autocode.utils;

import java.math.BigDecimal;

public enum DatabaseTypeMapping {
    // 数值类型
    bigint("bigint", "Long","0"),
    varchar("varchar", "String", "0"),
    datetime("datetime", "java.util.Date", "1"),
    decimal("decimal", "java.math.BigDecimal", "1"),
    dbchar("char", "String", "0");

    private final String databaseType;
    private final String javaType;
    //0不需要导包 1 需要导包
    private final String needImport;


    DatabaseTypeMapping(String databaseType, String javaType, String needImport) {
        this.databaseType = databaseType;
        this.javaType = javaType;
        this.needImport = needImport;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public String getJavaType() {
        return javaType;
    }

    public String getNeedImport() {
        return needImport;
    }

    // 根据数据库类型获取映射的Java类型
    public static String getJavaTypeByDatabaseType(String dbType) {
        for (DatabaseTypeMapping mapping : DatabaseTypeMapping.values()) {
            if (mapping.databaseType.equalsIgnoreCase(dbType)) {
                return mapping.javaType;
            }
        }
        return null; // 默认返回Object
    }
}
