package com.stardevllc.stardata.sql.statements;

import com.stardevllc.stardata.sql.interfaces.Column;

import java.util.LinkedList;
import java.util.List;

public class WhereClause {
    private List<SqlColumnKey> columns = new LinkedList<>();
    private List<String> operators = new LinkedList<>();
    private List<Object> values = new LinkedList<>();

    public WhereClause addCondition(String column, String operator, Object value) {
        this.columns.add(new SqlColumnKey(column));
        this.operators.add(operator);
        this.values.add(value);
        return this;
    }
    
    public WhereClause addCondition(SqlColumnKey columnKey, String operator, Object value) {
        this.columns.add(columnKey);
        this.operators.add(operator);
        this.values.add(value);
        return this;
    }

    public WhereClause columns(String... columns) {
        if (columns != null) {
            for (String column : columns) {
                this.columns.add(new SqlColumnKey(column));
            }
        }
        return this;
    }
    
    public WhereClause columns(SqlColumnKey... columnKeys) {
        if (columnKeys != null) {
            this.columns.addAll(List.of(columnKeys));
        }
        return this;
    }
    
    public WhereClause columns(Column... columns) {
        if (columns != null) {
            for (Column column : columns) {
                this.columns.add(new SqlColumnKey(column.getTable().getName(), column.getName(), null));
            }
        }
        
        return this;
    }

    public WhereClause operators(String... operators) {
        if (operators != null) {
            this.operators.addAll(List.of(operators));
        }
        return this;
    }

    public WhereClause values(Object... values) {
        if (values != null) {
            this.values.addAll(List.of(values));
        }
        return this;
    }
    
    public String build() {
        int columnSize = this.columns.size();
        int operatorsSize = this.operators.size();
        int valuesSize = this.values.size();
        
        if (columnSize != operatorsSize || columnSize != valuesSize) {
            throw new IllegalArgumentException("Columns, operators and values do not have the same amount of entries.");
        }
        
        if (columnSize == 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder("WHERE ");
        
        for (int i = 0; i < columnSize; i++) {
            SqlColumnKey column = this.columns.get(i);
            if (column.getTableName() != null) {
                sb.append("`").append(column.getTableName()).append("`.");
            }
            sb.append("`").append(column.getColumnName()).append("`").append(this.operators.get(i)).append("'").append(this.values.get(i)).append("'").append(" AND ");
        }
        
        sb.delete(sb.length() - 5, sb.length());
        return sb.toString();
    }
}
