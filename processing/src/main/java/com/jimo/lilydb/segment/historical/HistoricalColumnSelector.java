package com.jimo.lilydb.segment.historical;

import com.jimo.lilydb.segment.ColumnValueSelector;

public interface HistoricalColumnSelector<T> extends ColumnValueSelector<T> {

    double getDouble(int offset);
}
