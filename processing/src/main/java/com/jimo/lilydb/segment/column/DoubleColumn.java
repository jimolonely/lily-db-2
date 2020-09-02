package com.jimo.lilydb.segment.column;

import com.jimo.lilydb.segment.ColumnValueSelector;
import com.jimo.lilydb.segment.data.ReadableOffset;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 22:09
 */
public class DoubleColumn implements NumericColumn {


    @Override
    public int length() {
        return 0;
    }

    @Override
    public long getLongSingleValueRow(int rowNum) {
        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public ColumnValueSelector<?> makeColumnValueSelector(ReadableOffset offset) {
        return null;
    }
}
