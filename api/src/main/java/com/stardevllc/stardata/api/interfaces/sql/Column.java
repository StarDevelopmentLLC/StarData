package com.stardevllc.stardata.api.interfaces.sql;

import com.stardevllc.stardata.api.annotations.Codec;
import com.stardevllc.stardata.api.annotations.Name;
import com.stardevllc.stardata.api.annotations.Order;
import com.stardevllc.stardata.api.annotations.Type;
import com.stardevllc.stardata.api.interfaces.ObjectCodec;
import com.stardevllc.stardata.api.model.ForeignKeyStorageInfo;

import java.lang.reflect.Field;
import java.util.List;
import java.util.logging.Logger;

/**
 * This represents a column in a Table within a Database. Instances of this class are created for every field that is not ignored following the ignoring rules in the {@link Table} documentation<br>
 * This class is mainly an internal class used by the library. You shouldn't need to interact with this class directly, however you can find how this works for how Columns work in this library<br>
 * When determining the type of a field, this library first looks at the {@link Codec} and set it to a default of varchar(1000), this can be overridden with the {@link Type} annotation<br>
 * It will then look at to see if there is a registered table in the database. if there is, then it set the values based on the primary key column of the table<br>
 * Then it will look at the {@link Type} annotation for an override. Please note, these must be compatible. <br>
 * For compatibility, it can just be default compatible, using a {@link TypeHandler} or {@link ObjectCodec}. <br>
 * The name of the column can be taken from the name of the class, which will be all lower-case, or using the {@link Name} annotation.<br>
 * You can use the other annotatations to customize the columns as you see fit. This library will not check to see if your configuration is wrong, you will get SQLExceptions if it is.
 */
public interface Column extends Comparable<Column> {

    /**
     * @return The logger provided via the Database
     */
    Logger getLogger();

    /**
     * @return The order index, see the {@link Order} annotation
     */
    int getOrder();

    /**
     * @return The type handler for parsing values in this column.
     */
    TypeHandler getTypeHandler();

    /**
     * @return The table that this column is a part of.
     */
    Table getTable();

    /**
     * @return The field that this column is based on.
     */
    Field getField();

    /**
     * @return The name of this column, this is the name in the actual database.
     */
    String getName();

    /**
     * @return The SQL Database Type
     */
    String getType();

    /**
     * @return If this column is unique in the database
     */
    boolean isUnique();

    /**
     * @return If this column is the primary key of the table.
     */
    boolean isPrimaryKey();

    /**
     * @return If this column auto-increments when inserting.
     */
    boolean isAutoIncrement();

    /**
     * @return If this column is allowed to have null values in the database.
     */
    boolean isNotNull();

    /**
     * @return The current object codec for parsing values in this column
     */
    ObjectCodec<?> getCodec();

    /**
     * @return If this column has a Foreign Key Constraint
     */
    boolean hasForeignKey();

    /**
     * @return The linked parent for the configured Foreign Key
     */
    Table getParentForeignKeyTable();

    /**
     * @return The Primary Key Column for the configured Foreign key. <br>
     * This is just a utility method to make things a bit cleaner on the backend.
     */
    Column getParentForeignKeyColumn();

    /**
     * @return Gets all information about linked foreign keys. This is only populated on the Primary Key column.
     */
    List<ForeignKeyStorageInfo> getForeignKeyStorageInfos();
}
