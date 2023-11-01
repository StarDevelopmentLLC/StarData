package com.stardevllc.stardata.sql.objects.typehandlers.impl;

import com.stardevllc.stardata.api.interfaces.sql.Column;
import com.stardevllc.stardata.sql.objects.typehandlers.SQLTypeHandler;

public class StringHandler extends SQLTypeHandler {
    public StringHandler() {
        super(String.class, "varchar(1000)", StringHandler::parse, StringHandler::parse);
        addAdditionalClass(Character.class, char.class);
    }
    
    private static Object parse(Column column, Object object) {
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
