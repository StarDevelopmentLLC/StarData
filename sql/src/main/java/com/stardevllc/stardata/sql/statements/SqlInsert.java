package com.stardevllc.stardata.sql.statements;

import com.stardevllc.stardata.sql.interfaces.Column;
import com.stardevllc.stardata.sql.interfaces.Table;

import java.util.LinkedList;
import java.util.List;

public class SqlInsert implements SqlStatement {

    private final String tableName;
    private List<SqlColumnKey> columns = new LinkedList<>();
    private List<List<Object>> rows = new LinkedList<>();

    public SqlInsert(String tableName) {
        this.tableName = tableName;
    }

    public SqlInsert(Table table, boolean allColumns) {
        this(table.getName());
        if (allColumns) {
            for (Column column : table.getColumns()) {
                columns.add(new SqlColumnKey(this.tableName, column.getName(), null));
            }
        }
    }

    public SqlInsert columns(String... columns) {
        if (columns != null) {
            for (String column : columns) {
                this.columns.add(new SqlColumnKey(null, column, null));
            }
        }

        return this;
    }

    public SqlInsert columns(SqlColumnKey... columnKeys) {
        if (columnKeys != null) {
            this.columns.addAll(List.of(columnKeys));
        }

        return this;
    }

    public SqlInsert row(Object... values) {
        if (values != null) {
            rows.add(new LinkedList<>(List.of(values)));
        }

        return this;
    }

    public String build() {
        if (columns.isEmpty()) {
            throw new IllegalArgumentException("You cannot have empty columns in an insert statement.");
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("You cannot have empty rows in an insert statement.");
        }

        StringBuilder sb = new StringBuilder("INSERT INTO ").append("`").append(tableName).append("` (");
        for (SqlColumnKey column : columns) {
            String tableName = column.getTableName() != null ? "`" + column.getTableName() + "`." : "";
            String columnName = column.getAlias() != null ? column.getAlias() : column.getColumnName();

            sb.append(tableName).append("`").append(columnName).append("`").append(", ");
        }

        sb.delete(sb.length() - 2, sb.length()).append(") VALUES ");

        for (List<Object> row : rows) {
            sb.append("(");
            for (Object object : row) {
                sb.append("'").append(object).append("', ");
            }

            sb.delete(sb.length() - 2, sb.length()).append("), ");
        }

        sb.delete(sb.length() - 2, sb.length()).append(";");
        return sb.toString();
    }
}
