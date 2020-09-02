package com.jimo.lilydb.segment;

import com.jimo.lilydb.guice.ExtensionPoint;

/**
 * @author jimo
 * @version 1.0.0
 * @date 2020/9/2 9:16
 */
@ExtensionPoint
public interface BaseDoubleColumnValueSelector extends BaseNullableColumnValueSelector {

    double getDouble();
}
