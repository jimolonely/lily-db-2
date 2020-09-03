package com.jimo.lilydb.segment.vector;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/3 8:34
 */
public abstract class BaseDoubleVectorValueSelector implements VectorValueSelector {

    protected final ReadableVectorOffset offset;

    private int longId = ReadableVectorOffset.NULL_ID;
    private int floatId = ReadableVectorOffset.NULL_ID;

    private long[] longVector;
    private float[] floatVector;

    public BaseDoubleVectorValueSelector(ReadableVectorOffset offset) {
        this.offset = offset;
    }

    @Override
    public int getCurrentVectorSize() {
        return offset.getCurrentVectorSize();
    }

    @Override
    public int getMaxVectorSize() {
        return offset.getMaxVectorSize();
    }

    @Override
    public long[] getLongVector() {
        if (longId == offset.getId()) {
            return longVector;
        }
        if (longVector == null) {
            longVector = new long[offset.getMaxVectorSize()];
        }

        final double[] doubleVector = getDoubleVector();
        for (int i = 0; i < getCurrentVectorSize(); i++) {
            longVector[i] = (long) doubleVector[i];
        }
        longId = offset.getId();
        return longVector;
    }

    @Override
    public float[] getFloatVector() {
        if (floatId == offset.getId()) {
            return floatVector;
        }
        if (floatVector == null) {
            floatVector = new float[offset.getMaxVectorSize()];
        }
        final double[] doubleVector = getDoubleVector();
        for (int i = 0; i < getCurrentVectorSize(); i++) {
            floatVector[i] = (float) doubleVector[i];
        }
        floatId = offset.getId();
        return floatVector;
    }
}
