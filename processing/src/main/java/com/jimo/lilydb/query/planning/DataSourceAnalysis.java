package com.jimo.lilydb.query.planning;

import com.jimo.lilydb.query.DataSource;
import com.jimo.lilydb.query.spec.QuerySegmentSpec;

import java.util.Optional;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 21:54
 */
public class DataSourceAnalysis {


    public static DataSourceAnalysis forDataSource(final DataSource dataSource) {
        // TODO
        return new DataSourceAnalysis();
    }

    public Optional<QuerySegmentSpec> getBaseQuerySegmentSpec() {
        // TODO
        return null;
    }
}
