package com.jimo.lilydb.segment.column;

import com.jimo.lilydb.collections.bitmap.ImmutableBitmap;
import com.jimo.lilydb.segment.ColumnValueSelector;
import com.jimo.lilydb.segment.data.ColumnarDoubles;
import com.jimo.lilydb.segment.data.ReadableOffset;
import com.jimo.lilydb.segment.vector.ReadableVectorOffset;
import com.jimo.lilydb.segment.vector.VectorValueSelector;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 22:09
 */
public class DoublesColumn implements NumericColumn {

    public static DoublesColumn create(ColumnarDoubles column, ImmutableBitmap nullValueBitmap) {
        if (nullValueBitmap.isEmpty()) {
            return new DoublesColumn(column);
        } else {
            return new DoublesColumnWithNulls(column, nullValueBitmap);
        }
    }

    final ColumnarDoubles column;

    public DoublesColumn(ColumnarDoubles column) {
        this.column = column;
    }

    @Override
    public int length() {
        return column.size();
    }

    @Override
    public long getLongSingleValueRow(int rowNum) {
        return (long) column.get(rowNum);
    }

    @Override
    public void close() {
        column.close();
    }

    @Override
    public ColumnValueSelector<?> makeColumnValueSelector(ReadableOffset offset) {
        return null;
    }

    @Override
    public VectorValueSelector makeVectorValueSelector(ReadableVectorOffset offset) {
        return null;
    }
}
