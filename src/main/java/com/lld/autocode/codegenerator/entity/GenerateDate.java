package com.lld.autocode.codegenerator.entity;

import java.util.List;

/**
 * @description:
 * @author: wzl
 * @date 2021/10/21 10:37
 */
public class GenerateDate {
    private String tableName;
    private List<TableMetaData> attributeDate;
    private boolean normalAnnotation;
    private boolean swagger;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<TableMetaData> getAttributeDate() {
        return attributeDate;
    }

    public void setAttributeDate(List<TableMetaData> attributeDate) {
        this.attributeDate = attributeDate;
    }

    public boolean isNormalAnnotation() {
        return normalAnnotation;
    }

    public void setNormalAnnotation(boolean normalAnnotation) {
        this.normalAnnotation = normalAnnotation;
    }

    public boolean isSwagger() {
        return swagger;
    }

    public void setSwagger(boolean swagger) {
        this.swagger = swagger;
    }

    @Override
    public String toString() {
        return "GenerateDate{" +
                "tableName='" + tableName + '\'' +
                ", attributeDate=" + attributeDate.toString() +
                ", normalAnnotation=" + normalAnnotation +
                ", swagger=" + swagger +
                '}';
    }
}
