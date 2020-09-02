package com.jimo.lilydb.segment.vector;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 21:52
 */
public interface ReadableVectorOffset extends VectorSizeInspector {

    int NULL_ID = -1;

    int getId();

    boolean isContiguous();

    int getStartOffset();

    int[] getOffsets();
}
