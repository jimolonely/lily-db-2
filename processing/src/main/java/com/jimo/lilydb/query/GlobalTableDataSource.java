package com.jimo.lilydb.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("globalTable")
public class GlobalTableDataSource extends TableDataSource {

    public GlobalTableDataSource() {
        super(name);
    }

    @Override
    public boolean isGlobal() {
        return true;
    }
}
