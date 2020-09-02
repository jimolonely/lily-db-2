package com.jimo.lilydb.segment.vector;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 22:03
 */
public interface VectorObjectSelector extends VectorSizeInspector {

    Object[] getObjectVector();
}
