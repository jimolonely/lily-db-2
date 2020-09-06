package com.jimo.lilydb.query;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import com.jimo.lilydb.guice.ExtensionPoint;
import com.jimo.lilydb.java.util.common.granularity.Granularity;
import com.jimo.lilydb.query.filter.DimFilter;
import com.jimo.lilydb.query.spec.QuerySegmentSpec;
import com.jimo.lilydb.query.timeseries.TimeseriesQuery;
import com.jimo.lilydb.segment.VirtualColumns;
import com.sun.istack.internal.Nullable;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 6:44
 */
@ExtensionPoint
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "queryType")
@JsonSubTypes(value = {
        @JsonSubTypes.Type(name = Query.TIMESERIES, value = TimeseriesQuery.class)
})
public interface Query<T> {

    String TIMESERIES = "timeseries";
    String SEARCH = "search";
    String TIME_BOUNDARY = "timeBoundary";
    String GROUPBY = "groupBy";
    String SCAN = "scan";
    String SEGMENT_METADATA = "segmentMetadata";
    String SELECT = "select";
    String TOPN = "topN";
    String DATASOURCE_METADATA = "dataSourceMetadata";

    DataSource getDataSource();

    boolean hasFilters();

    String getType();

    QueryRunner<T> getRunner(QuerySegmentWalker walker);

    List<Interval> getIntervals();

    Duration getDuration();

    Granularity getGranularity();

    DateTimeZone getTimezone();

    Map<String, Object> getContext();

    <ContextType> ContextType getContextValue(String key);

    <ContextType> ContextType getContextValue(String key, ContextType defaultValue);

    boolean getContextBoolean(String key, boolean defaultValue);

    boolean isDescending();

    Ordering<T> getResultOrdering();

    Query<T> withOverriddenContext(Map<String, Object> contextOverride);

    Query<T> withQuerySegmentSpec(QuerySegmentSpec spec);

    Query<T> withId(String id);

    @Nullable
    String getId();

    Query<T> withSubQueryId(String subQueryId);

    default Query<T> withDefaultSubQueryId() {
        return withSubQueryId(UUID.randomUUID().toString());
    }

    String getSubQueryId();

    default Query<T> withSqlQueryId() {
        return this;
    }

    @Nullable
    default String getSqlQueryId() {
        return null;
    }

    default String getMostSpecifiedId() {
        final String subQueryId = getSubQueryId();
        return subQueryId == null ? Preconditions.checkNotNull(getId(), "queryId") : subQueryId;
    }

    Query<T> withDataSource(DataSource dataSource);

    default Query<T> optimizeForSegment(PerSegmentQueryOptimizationContext optimizationContext) {
        return this;
    }

    default Query<T> withPriority(int priority) {
        return withOverriddenContext(ImmutableMap.of(QueryContexts.PRIORITY_KEY, priority));
    }

    default Query<T> withLane(String lane) {
        return withOverriddenContext(ImmutableMap.of(QueryContexts.LANE_KEY, lane));
    }

    default VirtualColumns getVirtualColumns() {
        return VirtualColumns.EMPTY;
    }

    DimFilter getFilter();
}
