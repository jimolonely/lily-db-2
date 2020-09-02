package com.jimo.lilydb.segment.vector;

import com.sun.istack.internal.Nullable;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 21:47
 */
public interface VectorValueSelector extends VectorSizeInspector {

    long[] getLongVector();

    float[] getFloatVector();

    double[] getDoubleVector();

    @Nullable
    boolean[] getNullVector();
}
