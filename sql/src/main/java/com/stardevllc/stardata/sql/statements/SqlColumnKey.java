package com.stardevllc.stardata.sql.statements;

import com.stardevllc.stardata.sql.interfaces.Column;

public class SqlColumnKey {
    private final String tableName, columnName, alias;

    public SqlColumnKey(String tableName, String columnName, String alias) {
        this.tableName = tableName;
        this.columnName = columnName;
        this.alias = alias;
    }

    public SqlColumnKey(String columnName, String alias) {
        this(null, columnName, alias);
    }

    public SqlColumnKey(String columnName) {
        this(columnName, null);
    }
    
    public SqlColumnKey(Column column, String alias) {
        this(column.getTable().getName(), column.getName(), alias);
    }
    
    public SqlColumnKey(Column column) {
        this(column, null);
    }

    public String getTableName() {
        return tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public String getAlias() {
        return alias;
    }
}