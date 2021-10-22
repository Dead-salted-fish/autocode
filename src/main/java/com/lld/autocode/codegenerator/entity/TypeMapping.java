package com.lld.autocode.codegenerator.entity;

/**
 * @description:
 * @author: wzl
 * @date 2021/10/20 15:13
 */
public class TypeMapping {
    private Long id;
    private String databaseType;
    private String javaTypeClassName;
    private String javaTypePackage;
    private String importPackage;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public String getJavaTypeClassName() {
        return javaTypeClassName;
    }

    public void setJavaTypeClassName(String javaTypeClassName) {
        this.javaTypeClassName = javaTypeClassName;
    }

    public String getJavaTypePackage() {
        return javaTypePackage;
    }

    public void setJavaTypePackage(String javaTypePackage) {
        this.javaTypePackage = javaTypePackage;
    }

    public String getImportPackage() {
        return importPackage;
    }

    public void setImportPackage(String importPackage) {
        this.importPackage = importPackage;
    }
}
