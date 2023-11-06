package com.stardevllc.stardata.sql;

import com.stardevllc.stardata.sql.interfaces.Table;

public record SQLPushInfo(String sql, boolean generateKeys, Table table) {
}
