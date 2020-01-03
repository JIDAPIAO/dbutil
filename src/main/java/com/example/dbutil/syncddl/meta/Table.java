/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package com.example.dbutil.syncddl.meta;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Author lizhuyang
 */
public class Table {
    private String tableName;
    private String createTable;
    private Map<String, Column> columns;
    private Map<String, Index> indexes;

    public Table() {
        columns = new LinkedHashMap<String, Column>();
        indexes = new LinkedHashMap<String, Index>();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getCreateTable() {
        return createTable;
    }

    public void setCreateTable(String createTable) {
        this.createTable = createTable;
    }

    public Map<String, Column> getColumns() {
        return columns;
    }

    public void setColumns(Map<String, Column> columns) {
        this.columns = columns;
    }

    public Map<String, Index> getIndexes() {
        return indexes;
    }

    public void setIndexes(Map<String, Index> indexes) {
        this.indexes = indexes;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableName='" + tableName + '\'' +
                ", createTable='" + createTable + '\'' +
                ", columns=" + columns +
                ", indexes=" + indexes +
                '}';
    }
}
