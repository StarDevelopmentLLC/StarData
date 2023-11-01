package com.stardevllc.stardata.sqlite;

import com.stardevllc.stardata.sql.objects.AbstractSQLDatabase;

import java.util.logging.Logger;

public class SQLiteDatabase extends AbstractSQLDatabase {
    public SQLiteDatabase(Logger logger, SQLiteProperties properties) {
        super(logger, properties);

        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        this.url = "jdbc:sqlite:";
        if (properties.isMemory()) {
            this.url += ":memory:";
        } else {
            this.url += properties.getDatabaseName();
        }
    }
}
