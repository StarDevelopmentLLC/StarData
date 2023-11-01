package com.stardevllc.stardata.sql.objects.typehandlers.impl;

import com.stardevllc.stardata.api.interfaces.sql.Column;
import com.stardevllc.stardata.sql.objects.typehandlers.SQLTypeHandler;

public class IntegerHandler extends SQLTypeHandler {
    public IntegerHandler() {
        super(Integer.class, "int", IntegerHandler::parse, IntegerHandler::parse);
        addAdditionalClass(int.class);
    }
    
    private static Object parse(Column column, Object object) {
        if (object instanceof Number number) {
            return number.intValue();
        } else if (object instanceof String str) {
            return Integer.parseInt(str);
        }
        return 0;
    }
}
