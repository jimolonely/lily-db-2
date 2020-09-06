package com.jimo.lilydb.query.scan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Preconditions;
import com.google.common.collect.Ordering;
import com.jimo.lilydb.java.util.common.StringUtils;
import com.jimo.lilydb.java.util.common.UOE;
import com.jimo.lilydb.query.BaseQuery;
import com.jimo.lilydb.query.DataSource;
import com.jimo.lilydb.query.Druids;
import com.jimo.lilydb.query.Query;
import com.jimo.lilydb.query.filter.DimFilter;
import com.jimo.lilydb.query.spec.QuerySegmentSpec;
import com.jimo.lilydb.segment.VirtualColumns;
import com.jimo.lilydb.segment.column.ColumnHolder;

import java.util.List;
import java.util.Map;

public class ScanQuery extends BaseQuery<ScanResultValue> {

    public enum ResultFormat {
        RESULT_FORMAT_LIST,
        RESULT_FORMAT_COMPACTED_LIST,
        RESULT_FORMAT_VALUE_VECTOR;

        @JsonValue
        @Override
        public String toString() {
            switch (this) {
                case RESULT_FORMAT_LIST:
                    return "list";
                case RESULT_FORMAT_COMPACTED_LIST:
                    return "compactedList";
                case RESULT_FORMAT_VALUE_VECTOR:
                    return "valueVector";
                default:
                    return "";
            }
        }

        public static ResultFormat fromString(String name) {
            switch (name) {
                case "list":
                    return RESULT_FORMAT_LIST;
                case "compactedList":
                    return RESULT_FORMAT_COMPACTED_LIST;
                case "valueVector":
                    return RESULT_FORMAT_VALUE_VECTOR;
                default:
                    throw new UOE("scan query result format [%s] is not support", name);
            }
        }
    }

    public enum Order {
        ASCENDING,
        DESCENDING,
        NONE;

        @Override
        @JsonValue
        public String toString() {
            return StringUtils.toLowerCase(this.name());
        }

        @JsonCreator
        public static Order fromString(String name) {
            return valueOf(StringUtils.toUpperCase(name));
        }
    }

    public static final String CTX_KEY_OUTERMOST = "scanOutermost";

    private final VirtualColumns virtualColumns;
    private final ResultFormat resultFormat;
    private final int batchSize;
    @JsonProperty("limit")
    private final long scanRowsLimit;
    private final DimFilter dimFilter;
    private final List<String> columns;
    private final Boolean legacy;
    private final Order order;
    private final Integer maxRowsQueuedForOrdering;
    private final Integer maxSegmentPartitionsOrderedInMemory;

    @JsonCreator
    public ScanQuery(DataSource dataSource,
                     Map<String, Object> context, QuerySegmentSpec spec,
                     VirtualColumns virtualColumns,
                     ResultFormat resultFormat,
                     int batchSize,
                     long scanRowsLimit,
                     DimFilter dimFilter,
                     List<String> columns,
                     Boolean legacy,
                     Order order) {
        super(dataSource, false, context, spec);
        this.virtualColumns = VirtualColumns.nullToEmpty(virtualColumns);
        this.resultFormat = resultFormat == null ? ResultFormat.RESULT_FORMAT_LIST : resultFormat;
        this.batchSize = batchSize == 0 ? 4096 * 5 : batchSize;
        Preconditions.checkArgument(this.batchSize > 0, "batchSize must be greater than 0");
        this.scanRowsLimit = scanRowsLimit == 0 ? Long.MAX_VALUE : scanRowsLimit;
        Preconditions.checkArgument(this.scanRowsLimit > 0, "limit must be greater than 0");
        this.dimFilter = dimFilter;
        this.columns = columns;
        this.legacy = legacy;
        this.order = order == null ? Order.NONE : order;
        if (this.order != Order.NONE) {
            Preconditions.checkArgument(
                    columns == null || columns.size() == 0 || columns.contains(ColumnHolder.TIME_COLUMN_NAME),
                    "The __time column must be selected if the results are time-ordered."
            );
        }
        this.maxRowsQueuedForOrdering = validateAndGetMaxRowsQueuedForOrdering();
        this.maxSegmentPartitionsOrderedInMemory = validateAndGetMaxSegmentPartitionsOrderedInMemory();
    }

    private Integer validateAndGetMaxSegmentPartitionsOrderedInMemory() {
        final Integer maxSegmentPartitionsOrderedInMemory =
                getContextValue(ScanQueryConfig.CTX_KEY_MAX_SEGMENT_PARTITIONS_FOR_ORDERING, null);
        Preconditions.checkArgument(
                maxSegmentPartitionsOrderedInMemory == null || maxSegmentPartitionsOrderedInMemory > 0,
                "maxSegmentPartitionsOrderedInMemory must be greater than 0"
        );
        return maxSegmentPartitionsOrderedInMemory;
    }

    private Integer validateAndGetMaxRowsQueuedForOrdering() {
        final Integer maxRowsQueuedForOrdering = getContextValue(ScanQueryConfig.CTX_KEY_MAX_ROWS_QUEUED_FOR_ORDERING
                , null);
        Preconditions.checkArgument(
                maxRowsQueuedForOrdering == null || maxRowsQueuedForOrdering > 0,
                "maxRowsQueuedForOrdering must be greater than 0"
        );
        return maxRowsQueuedForOrdering;
    }

    @Override
    public VirtualColumns getVirtualColumns() {
        return virtualColumns;
    }

    public ResultFormat getResultFormat() {
        return resultFormat;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public long getScanRowsLimit() {
        return scanRowsLimit;
    }

    public Order getOrder() {
        return order;
    }

    public Integer getMaxRowsQueuedForOrdering() {
        return maxRowsQueuedForOrdering;
    }

    public Integer getMaxSegmentPartitionsOrderedInMemory() {
        return maxSegmentPartitionsOrderedInMemory;
    }

    public List<String> getColumns() {
        return columns;
    }

    public Boolean isLegacy() {
        return legacy;
    }

    @Override
    public boolean hasFilters() {
        return dimFilter != null;
    }

    @Override
    public DimFilter getFilter() {
        return dimFilter;
    }

    @Override
    public String getType() {
        return SCAN;
    }

    @Override
    public Ordering<ScanResultValue> getResultOrdering() {
        if (order == Order.NONE) {
            return Ordering.natural();
        }
        return Ordering.from(new ScanQueryResultTimestampComparator(this)).reverse();
    }

    @Override
    public Query<ScanResultValue> withOverriddenContext(Map<String, Object> contextOverride) {
        return Druids.ScanQueryBuilder.copy(this).context(computeOverriddenContext(getContext(), contextOverride)).build();
    }

    @Override
    public Query<ScanResultValue> withQuerySegmentSpec(QuerySegmentSpec spec) {
        return Druids.ScanQueryBuilder.copy(this).intervals(spec).build();
    }

    @Override
    public Query<ScanResultValue> withDataSource(DataSource dataSource) {
        return Druids.ScanQueryBuilder.copy(this).dataSource(dataSource).build();
    }
}
