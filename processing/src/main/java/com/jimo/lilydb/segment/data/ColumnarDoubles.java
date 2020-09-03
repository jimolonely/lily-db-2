package com.jimo.lilydb.segment.data;

import com.jimo.lilydb.collections.bitmap.ImmutableBitmap;
import com.jimo.lilydb.segment.ColumnValueSelector;
import com.jimo.lilydb.segment.DoubleColumnSelector;
import com.jimo.lilydb.segment.historical.HistoricalColumnSelector;
import com.jimo.lilydb.segment.vector.BaseDoubleVectorValueSelector;
import com.jimo.lilydb.segment.vector.ReadableVectorOffset;
import com.jimo.lilydb.segment.vector.VectorSelectorUtils;
import com.jimo.lilydb.segment.vector.VectorValueSelector;
import com.sun.istack.internal.Nullable;
import org.roaringbitmap.PeekableIntIterator;

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

            class HistoricalDoubleColumnSelector implements DoubleColumnSelector, HistoricalColumnSelector<Double> {

                @Override
                public double getDouble(int offset) {
                    return ColumnarDoubles.this.get(offset);
                }

                @Override
                public double getDouble() {
                    return ColumnarDoubles.this.get(offset.getOffset());
                }

                @Override
                public boolean isNull() {
                    return false;
                }
            }
            return new HistoricalDoubleColumnSelector();
        } else {
            class HistoricalDoubleColumnSelectorWithNulls implements DoubleColumnSelector,
                    HistoricalColumnSelector<Double> {

                private PeekableIntIterator nullIterator = nullValueBitmap.peekableIterator();
                private int nullMark = -1;
                private int offsetMark = -1;

                @Override
                public double getDouble(int offset) {
                    assert !nullValueBitmap.get(offset);
                    return ColumnarDoubles.this.get(offset);
                }

                @Override
                public double getDouble() {
                    assert !isNull();
                    return ColumnarDoubles.this.get(offset.getOffset());
                }

                @Override
                public boolean isNull() {
                    final int i = offset.getOffset();
                    if (i < offsetMark) {
                        nullMark = -1;
                        nullIterator = nullValueBitmap.peekableIterator();
                    }
                    offsetMark = i;
                    if (nullMark < i) {
                        nullIterator.advanceIfNeeded(offsetMark);
                        if (nullIterator.hasNext()) {
                            nullMark = nullIterator.next();
                        }
                    }
                    return nullMark == offsetMark;
                }
            }
            return new HistoricalDoubleColumnSelectorWithNulls();
        }
    }

    default VectorValueSelector makeVectorValueSelector(final ReadableVectorOffset theOffset,
                                                        ImmutableBitmap nullValueBitmap) {
        class ColumnarDoubleVectorValueSelector extends BaseDoubleVectorValueSelector {

            private final double[] doubleVector;
            private int id = ReadableVectorOffset.NULL_ID;

            private PeekableIntIterator nullIterator = nullValueBitmap.peekableIterator();
            private int offsetMark = -1;

            @Nullable
            private boolean[] nullVector = null;

            public ColumnarDoubleVectorValueSelector() {
                super(theOffset);
                this.doubleVector = new double[offset.getMaxVectorSize()];
            }

            @Override
            public double[] getDoubleVector() {
                computeVectorIfNeeded();
                return doubleVector;
            }

            @Override
            public boolean[] getNullVector() {
                computeVectorIfNeeded();
                return nullVector;
            }

            private void computeVectorIfNeeded() {
                if (id == offset.getId()) {
                    return;
                }
                if (offset.isContiguous()) {
                    if (offset.getStartOffset() < offsetMark) {
                        nullIterator = nullValueBitmap.peekableIterator();
                    }
                    offsetMark = offset.getStartOffset() + offset.getCurrentVectorSize();
                    ColumnarDoubles.this.get(doubleVector, offset.getStartOffset(), offset.getCurrentVectorSize());
                } else {
                    final int[] offsets = offset.getOffsets();
                    if (offsets[offsets.length - 1] < offsetMark) {
                        nullIterator = nullValueBitmap.peekableIterator();
                    }
                    offsetMark = offsets[offsets.length - 1];
                    ColumnarDoubles.this.get(doubleVector, offsets, offset.getCurrentVectorSize());
                }

                nullVector = VectorSelectorUtils.populateNullVector(nullVector, offset, nullIterator);
                id = offset.getId();
            }
        }
        return new ColumnarDoubleVectorValueSelector();
    }
}
