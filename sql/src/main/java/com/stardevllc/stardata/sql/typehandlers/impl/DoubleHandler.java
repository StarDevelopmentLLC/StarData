package com.stardevllc.stardata.sql.typehandlers.impl;

import com.stardevllc.stardata.api.interfaces.model.FieldModel;
import com.stardevllc.stardata.api.interfaces.sql.SQLDatabase;
import com.stardevllc.stardata.sql.typehandlers.SQLTypeHandler;

public class DoubleHandler extends SQLTypeHandler {
    public DoubleHandler() {
        super(Double.class, "double", DoubleHandler::parse, DoubleHandler::parse);
        addAdditionalClass(double.class);
    }
    
    private static Object parse(FieldModel<SQLDatabase> column, Object object) {
        if (object instanceof Number number) {
            return number.doubleValue();
        } else if (object instanceof String str) {
            return Double.parseDouble(str);
        }
        return 0.0;
    }
}
