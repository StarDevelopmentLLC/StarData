package com.stardevllc.stardata.postgres;

import com.stardevllc.stardata.sql.AbstractSQLDatabase;

import java.util.logging.Logger;

public class PostgresDatabase extends AbstractSQLDatabase {
    public PostgresDatabase(Logger logger, PostgresProperties properties) {
        super(logger, properties);

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        this.url = "jdbc:postgresql:" + properties.getHost() + ":" + properties.getPort() + "/" + properties.getDatabaseName();
    }
}