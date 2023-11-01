package com.stardevllc.stardata.api.model;

import com.stardevllc.stardata.api.interfaces.Database;
import com.stardevllc.starlib.registry.StringRegistry;

import java.util.Collection;
import java.util.Map;
import java.util.logging.Logger;

public abstract class DatabaseRegistry<D extends Database> extends StringRegistry<D> {
    
    protected final Logger logger;
    
    public DatabaseRegistry(Logger logger, Map<String, D> initialObjects) {
        super(initialObjects, key -> key.toLowerCase().replace(" ", "_"));
        this.logger = logger;
    }

    public DatabaseRegistry(Logger logger) {
        this(logger, null);
    }

    public Logger getLogger() {
        return logger;
    }

    public abstract void setup() throws Exception;
    public abstract boolean isSetup();
    public abstract void register(D database);
    public abstract void registerAll(Collection<D> databases);
}