package com.jimo.lilydb.segment.data;

import com.jimo.lilydb.collections.bitmap.ImmutableBitmap;
import com.jimo.lilydb.segment.ColumnValueSelector;

import java.io.Closeable;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 22:10
 */
public interface ColumnarDoubles extends Closeable {

    int size();

    double get(int index);

    default void get(double[] out, int start, int length) {
        for (int i = 0; i < length; i++) {
            out[i] = get(i + start);
        }
    }

    default void get(double[] out, int[] indexes, int length) {
        for (int i = 0; i < length; i++) {
            out[i] = get(indexes[i]);
        }
    }

    @Override
    void close();

    default ColumnValueSelector<Double> makeColumnValueSelector(ReadableOffset offset,
                                                                ImmutableBitmap nullValueBitmap) {
        if (nullValueBitmap.isEmpty()) {

        } else {

        }
        // TODO
        return null;
    }
}
