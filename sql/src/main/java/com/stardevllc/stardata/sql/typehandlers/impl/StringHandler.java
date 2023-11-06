package com.stardevllc.stardata.sql.typehandlers.impl;

import com.stardevllc.stardata.api.interfaces.model.FieldModel;
import com.stardevllc.stardata.api.interfaces.sql.SQLDatabase;
import com.stardevllc.stardata.sql.typehandlers.SQLTypeHandler;

public class StringHandler extends SQLTypeHandler {
    public StringHandler() {
        super(String.class, "varchar(1000)", StringHandler::parse, StringHandler::parse);
        addAdditionalClass(Character.class, char.class);
    }
    
    private static Object parse(FieldModel<SQLDatabase> column, Object object) {
        if (object instanceof String str) {
            return str;
        } else if (object instanceof Character character) {
            return character;
        } else if (object != null) {
            return object.toString();
        }
        return "";
    }
}
