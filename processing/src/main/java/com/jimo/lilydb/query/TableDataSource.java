package com.jimo.lilydb.query;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.google.common.base.Preconditions;
import com.jimo.lilydb.java.util.common.IAE;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/4 9:24
 */
@JsonTypeName("table")
public class TableDataSource implements DataSource {

    private final String name;

    @JsonCreator
    public TableDataSource(@JsonProperty("name") String name) {
        this.name = Preconditions.checkNotNull(name, "'name' must not be null");
    }

    @JsonCreator
    public static TableDataSource create(final String name) {
        return new TableDataSource(name);
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @Override
    public Set<String> getTableNames() {
        return Collections.singleton(name);
    }

    @Override
    public List<DataSource> getChildren() {
        return Collections.emptyList();
    }

    @Override
    public DataSource withChildren(List<DataSource> children) {
        if (!children.isEmpty()) {
            throw new IAE("Cannot accept children");
        }
        return this;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    @Override
    public boolean isGlobal() {
        return false;
    }

    @Override
    public boolean isConcrete() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TableDataSource that = (TableDataSource) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
