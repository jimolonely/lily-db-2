package com.jimo.lilydb.segment.column;

import com.jimo.lilydb.segment.ColumnValueSelector;
import com.jimo.lilydb.segment.data.ReadableOffset;

import java.io.Closeable;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 9:02
 */
public interface BaseColumn extends Closeable {

    ColumnValueSelector<?> makeColumnValueSelector(ReadableOffset offset);
}
