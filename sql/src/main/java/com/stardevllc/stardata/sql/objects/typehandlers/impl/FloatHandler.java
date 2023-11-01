package com.stardevllc.stardata.sql.objects.typehandlers.impl;

import com.stardevllc.stardata.api.interfaces.sql.Column;
import com.stardevllc.stardata.sql.objects.typehandlers.SQLTypeHandler;

public class FloatHandler extends SQLTypeHandler {
    public FloatHandler() {
        super(Float.class, "float", FloatHandler::parse, FloatHandler::parse);
        addAdditionalClass(float.class);
    }
    
    private static Object parse(Column column, Object object) {
        if (object instanceof Number number) {
            return number.floatValue();
        } else if (object instanceof String str) {
            return Float.parseFloat(str);
        }
        return 0.0F;
    }
}
