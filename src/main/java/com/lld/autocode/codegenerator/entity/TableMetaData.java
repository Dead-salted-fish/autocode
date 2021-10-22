package com.lld.autocode.codegenerator.entity;



public class TableMetaData {
    private int rowKey;
    private String  columnName;
    private String  dataType;
    private boolean  canNullable;
    private String  columnKey;
    private String  remark;
    private String  javaType;
    private boolean  queryDtoAttribute;
    private boolean  dtoAttribute = true;


    public int getRowKey() {
        return rowKey;
    }

    public void setRowKey(int rowKey) {
        this.rowKey = rowKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public boolean isCanNullable() {
        return canNullable;
    }

    public void setCanNullable(boolean canNullable) {
        this.canNullable = canNullable;
    }

    public String getColumnKey() {
        return columnKey;
    }

    public void setColumnKey(String columnKey) {
        this.columnKey = columnKey;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public boolean isQueryDtoAttribute() {
        return queryDtoAttribute;
    }

    public void setQueryDtoAttribute(boolean queryDtoAttribute) {
        this.queryDtoAttribute = queryDtoAttribute;
    }

    public boolean isDtoAttribute() {
        return dtoAttribute;
    }

    public void setDtoAttribute(boolean dtoAttribute) {
        this.dtoAttribute = dtoAttribute;
    }
}
