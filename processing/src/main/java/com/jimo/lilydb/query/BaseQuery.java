package com.jimo.lilydb.query;

import com.jimo.lilydb.guice.ExtensionPoint;
import com.jimo.lilydb.java.util.common.granularity.Granularities;
import com.jimo.lilydb.java.util.common.granularity.Granularity;
import com.jimo.lilydb.query.spec.QuerySegmentSpec;
import org.joda.time.Duration;

import java.util.Map;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 7:24
 */
@ExtensionPoint
public abstract class BaseQuery<T> implements Query<T> {

    protected BaseQuery(DataSource dataSource, boolean descending, Map<String, Object> context, QuerySegmentSpec spec
            , Granularity granularity) {
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

}
