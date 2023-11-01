package com.stardevllc.stardata.sql.objects;

import com.stardevllc.stardata.api.FKAction;
import com.stardevllc.stardata.api.annotations.*;
import com.stardevllc.stardata.api.interfaces.ObjectCodec;
import com.stardevllc.stardata.api.interfaces.SQLDatabase;
import com.stardevllc.stardata.api.interfaces.sql.Column;
import com.stardevllc.stardata.api.interfaces.sql.Table;
import com.stardevllc.stardata.api.interfaces.sql.TypeHandler;
import com.stardevllc.stardata.api.model.ForeignKeyStorageInfo;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Logger;

public class SQLColumn implements Column {
    private final Table table;
    private final Field field;

    private final String name;
    private String type;
    private final boolean primaryKey, autoIncrement, notNull, unique;
    private final int order;
    private ObjectCodec<?> codec;
    private TypeHandler typeHandler;
    
    private List<ForeignKeyStorageInfo> foreignKeyStorageInfos = new ArrayList<>();
    
    private Table parentForeignKeyTable; //This is the table that this column references
    private Column parentForeignKeyColumn; 
    
    private FKAction fkOnDelete, fkOnUpdate;
    
    /**
     * Constructs a column based on a class and the Field
     *
     * @param table The class
     * @param field The field
     */
    public SQLColumn(Table table, Field field) {
        this.table = table;
        this.field = field;

        Class<?> fieldType = field.getType();
        SQLDatabase database = table.getDatabase();
        if (field.isAnnotationPresent(Codec.class)) {
            try {
                codec = field.getAnnotation(Codec.class).value().getConstructor().newInstance();
                type = "varchar(1000)";
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException |
                     IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        
        if (field.isAnnotationPresent(Type.class)) {
            String codecType = field.getAnnotation(Type.class).value();
            if (this.codec != null) {
                if (!codecType.toLowerCase().startsWith("varchar")) {
                    throw new IllegalArgumentException("Field " + field.getName() + " in class " + table.getModelClass().getName() + " is annotated with the ColumnType annotation and specifies a non-varchar value. This is not allowed by this library.");
                }
            }

            type = codecType;
        }
        
        if (typeHandler == null && codec == null) {
            for (TypeHandler typeHandler : this.table.getDatabase().getTypeHandlers()) {
                if (typeHandler.matches(this.field.getType())) {
                    this.typeHandler = typeHandler;
                    break;
                }
            }
            
            ForeignKey foreignKeyData = field.getAnnotation(ForeignKey.class);
            if (foreignKeyData != null) {
                this.parentForeignKeyTable = database.getTable(foreignKeyData.value());
                if (this.parentForeignKeyTable != null) {
                    this.parentForeignKeyColumn = this.parentForeignKeyTable.getPrimaryKeyColumn();
                }
                
                if (this.typeHandler == null) {
                    this.typeHandler = this.parentForeignKeyColumn.getTypeHandler();
                }

                FKOnDelete onDeleteAnnotation = field.getAnnotation(FKOnDelete.class);
                if (onDeleteAnnotation != null) {
                    this.fkOnDelete = onDeleteAnnotation.value();
                } else {
                    this.fkOnDelete = foreignKeyData.onDelete();
                }

                FKOnUpdate onUpdateAnnotation = field.getAnnotation(FKOnUpdate.class);
                if (onUpdateAnnotation != null) {
                    this.fkOnUpdate = onUpdateAnnotation.value();
                } else {
                    this.fkOnUpdate = foreignKeyData.onUpdate();
                }
            } 

            if (this.typeHandler != null) {
                type = this.typeHandler.getMysqlType();
            } else {
                throw new IllegalArgumentException("Could not determine a handler or a codec for the field type " + field.getType().getName() + " in class " + table.getModelClass().getName());
            }
        }

        if (field.isAnnotationPresent(Name.class)) {
            name = field.getAnnotation(Name.class).value();
        } else {
            name = field.getName().toLowerCase();
        }

        boolean invalidIDType = List.of(String.class, char.class, Character.class, boolean.class, Boolean.class, UUID.class, Enum.class, float.class, Float.class, double.class, Double.class).contains(fieldType);
        boolean declaredID = field.isAnnotationPresent(ID.class) && !invalidIDType;
        
        boolean fieldIsLong = field.getType().equals(long.class) || field.getType().equals(Long.class);
        boolean fieldIsInt = field.getType().equals(int.class) || field.getType().equals(Integer.class);
        boolean fieldIsCorrectType = fieldIsInt || fieldIsLong;
        boolean fieldIsId = field.getName().equalsIgnoreCase("id");
        boolean fieldIsSoftPrimary = fieldIsCorrectType && fieldIsId;
        
        if (declaredID || fieldIsSoftPrimary) {
            autoIncrement = true;
            primaryKey = true;
            unique = true;
        } else {
            primaryKey = field.isAnnotationPresent(PrimaryKey.class);
            autoIncrement = field.isAnnotationPresent(AutoIncrement.class);
            unique = field.isAnnotationPresent(Unique.class);
        }

        notNull = field.isAnnotationPresent(DBNotNull.class);

        if (field.isAnnotationPresent(Order.class)) {
            Order order = field.getAnnotation(Order.class);
            Column otherOrderColumn = table.getColumnByOrder(order.value());
            if (otherOrderColumn != null) {
                table.getLogger().warning("Column " + name + " has a duplicate order value with " + otherOrderColumn.getName() + ". Using normal order increment for unordered columns.");
                this.order = table.getColumnOrderIndex();
                table.setColumnOrderIndex(this.order + 1);
            } else {
                this.order = order.value();
            }
        } else {
            this.order = table.getColumnOrderIndex();
            table.setColumnOrderIndex(this.order + 1);
        }
    }

    @Override
    public FKAction getForeignKeyOnDeleteAction() {
        return this.fkOnDelete;
    }

    @Override
    public FKAction getForeignKeyOnUpdateAction() {
        return this.fkOnUpdate;
    }

    public List<ForeignKeyStorageInfo> getForeignKeyStorageInfos() {
        return foreignKeyStorageInfos;
    }

    @Override
    public Table getParentForeignKeyTable() {
        return parentForeignKeyTable;
    }

    @Override
    public Column getParentForeignKeyColumn() {
        return parentForeignKeyColumn;
    }

    @Override
    public boolean hasForeignKey() {
        return this.parentForeignKeyTable != null && this.parentForeignKeyColumn != null;
    }

    @Override
    public Logger getLogger() {
        return table.getLogger();
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public TypeHandler getTypeHandler() {
        return typeHandler;
    }

    @Override
    public Table getTable() {
        return table;
    }

    @Override
    public Field getField() {
        return field;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean isUnique() {
        return unique;
    }

    @Override
    public boolean isPrimaryKey() {
        return primaryKey;
    }

    @Override
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    @Override
    public boolean isNotNull() {
        return notNull;
    }

    @Override
    public ObjectCodec<?> getCodec() {
        return codec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SQLColumn column = (SQLColumn) o;
        return Objects.equals(name, column.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(Column other) {
        return Integer.compare(this.order, other.getOrder());
    }
}