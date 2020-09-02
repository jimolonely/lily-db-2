package com.jimo.lilydb.segment.column;

import com.jimo.lilydb.java.util.common.UOE;
import com.jimo.lilydb.segment.ColumnValueSelector;
import com.jimo.lilydb.segment.data.ReadableOffset;
import com.jimo.lilydb.segment.vector.ReadableVectorOffset;
import com.jimo.lilydb.segment.vector.VectorObjectSelector;
import com.jimo.lilydb.segment.vector.VectorValueSelector;

import java.io.Closeable;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 9:02
 */
public interface BaseColumn extends Closeable {

    ColumnValueSelector<?> makeColumnValueSelector(ReadableOffset offset);

    default VectorValueSelector makeVectorValueSelector(ReadableVectorOffset offset) {
        throw new UOE("Cannot make VectorValueSelector for column with class[%s]", getClass().getName());
    }

    default VectorObjectSelector makeVectorObjectSelector(ReadableVectorOffset offset) {
        throw new UOE("Cannot make VectorObjectSelector for column with class[%s]", getClass().getName());
    }
}
