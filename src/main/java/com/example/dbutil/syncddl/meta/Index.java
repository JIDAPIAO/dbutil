/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package com.example.dbutil.syncddl.meta;

import java.util.ArrayList;
import java.util.List;

/**
 * 索引信息
 *
 * @Author lizhuyang
 */
public class Index {

    private List<String> columns;
    private String indexName;
    // 0表示unique,1表示普通索引
    private String notUnique;

    public Index() {
        columns = new ArrayList<String>();
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getNotUnique() {
        return notUnique;
    }

    public void setNotUnique(String notUnique) {
        this.notUnique = notUnique;
    }

    @Override
    public String toString() {
        return "Index{" +
                "columns=" + columns +
                ", indexName='" + indexName + '\'' +
                ", notUnique='" + notUnique + '\'' +
                '}' + "\n";
    }
}
