package com.jimo.lilydb.query.spec;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.jimo.lilydb.query.Query;
import com.jimo.lilydb.query.QueryRunner;
import com.jimo.lilydb.query.QuerySegmentWalker;
import org.joda.time.Interval;

import java.util.List;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 7:03
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
public interface QuerySegmentSpec {
    <T> QueryRunner<T> lookup(Query<T> query, QuerySegmentWalker walker);
    // TODO

    List<Interval> getIntervals();
}
