package com.jimo.lilydb.query.scan;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.internal.Nullable;

import java.util.List;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/5 22:42
 */
public class ScanResultValue implements Comparable<ScanResultValue> {

    private final String segmentId;
    private final List<String> columns;
    private final Object events;

    @JsonCreator
    public ScanResultValue(
            @JsonProperty("segmentId") @Nullable String segmentId,
            @JsonProperty("columns") List<String> columns,
            @JsonProperty("events") Object events) {
        this.segmentId = segmentId;
        this.columns = columns;
        this.events = events;
    }

    @Override
    public int compareTo(ScanResultValue o) {
        return 0;
    }

    @JsonProperty
    @Nullable
    public String getSegmentId() {
        return segmentId;
    }

    @JsonProperty
    public List<String> getColumns() {
        return columns;
    }

    @JsonProperty
    public Object getEvents() {
        return events;
    }

}
