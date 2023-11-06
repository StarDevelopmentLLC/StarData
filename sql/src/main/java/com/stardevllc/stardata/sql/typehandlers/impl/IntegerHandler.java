package com.stardevllc.stardata.sql.typehandlers.impl;

import com.stardevllc.stardata.api.interfaces.model.FieldModel;
import com.stardevllc.stardata.api.interfaces.sql.SQLDatabase;
import com.stardevllc.stardata.sql.typehandlers.SQLTypeHandler;

public class IntegerHandler extends SQLTypeHandler {
    public IntegerHandler() {
        super(Integer.class, "int", IntegerHandler::parse, IntegerHandler::parse);
        addAdditionalClass(int.class);
    }
    
    private static Object parse(FieldModel<SQLDatabase> column, Object object) {
        if (object instanceof Number number) {
            return number.intValue();
        } else if (object instanceof String str) {
            return Integer.parseInt(str);
        }
        return 0;
    }
}
