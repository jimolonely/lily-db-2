package com.jimo.lilydb.segment.vector;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 21:47
 */
public interface VectorSizeInspector {

    int getMaxVectorSize();

    int getCurrentVectorSize();
}
