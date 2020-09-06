package com.jimo.lilydb.query;

import com.jimo.lilydb.query.filter.DimFilter;
import com.jimo.lilydb.query.scan.ScanQuery;
import com.jimo.lilydb.query.spec.QuerySegmentSpec;
import com.jimo.lilydb.segment.VirtualColumn;
import com.jimo.lilydb.segment.VirtualColumns;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Druids {

    public static class ScanQueryBuilder {
        private DataSource dataSource;
        private QuerySegmentSpec querySegmentSpec;
        private VirtualColumns virtualColumns;
        private Map<String, Object> context;
        private ScanQuery.ResultFormat resultFormat;
        private int batchSize;
        private long limit;
        private DimFilter dimFilter;
        private List<String> columns;
        private Boolean legacy;
        private ScanQuery.Order order;

        public ScanQueryBuilder() {
            this.dataSource = null;
            querySegmentSpec = null;
            virtualColumns = null;
            context = null;
            resultFormat = null;
            dimFilter = null;
            columns = new ArrayList<>();
        }

        public ScanQuery build() {
            return new ScanQuery(
                    dataSource,
                    context,
                    querySegmentSpec,
                    virtualColumns,
                    resultFormat,
                    batchSize,
                    limit,
                    dimFilter,
                    columns,
                    legacy,
                    order
            );
        }

        public static ScanQueryBuilder copy(ScanQuery query) {
            return new ScanQueryBuilder()
                    .dataSource(query.getDataSource())
                    .intervals(query.getQuerySegmentSpec())
                    .virtualColumns(query.getVirtualColumns())
                    .resultFormat(query.getResultFormat())
                    .batchSize(query.getBatchSize())
                    .limit(query.getScanRowsLimit())
                    .filters(query.getFilter())
                    .columns(query.getColumns())
                    .legacy(query.isLegacy())
                    .context(query.getContext())
                    .order(query.getOrder());
        }

        public ScanQueryBuilder querySegmentSpec(QuerySegmentSpec spec) {
            this.querySegmentSpec = spec;
            return this;
        }

        public ScanQueryBuilder dataSource(String ds) {
            this.dataSource = new TableDataSource(ds);
            return this;
        }

        public ScanQueryBuilder dataSource(DataSource ds) {
            this.dataSource = ds;
            return this;
        }


        public ScanQueryBuilder intervals(QuerySegmentSpec q) {
            querySegmentSpec = q;
            return this;
        }

        public ScanQueryBuilder virtualColumns(VirtualColumns virtualColumns) {
            this.virtualColumns = virtualColumns;
            return this;
        }

        public ScanQueryBuilder virtualColumns(VirtualColumn... virtualColumns) {
            return virtualColumns(VirtualColumns.create(Arrays.asList(virtualColumns)));
        }

        public ScanQueryBuilder context(Map<String, Object> c) {
            this.context = c;
            return this;
        }

        public ScanQueryBuilder resultFormat(ScanQuery.ResultFormat r) {
            resultFormat = r;
            return this;
        }

        public ScanQueryBuilder batchSize(int b) {
            batchSize = b;
            return this;
        }

        public ScanQueryBuilder limit(long l) {
            limit = l;
            return this;
        }

        public ScanQueryBuilder filters(DimFilter f) {
            dimFilter = f;
            return this;
        }

        public ScanQueryBuilder columns(List<String> c) {
            columns = c;
            return this;
        }

        public ScanQueryBuilder columns(String... c) {
            columns = Arrays.asList(c);
            return this;
        }

        public ScanQueryBuilder legacy(Boolean legacy) {
            this.legacy = legacy;
            return this;
        }

        public ScanQueryBuilder order(ScanQuery.Order order) {
            this.order = order;
            return this;
        }
    }
}
