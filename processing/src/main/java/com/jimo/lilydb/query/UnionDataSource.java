package com.jimo.lilydb.query;

import java.util.List;
import java.util.Set;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/4 9:07
 */
public class UnionDataSource implements DataSource {
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
