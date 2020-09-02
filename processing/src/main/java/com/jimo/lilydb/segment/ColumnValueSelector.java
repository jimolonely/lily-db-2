package com.jimo.lilydb.segment;

import com.jimo.lilydb.guice.ExtensionPoint;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 9:05
 */
@ExtensionPoint
public interface ColumnValueSelector<T> extends BaseLongColumnValueSelector, BaseDoubleColumnValueSelector,
        BaseFloatColumnValueSelector, BaseObjectColumnValueSelector {

    ColumnValueSelector[] EMPTY_ARRAY = new ColumnValueSelector[0];
}
