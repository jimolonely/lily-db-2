package com.jimo.lilydb.query;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Ordering;
import com.jimo.lilydb.guice.ExtensionPoint;
import com.jimo.lilydb.java.util.common.granularity.Granularities;
import com.jimo.lilydb.java.util.common.granularity.Granularity;
import com.jimo.lilydb.java.util.common.granularity.PeriodGranularity;
import com.jimo.lilydb.query.planning.DataSourceAnalysis;
import com.jimo.lilydb.query.spec.QuerySegmentSpec;
import com.sun.istack.internal.Nullable;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 7:24
 */
@ExtensionPoint
public abstract class BaseQuery<T> implements Query<T> {

    protected BaseQuery(DataSource dataSource,
                        boolean descending,
                        Map<String, Object> context,
                        QuerySegmentSpec spec,
                        Granularity granularity) {
        Preconditions.checkNotNull(dataSource, "datasource cannot be null");
        Preconditions.checkNotNull(spec, "querySegmentSpec cannot be null");
        Preconditions.checkNotNull(granularity, "granularity cannot be null");
        this.dataSource = dataSource;
        this.descending = descending;
        this.context = context;
        querySegmentSpec = spec;
        this.granularity = granularity;
    }

    public BaseQuery(DataSource dataSource, boolean descending, Map<String, Object> context,
                     QuerySegmentSpec querySegmentSpec) {
        this(dataSource, descending, context, querySegmentSpec, Granularities.ALL);
    }

    public static void checkInterrupted() {
        if (Thread.interrupted()) {
            throw new QueryInterruptedException(new InterruptedException());
        }
    }

    public static final String QUERY_ID = "queryId";
    public static final String SUB_QUERY_ID = "subQueryId";
    public static final String SQL_QUERY_ID = "sqlQueryId";

    private final DataSource dataSource;
    private final boolean descending;
    private final Map<String, Object> context;
    private final QuerySegmentSpec querySegmentSpec;
    private volatile Duration duration;
    private final Granularity granularity;

    @JsonProperty
    @Override
    public DataSource getDataSource() {
        return dataSource;
    }

    @JsonProperty
    @Override
    public boolean isDescending() {
        return descending;
    }

    @JsonProperty("intervals")
    public QuerySegmentSpec getQuerySegmentSpec() {
        return querySegmentSpec;
    }

    @Override
    public QueryRunner<T> getRunner(QuerySegmentWalker walker) {
        return getQuerySegmentSpecForLookUp(this).lookup(this, walker);
    }

    public static QuerySegmentSpec getQuerySegmentSpecForLookUp(BaseQuery<?> query) {
        return DataSourceAnalysis.forDataSource(query.getDataSource())
                .getBaseQuerySegmentSpec()
                .orElseGet(query::getQuerySegmentSpec);
    }

    public static Map<String, Object> computeOverriddenContext(
            final Map<String, Object> context,
            final Map<String, Object> overrides
    ) {
        Map<String, Object> overridden = new TreeMap<>();
        if (context != null) {
            overridden.putAll(context);
        }
        overridden.putAll(overrides);

        return overridden;
    }

    @Override
    public List<Interval> getIntervals() {
        return querySegmentSpec.getIntervals();
    }

    @Override
    public Duration getDuration() {
        if (duration == null) {
            Duration totalDuration = new Duration(0L);
            for (Interval interval : querySegmentSpec.getIntervals()) {
                if (interval != null) {
                    totalDuration = totalDuration.plus(interval.toDuration());
                }
            }
            duration = totalDuration;
        }
        return duration;
    }

    @Override
    @JsonProperty
    public Granularity getGranularity() {
        return granularity;
    }

    @Override
    public DateTimeZone getTimezone() {
        return granularity instanceof PeriodGranularity
                ? ((PeriodGranularity) granularity).getTimeZone()
                : DateTimeZone.UTC;
    }

    @Override
    @JsonProperty
    public Map<String, Object> getContext() {
        return context;
    }

    @Override
    public <ContextType> ContextType getContextValue(String key) {
        return context == null ? null : (ContextType) context.get(key);
    }

    @Override
    public <ContextType> ContextType getContextValue(String key, ContextType defaultValue) {
        final ContextType retVal = getContextValue(key);
        return retVal == null ? defaultValue : retVal;
    }

    @Override
    public boolean getContextBoolean(String key, boolean defaultValue) {
        return QueryContexts.parseBoolean(this, key, defaultValue);
    }

    @Override
    public Ordering<T> getResultOrdering() {
        final Ordering order = Ordering.natural();
        return descending ? order.reverse() : order;
    }

    @Nullable
    @Override
    public String getId() {
        return getContextValue(QUERY_ID);
    }

    @Override
    public Query<T> withSubQueryId(String subQueryId) {
        return withOverriddenContext(ImmutableMap.of(SUB_QUERY_ID, subQueryId));
    }

    @Override
    public String getSubQueryId() {
        return getContextValue(SUB_QUERY_ID);
    }

    @Override
    public Query<T> withId(String id) {
        return withOverriddenContext(ImmutableMap.of(QUERY_ID, id));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseQuery<?> baseQuery = (BaseQuery<?>) o;
        return descending == baseQuery.descending &&
                Objects.equals(dataSource, baseQuery.dataSource) &&
                Objects.equals(context, baseQuery.context) &&
                Objects.equals(querySegmentSpec, baseQuery.querySegmentSpec) &&
                Objects.equals(duration, baseQuery.duration) &&
                Objects.equals(granularity, baseQuery.granularity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataSource, descending, context, querySegmentSpec, duration, granularity);
    }

}
