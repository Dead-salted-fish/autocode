package com.lld.autocode.utils;


import java.util.HashMap;
import java.util.Map;

public enum DatabaseTypeMapping {
    // 数值类型
    bigint("bigint", "Long", "Long", "0"),
    dbint("int", "Integer", "Integer", "0"),
    varchar("varchar", "String", "String","0"),
    datetime("datetime", "Date", "java.util.Date","1"),
    decimal("decimal", "BigDecimal", "java.math.BigDecimal","1"),
    dbchar("char", "String","String", "0"),
    text("text", "String","String", "0");

    //数据库类型
    private final String databaseType;
    //java类型
    private final String javaType;
    //包名
    private final String packageUrl;
    //0不需要导包 1 需要导包
    private final String needImport;



    DatabaseTypeMapping(String databaseType, String javaType, String packageUrl , String needImport) {
        this.databaseType = databaseType;
        this.javaType = javaType;
        this.packageUrl = packageUrl;
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

    public String getPackageUrl() {
        return packageUrl;
    }

    // 根据数据库类型获取映射的Java类型
    public static Map<String, String> getJavaTypeAndPackageUrlByDatabaseType(String dbType) {
        Map<String, String> map = new HashMap<>();
        for (DatabaseTypeMapping mapping : DatabaseTypeMapping.values()) {
            if (mapping.databaseType.equalsIgnoreCase(dbType)) {
                map.put("packageUrl", mapping.packageUrl);
                map.put("needImport", mapping.needImport);
                map.put("javaType", mapping.javaType);
                return map;
            }
        }
        return null; // 默认返回Object
    }
}
