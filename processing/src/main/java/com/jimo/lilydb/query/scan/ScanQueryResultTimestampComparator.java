package com.jimo.lilydb.query.scan;

import java.util.Comparator;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/6 7:31
 */
public class ScanQueryResultTimestampComparator implements Comparator<ScanResultValue> {

    private final ScanQuery scanQuery;

    public ScanQueryResultTimestampComparator(ScanQuery scanQuery) {
        this.scanQuery = scanQuery;
    }

    @Override
    public int compare(ScanResultValue o1, ScanResultValue o2) {

        return 0;
    }
}
