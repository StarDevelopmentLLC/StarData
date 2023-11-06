package com.stardevllc.stardata.sql;

import com.stardevllc.stardata.api.interfaces.sql.Table;

public record SQLPushInfo(String sql, boolean generateKeys, Table table) {
}
