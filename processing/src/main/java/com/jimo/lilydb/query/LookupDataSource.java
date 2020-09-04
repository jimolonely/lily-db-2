package com.jimo.lilydb.query;

import java.util.List;
import java.util.Set;

public class LookupDataSource implements DataSource {
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
