/*
 * Copyright (C) 2016 alchemystar, Inc. All Rights Reserved.
 */
package com.example.dbutil.syncddl.meta;



import com.alibaba.druid.util.JdbcUtils;
import com.example.dbutil.syncddl.util.SqlUtil;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 当前Metadata不考虑分区键
 *
 * @Author lizhuyang
 */
public class MetaData {

    private Connection conn;
    private String user;
    private String password;
    private String schema;
    private String jdbcUrl;

    private Map<String, Table> tables = new LinkedHashMap<String, Table>();

    public void init() {
        try {
            Class.forName(JdbcUtils.getDriverClassName(jdbcUrl));
            conn = DriverManager.getConnection(jdbcUrl, user, password);
            getTablesFromDb();
            getCreateTables();
            getAllTableColumns();
            getAllTableKeys();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getTablesFromDb() throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "show tables from " + schema);
        while (rs.next()) {
            Table table = new Table();
            table.setTableName(rs.getString("Tables_in_" + schema));
            tables.put(table.getTableName(), table);
        }
    }

    private void getCreateTables() throws SQLException {
        for (Table table : tables.values()) {
            getCreateTable(table);
        }
    }

    private void getCreateTable(Table table) throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "show create table " + schema + "." + table.getTableName());
        while (rs.next()) {
            table.setCreateTable(rs.getString("Create Table"));
        }
    }

    private void getAllTableColumns() throws SQLException {
        for (Table table : tables.values()) {
            getTableColumns(table);
        }
    }

    private void getTableColumns(Table table) throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "select COLUMN_NAME,COLUMN_TYPE,IS_NULLABLE,COLUMN_DEFAULT,"
                + "COLUMN_COMMENT,EXTRA,CHARACTER_SET_NAME,COLLATION_NAME from information_schema.columns where TABLE_SCHEMA=" + "'" + schema + "'"
                + " and "
                + "TABLE_NAME=" + "'" + table.getTableName() + "'" + " order by ORDINAL_POSITION asc");
        while (rs.next()) {
            Column column = new Column();
            column.setName(rs.getString("COLUMN_NAME"));
            column.setType(rs.getString("COLUMN_TYPE"));
            column.setDefaultValue(rs.getString("COLUMN_DEFAULT"));
            column.setIsNull(rs.getString("IS_NULLABLE"));
            column.setExtra(rs.getString("EXTRA"));
            column.setComment(rs.getString("COLUMN_COMMENT"));
            // column.setCharset(rs.getString("CHARACTER_SET_NAME"));
            column.setCollate(rs.getString("COLLATION_NAME"));
            table.getColumns().put(column.getName(), column);
        }
    }

    private void getAllTableKeys() throws SQLException {
        for (Table table : tables.values()) {
            getTableKeys(table);
        }
    }

    private void getTableKeys(Table table) throws SQLException {
        ResultSet rs = SqlUtil.executeSql(conn, "show keys from " + schema + "." + table.getTableName());
        Index last = null;
        Index tmp = null;
        while (rs.next()) {
            String keyName = rs.getString("Key_name");
            if (last == null || !keyName.equals(last.getIndexName())) {
                last = new Index();
                last.setIndexName(keyName);
                last.getColumns().add(rs.getString("Column_name"));
                last.setNotUnique(rs.getString("Non_unique"));
                table.getIndexes().put(last.getIndexName(), last);
            } else {
                // 表明这两个key在同一索引中
                last.getColumns().add(rs.getString("Column_name"));
            }
        }
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Map<String, Table> getTables() {
        return tables;
    }

    public void setTables(Map<String, Table> tables) {
        this.tables = tables;
    }
}
