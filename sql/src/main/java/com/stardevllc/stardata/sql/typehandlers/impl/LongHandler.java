package com.stardevllc.stardata.sql.typehandlers.impl;

import com.stardevllc.stardata.api.interfaces.model.FieldModel;
import com.stardevllc.stardata.api.interfaces.sql.SQLDatabase;
import com.stardevllc.stardata.sql.typehandlers.SQLTypeHandler;

public class LongHandler extends SQLTypeHandler {
    public LongHandler() {
        super(Long.class, "bigint", LongHandler::parse, LongHandler::parse);
        addAdditionalClass(long.class);
    }
    
    private static Object parse(FieldModel<SQLDatabase> column, Object object) {
        if (object instanceof Number number) {
            return number.longValue();
        } else if (object instanceof String str) {
            return Long.parseLong(str);
        }
        return 0;
    }
}
