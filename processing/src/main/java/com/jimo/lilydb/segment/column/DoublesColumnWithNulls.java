package com.jimo.lilydb.segment.column;

import com.jimo.lilydb.collections.bitmap.ImmutableBitmap;
import com.jimo.lilydb.segment.ColumnValueSelector;
import com.jimo.lilydb.segment.data.ColumnarDoubles;
import com.jimo.lilydb.segment.data.ReadableOffset;
import com.jimo.lilydb.segment.vector.ReadableVectorOffset;
import com.jimo.lilydb.segment.vector.VectorValueSelector;

public class DoublesColumnWithNulls extends DoublesColumn {

    private final ImmutableBitmap nullValueBitmap;

    public DoublesColumnWithNulls(ColumnarDoubles column, ImmutableBitmap nullValueBitmap) {
        super(column);
        this.nullValueBitmap = nullValueBitmap;
    }

    @Override
    public ColumnValueSelector<?> makeColumnValueSelector(ReadableOffset offset) {
        return column.makeColumnValueSelector(offset, nullValueBitmap);
    }

    @Override
    public VectorValueSelector makeVectorValueSelector(ReadableVectorOffset offset) {
        return column.makeVectorValueSelector(offset, nullValueBitmap);
    }

    @Override
    public long getLongSingleValueRow(int rowNum) {
        assert !nullValueBitmap.get(rowNum);
        return super.getLongSingleValueRow(rowNum);
    }
}
