package com.jimo.lilydb.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("globalTable")
public class GlobalTableDataSource extends TableDataSource {

    @Override
    public boolean isGlobal() {
        return true;
    }
}
