package com.jimo.lilydb.query;

import com.fasterxml.jackson.annotation.JsonTypeName;

import java.util.List;
import java.util.Set;

@JsonTypeName("table")
public class TableDataSource implements DataSource {
    @Override
    public Set<String> getTableNames() {
        return null;
    }

    @Override
    public List<DataSource> getChildren() {
        return null;
    }

    @Override
    public DataSource withChildren(List<DataSource> children) {
        return null;
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public boolean isConcrete() {
        return false;
    }
}
