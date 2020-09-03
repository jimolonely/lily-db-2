package com.jimo.lilydb.segment;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/3 8:16
 */
public interface DoubleColumnSelector extends ColumnValueSelector<Double> {

    @Override
    default float getFloat() {
        return (float) getDouble();
    }

    @Override
    default long getLong() {
        return (long) getDouble();
    }

    @Override
    default Double getObject() {
        if (isNull()) {
            return null;
        }
        return getDouble();
    }

    @Override
    default Class<Double> classObject() {
        return Double.class;
    }
}
