package com.jimo.lilydb.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;
import java.util.Set;

/**
 * Represents a source... of data... for a query. Analogous to the "FROM" clause in SQL.
 *
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/4 8:50
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", defaultImpl = TableDataSource.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = TableDataSource.class, name = "table"),
        @JsonSubTypes.Type(value = QueryDataSource.class, name = "query"),
        @JsonSubTypes.Type(value = UnionDataSource.class, name = "union"),
        @JsonSubTypes.Type(value = JoinDataSource.class, name = "join"),
        @JsonSubTypes.Type(value = LookupDataSource.class, name = "lookup"),
        @JsonSubTypes.Type(value = InlineDataSource.class, name = "inline"),
        @JsonSubTypes.Type(value = GlobalTableDataSource.class, name = "globalTable")
})
public interface DataSource {

    Set<String> getTableNames();

    List<DataSource> getChildren();

    DataSource withChildren(List<DataSource> children);

    boolean isCacheable();

    boolean isGlobal();

    boolean isConcrete();
}
